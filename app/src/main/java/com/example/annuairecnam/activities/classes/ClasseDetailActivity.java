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

        intituleEt = findViewById(R.id.intitule_et);
        promotionEt = findViewById(R.id.promotion_et);
        FloatingActionButton updateClBtn = findViewById(R.id.update_btn);
        FloatingActionButton deleteClBtn = findViewById(R.id.delete_btn);
        FloatingActionButton returnBtn = findViewById(R.id.return_btn);

        FloatingActionButton addElBtn = findViewById(R.id.add_elev_btn);
        FloatingActionButton deleteElBtn = findViewById(R.id.delete_elev_btn);

        FloatingActionButton addMtBtn = findViewById(R.id.add_mat_btn);
        FloatingActionButton deleteMtBtn = findViewById(R.id.delete_mat_btn);

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

            dbManager.updateClasse(classeId, classe);
            Toast.makeText(ClasseDetailActivity.this, "Classe mise à jour", Toast.LENGTH_SHORT).show();
            navigateToList();
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
        RecyclerView listEle = findViewById(R.id.recyclerViewLstEle);
        eleveListAdapter2 = new EleveListAdapter2(context, dbManager.getElevesByClasseId(classeId));
        listEle.setAdapter(eleveListAdapter2);
        listEle.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void initListMat() {
        RecyclerView listMat = findViewById(R.id.recyclerViewLstMat);
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
