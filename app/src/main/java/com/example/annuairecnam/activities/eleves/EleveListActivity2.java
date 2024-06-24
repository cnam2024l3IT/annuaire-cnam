package com.example.annuairecnam.activities.eleves;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.annuairecnam.adapters.EleveListAdapter2;
import com.example.annuairecnam.databases.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        Log.d("Teva classeId", String.valueOf(classeId));
        initListRc();


        // Initialisation spécifique si classeId est défini
        if (classeId != -1) {
            initAddClElBtn();
        }
    }

    private void initAddClElBtn() {
        FloatingActionButton addBtn = findViewById(R.id.floatingActionButton_addItem);
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
                            }
                            Toast.makeText(EleveListActivity2.this, "Élèves ajoutés", Toast.LENGTH_SHORT).show();
                            // Retour à la ClasseDetail (si nécessaire)
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }




    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.recyclerView_list2);
        EleveListAdapter2 = new EleveListAdapter2(context, dbManager.getAllEleves());
        listRc.setAdapter(EleveListAdapter2);
        listRc.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void initDbManager() {
        dbManager = new DbManager(context).open();
    }

    private void initContext() {
        context = this;
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}
