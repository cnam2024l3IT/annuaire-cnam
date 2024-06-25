package com.example.annuairecnam.activities.matieres;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairecnam.R;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Matiere;

public class MatiereDetailActivity extends AppCompatActivity {
    private EditText intituleEt, descriptionEt, professeurEt;
    private Button updateBtn, deleteBtn, returnBtn;
    private DbManager dbManager;
    private Matiere matiere;
    private long matiereId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere_detail);

        dbManager = new DbManager(this).open();

        intituleEt = findViewById(R.id.md_intitule_ctrl);
        descriptionEt = findViewById(R.id.md_description_ctrl);
        professeurEt = findViewById(R.id.md_professeur_ctrl);
        updateBtn = findViewById(R.id.md_save_btn);
        deleteBtn = findViewById(R.id.md_delete_btn);
        returnBtn = findViewById(R.id.md_back_to_list_btn);

        matiereId = getIntent().getLongExtra("matiere_id", -1);

        if (matiereId != -1) {
            matiere = dbManager.getMatiere(matiereId);
            if (matiere != null) {
                intituleEt.setText(matiere.getIntitule());
                descriptionEt.setText(matiere.getDescription());
                professeurEt.setText(matiere.getProfesseur());
            }
        }

        updateBtn.setOnClickListener(v -> {
            matiere.setIntitule(intituleEt.getText().toString());
            matiere.setDescription(descriptionEt.getText().toString());
            matiere.setProfesseur(professeurEt.getText().toString());

            dbManager.updateMatiere(matiereId, matiere);
            Toast.makeText(MatiereDetailActivity.this, "Matière mise à jour", Toast.LENGTH_SHORT).show();
            navigateToList();
        });

        deleteBtn.setOnClickListener(v -> {
            dbManager.deleteMatiere(matiereId);
            Toast.makeText(MatiereDetailActivity.this, "Matière supprimée", Toast.LENGTH_SHORT).show();
            navigateToList();
        });

        returnBtn.setOnClickListener(v -> {
            navigateToList();
        });
    }

    private void navigateToList() {
        Intent intent = new Intent(MatiereDetailActivity.this, MatiereListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}