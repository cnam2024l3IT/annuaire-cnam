package com.example.annuairecnam.activities.matieres;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairecnam.R;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Matiere;

public class MatiereFormActivity extends AppCompatActivity {
    private EditText intituleEt, descriptionEt, professeurEt;
    private DbManager dbManager;
    private long matiereId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere_form);

        TextView matiereFormTv = findViewById(R.id.mf_title_tv);

        // Initialisation du gestionnaire de base de données
        dbManager = new DbManager(this).open();

        // Récupération des vues
        intituleEt = findViewById(R.id.mf_intitule_ctrl);
        descriptionEt = findViewById(R.id.mf_description_ctrl);
        professeurEt = findViewById(R.id.mf_professeur_ctrl);
        Button createBtn = findViewById(R.id.mf_save_btn);
        Button returnBtn = findViewById(R.id.mf_back_to_list_btn);

        // Récupération de l'ID de la matière si disponible
        matiereId = getIntent().getLongExtra("matiere_id", -1);

        // Si matiereId est -1, on crée une nouvelle matière
        if (matiereId == -1) {
            Matiere nouvelleMatiere = new Matiere();
            nouvelleMatiere.setIntitule("Nouvelle matière");
            matiereFormTv.setText(nouvelleMatiere.getIntitule());

            // OnClickListener pour le bouton de création
            createBtn.setOnClickListener(v -> {
                nouvelleMatiere.setIntitule(intituleEt.getText().toString());
                nouvelleMatiere.setDescription(descriptionEt.getText().toString());
                nouvelleMatiere.setProfesseur(professeurEt.getText().toString());

                // Insertion de la nouvelle matière dans la base de données
                long insertedId = dbManager.insertMatiere(nouvelleMatiere);
                if (insertedId != -1) {
                    Toast.makeText(MatiereFormActivity.this, "Nouvelle matière créée", Toast.LENGTH_SHORT).show();
                    navigateToList();
                } else {
                    Toast.makeText(MatiereFormActivity.this, "Erreur lors de la création de la matière", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si matiereId est différent de -1, on édite une matière existante
            Matiere matiereExistante = dbManager.getMatiere(matiereId);
            if (matiereExistante != null) {
                matiereFormTv.setText(matiereExistante.getIntitule());

                intituleEt.setText(matiereExistante.getIntitule());
                descriptionEt.setText(matiereExistante.getDescription());
                professeurEt.setText(matiereExistante.getProfesseur());

                // OnClickListener pour le bouton de mise à jour
                createBtn.setOnClickListener(v -> {
                    matiereExistante.setIntitule(intituleEt.getText().toString());
                    matiereExistante.setDescription(descriptionEt.getText().toString());
                    matiereExistante.setProfesseur(professeurEt.getText().toString());

                    // Mise à jour de la matière dans la base de données
                    int rowsAffected = dbManager.updateMatiere(matiereId, matiereExistante);
                    if (rowsAffected > 0) {
                        Toast.makeText(MatiereFormActivity.this, "Matière mise à jour", Toast.LENGTH_SHORT).show();
                        navigateToList();
                    } else {
                        Toast.makeText(MatiereFormActivity.this, "Erreur lors de la mise à jour de la matière", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(MatiereFormActivity.this, "Matière non trouvée dans la base de données", Toast.LENGTH_SHORT).show();
                navigateToList();
            }
        }
        returnBtn.setOnClickListener(v -> navigateToList());
    }

    // Méthode pour naviguer vers la liste des matières (MatiereListActivity)
    private void navigateToList() {
        Intent intent = new Intent(MatiereFormActivity.this, MatiereListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        // Fermeture du gestionnaire de base de données à la destruction de l'activité
        dbManager.close();
        super.onDestroy();
    }
}