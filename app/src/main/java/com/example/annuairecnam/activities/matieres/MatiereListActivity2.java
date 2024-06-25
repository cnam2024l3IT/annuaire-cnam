package com.example.annuairecnam.activities.matieres;

import static com.example.annuairecnam.databases.contracts.DataContract.SQL_MATIERES_NOIN_CLASSE_ID;

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
import com.example.annuairecnam.adapters.MatiereListAdapter2;
import com.example.annuairecnam.databases.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        addBtn = findViewById(R.id.floatingActionButton_addItem);

        // Récupération du classeId depuis l'intent
        classeId = getIntent().getLongExtra("CLASSE_ID", -1);

        initListRc();

        // Initialisation spécifique si classeId est défini
        if (classeId != -1) {
            initAddClMatBtn();
        }
    }


    private void initAddClMatBtn() {
        FloatingActionButton addBtn = findViewById(R.id.floatingActionButton_addItem);
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
        RecyclerView listRc = findViewById(R.id.recyclerView_list);

        Log.d("TEVA SQL_MATIERES_NOIN_CLASSE_ID ",SQL_MATIERES_NOIN_CLASSE_ID);
        MatiereListAdapter2 = new MatiereListAdapter2(context, dbManager.getMatieresByNotInClasseId(classeId));
        listRc.setAdapter(MatiereListAdapter2);
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
