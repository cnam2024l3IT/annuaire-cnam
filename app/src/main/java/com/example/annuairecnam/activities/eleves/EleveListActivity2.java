package com.example.annuairecnam.activities.eleves;

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
import com.example.annuairecnam.adapters.EleveListAdapter2;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Eleve;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Set;

public class EleveListActivity2 extends AppCompatActivity {
    private Context context;
    private DbManager dbManager;
    private long classeId;
    private EleveListAdapter2 EleveListAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleve_list2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initContext();
        initDbManager();

        // Récupération du classeId depuis l'intent
        classeId = getIntent().getLongExtra("CLASSE_ID", -1);
        initListRc();


        // Initialisation spécifique si classeId est défini
        if (classeId != -1) {
            initAddClElBtn();
        }
    }

    private void initAddClElBtn() {
        FloatingActionButton addBtn = findViewById(R.id.el2_add_btn);
        addBtn.setOnClickListener(v -> {
            Set<Long> selectedEleveIds = EleveListAdapter2.getSelectedEleveIds();
            if (selectedEleveIds.isEmpty()) {
                Toast.makeText(context, "Aucun élève sélectionné", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation de l'ajout")
                        .setMessage("Êtes-vous sûr de vouloir ajouter les élèves sélectionnés ?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            for (Long eleveId : selectedEleveIds) {
                                dbManager.insertClasseEleve(classeId, eleveId);
                                navigateToList();
                            }
                            Toast.makeText(EleveListActivity2.this, "Élèves ajoutés", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }




    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.el2_list_rv);
        List<Eleve> eleves = dbManager.getElevesByNotInClasseId(classeId);

        EleveListAdapter2 = new EleveListAdapter2(context, dbManager.getElevesByNotInClasseId(classeId));
        listRc.setAdapter(EleveListAdapter2);
        listRc.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        if (eleves.isEmpty()) {
            Toast.makeText(context, "Aucun élève ne peut être rajouter", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(EleveListActivity2.this, ClasseDetailActivity.class);
        intent.putExtra("CLASSE_ID", classeId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

}
