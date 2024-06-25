package com.example.annuairecnam.activities.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.activities.eleves.EleveListActivity2;
import com.example.annuairecnam.activities.matieres.MatiereListActivity2;
import com.example.annuairecnam.adapters.EleveListAdapter2;
import com.example.annuairecnam.adapters.MatiereListAdapter2;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Classe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Set;

public class ClasseDetailActivity extends AppCompatActivity {
    private EditText intituleEt, promotionEt;
    private Context context;
    private DbManager dbManager;
    private Classe classe;
    private long classeId;
    private EleveListAdapter2 eleveListAdapter2;
    private MatiereListAdapter2 matiereListAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_detail);

        classeId = getIntent().getLongExtra("CLASSE_ID", -1);

        dbManager = new DbManager(this);
        dbManager.open();

        initContext();
        initListEle();
        initListMat();

        intituleEt = findViewById(R.id.cd_intitule_ctrl);
        promotionEt = findViewById(R.id.cd_promotion_ctrl);
        FloatingActionButton updateClBtn = findViewById(R.id.cd_save_btn);
        FloatingActionButton deleteClBtn = findViewById(R.id.cd_delete_btn);
        FloatingActionButton returnBtn = findViewById(R.id.cd_back_to_list_btn);

        FloatingActionButton addElBtn = findViewById(R.id.cd_add_eleve_btn);
        FloatingActionButton deleteElBtn = findViewById(R.id.cd_delete_eleve_btn);

        FloatingActionButton addMtBtn = findViewById(R.id.cd_add_matiere_btn);
        FloatingActionButton deleteMtBtn = findViewById(R.id.cd_delete_matiere_btn);

        if (classeId != -1) {
            classe = dbManager.getClasse(classeId);
            if (classe != null) {
                intituleEt.setText(classe.getIntitule());
                promotionEt.setText(classe.getPromotion());
            }
        }

        updateClBtn.setOnClickListener(v -> {
            classe.setIntitule(intituleEt.getText().toString());
            classe.setPromotion(promotionEt.getText().toString());

            new AlertDialog.Builder(context)
                    .setTitle("Confirmation la mise à jour")
                    .setMessage("Êtes-vous sûr de vouloir modifier cette classe ?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        dbManager.updateClasse(classeId, classe);
                        Toast.makeText(ClasseDetailActivity.this, "Classe mise à jour", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        deleteClBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmation de suppression")
                    .setMessage("Êtes-vous sûr de vouloir supprimer cette classe ?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        dbManager.deleteClasse(classeId);
                        Toast.makeText(ClasseDetailActivity.this, "Classe supprimée", Toast.LENGTH_SHORT).show();
                        navigateToList();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        returnBtn.setOnClickListener(v -> navigateToClasseList());

        addElBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ClasseDetailActivity.this, EleveListActivity2.class);
            intent.putExtra("CLASSE_ID", classeId);
            startActivity(intent);
        });

        deleteElBtn.setOnClickListener(v -> {
            Set<Long> selectedEleveIds = eleveListAdapter2.getSelectedEleveIds();
            if (selectedEleveIds.isEmpty()) {
                Toast.makeText(context, "Aucun élève sélectionné", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation de suppression")
                        .setMessage("Êtes-vous sûr de vouloir supprimer les élèves sélectionnés ?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            for (Long eleveId : selectedEleveIds) {
                                dbManager.deleteClasseEleveLiaison(classeId, eleveId);
                            }
                            Toast.makeText(ClasseDetailActivity.this, "Élèves supprimés", Toast.LENGTH_SHORT).show();
                            initListEle(); // Refresh the list
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        addMtBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ClasseDetailActivity.this, MatiereListActivity2.class);
            intent.putExtra("CLASSE_ID", classeId);
            startActivity(intent);
        });

        deleteMtBtn.setOnClickListener(v -> {
            Set<Long> selectedMatiereIds = matiereListAdapter2.getSelectedMatiereIds();
            if (selectedMatiereIds.isEmpty()) {
                Toast.makeText(context, "Aucune matière sélectionnée", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation de suppression")
                        .setMessage("Êtes-vous sûr de vouloir supprimer les matières sélectionnées ?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            for (Long matiereId : selectedMatiereIds) {
                                dbManager.deleteClasseMatiereLiaison(classeId, matiereId);
                            }
                            Toast.makeText(ClasseDetailActivity.this, "Matières supprimées", Toast.LENGTH_SHORT).show();
                            initListMat(); // Refresh the list
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }

    private void initContext() {
        context = this;
    }

    private void initListEle() {
        RecyclerView listEle = findViewById(R.id.cd_eleves_rv);
        eleveListAdapter2 = new EleveListAdapter2(context, dbManager.getElevesByClasseId(classeId));
        listEle.setAdapter(eleveListAdapter2);
        listEle.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void initListMat() {
        RecyclerView listMat = findViewById(R.id.cd_matieres_rv);
        matiereListAdapter2 = new MatiereListAdapter2(context, dbManager.getMatieresByClasseId(classeId));
        listMat.setAdapter(matiereListAdapter2);
        listMat.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void navigateToList() {
        Intent intent = new Intent(ClasseDetailActivity.this, ClasseListActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToClasseList() {
        Intent intent = new Intent(this, ClasseListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}
