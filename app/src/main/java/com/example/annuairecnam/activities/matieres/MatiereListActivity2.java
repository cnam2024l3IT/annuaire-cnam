package com.example.annuairecnam.activities.matieres;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.activities.classes.ClasseDetailActivity;
import com.example.annuairecnam.adapters.MatiereListAdapter2;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Matiere;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Set;

public class MatiereListActivity2 extends AppCompatActivity {

    private Context context;
    private DbManager dbManager;
    private long classeId;
    private MatiereListAdapter2 MatiereListAdapter2;

    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matiere_list2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation du contexte et du DbManager
        initContext();
        initDbManager();

        // Initialiser les boutons
        addBtn = findViewById(R.id.ml2_add_btn);

        // Récupération du classeId depuis l'intent
        classeId = getIntent().getLongExtra("CLASSE_ID", -1);

        initListRc();

        // Initialisation spécifique si classeId est défini
        if (classeId != -1) {
            initAddClMatBtn();
        }
    }


    private void initAddClMatBtn() {
        FloatingActionButton addBtn = findViewById(R.id.ml2_add_btn);
        addBtn.setOnClickListener(v -> {
            Set<Long> selectedMatiereIds = MatiereListAdapter2.getSelectedMatiereIds();
            if (selectedMatiereIds.isEmpty()) {
                Toast.makeText(context, "Aucune matière sélectionnée", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation de l'ajout")
                        .setMessage("Êtes-vous sûr de vouloir ajouter les matières sélectionnées ?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            for (Long matiereId : selectedMatiereIds) {
                                dbManager.insertClasseMatiere(classeId, matiereId);
                                navigateToList();
                            }
                            Toast.makeText(MatiereListActivity2.this, "Matières ajoutées", Toast.LENGTH_SHORT).show();
                            //Retour à la ClasseDetail
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }



    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.ml2_list_rv);

        List<Matiere> matieres = dbManager.getMatieresByNotInClasseId(classeId);
        MatiereListAdapter2 = new MatiereListAdapter2(dbManager.getMatieresByNotInClasseId(classeId));
        listRc.setAdapter(MatiereListAdapter2);
        listRc.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        if (matieres.isEmpty()) {
            Toast.makeText(context, "Aucune matière ne peut être rajouter", Toast.LENGTH_SHORT).show();
            navigateToList();
        }
    }

    private void initDbManager() {
        dbManager = new DbManager(context).open();
    }

    private void initContext() {
        context = this;
    }

    private void navigateToList() {
        Intent intent = new Intent(MatiereListActivity2.this, ClasseDetailActivity.class);
        intent.putExtra("CLASSE_ID", classeId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}
