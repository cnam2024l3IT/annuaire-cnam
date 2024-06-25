package com.example.annuairecnam.activities.classes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairecnam.R;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Classe;


public class ClasseFormActivity extends AppCompatActivity {
    private EditText intituleEt, promotionEt;
    private Button createBtn, returnBtn;
    private DbManager dbManager;
    private long classeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_form);

        TextView classeFormTv = findViewById(R.id.classe_form_tv);

        // Initialisation du gestionnaire de base de données
        dbManager = new DbManager(this).open();

        // Récupération des vues
        intituleEt = findViewById(R.id.intitule_et);
        promotionEt = findViewById(R.id.promotion_et);
        createBtn = findViewById(R.id.caf_valider_btn);
        returnBtn = findViewById(R.id.caf_retour_btn);

        // Récupération de l'ID de la classe si disponible
        classeId = getIntent().getLongExtra("classe_id", -1);

        // Si classeId est -1, on crée une nouvelle classe
        if (classeId == -1) {
            Classe nouvelleClasse = new Classe();
            nouvelleClasse.setIntitule("Nouvelle classe");
            classeFormTv.setText(nouvelleClasse.getIntitule());

            // OnClickListener pour le bouton de création
            createBtn.setOnClickListener(v -> {
                nouvelleClasse.setIntitule(intituleEt.getText().toString());
                nouvelleClasse.setPromotion(promotionEt.getText().toString());

                // Insertion de la nouvelle classe dans la base de données
                long insertedId = dbManager.insertClasse(nouvelleClasse);
                if (insertedId != -1) {
                    Toast.makeText(ClasseFormActivity.this, "Nouvelle classe créée", Toast.LENGTH_SHORT).show();
                    navigateToList();
                } else {
                    Toast.makeText(ClasseFormActivity.this, "Erreur lors de la création de la classe", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si classeId est différent de -1, on édite une classe existante
            Classe classeExistante = dbManager.getClasse(classeId);
            if (classeExistante != null) {
                classeFormTv.setText(classeExistante.getIntitule());

                intituleEt.setText(classeExistante.getIntitule());
                promotionEt.setText(classeExistante.getPromotion());

                // OnClickListener pour le bouton de mise à jour
                createBtn.setText("Mettre à jour");
                createBtn.setOnClickListener(v -> {
                    classeExistante.setIntitule(intituleEt.getText().toString());
                    classeExistante.setPromotion(promotionEt.getText().toString());

                    // Mise à jour de la classe dans la base de données
                    int rowsAffected = dbManager.updateClasse(classeId, classeExistante);
                    if (rowsAffected > 0) {
                        Toast.makeText(ClasseFormActivity.this, "Classe mise à jour", Toast.LENGTH_SHORT).show();
                        navigateToList();
                    } else {
                        Toast.makeText(ClasseFormActivity.this, "Erreur lors de la mise à jour de la classe", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(ClasseFormActivity.this, "Classe non trouvée dans la base de données", Toast.LENGTH_SHORT).show();
                navigateToList();
            }
        }
        returnBtn.setOnClickListener(v -> {
            navigateToList();
        });
    }

    // Méthode pour naviguer vers la liste des matières (MatiereListActivity)
    private void navigateToList() {
        Intent intent = new Intent(ClasseFormActivity.this, ClasseListActivity.class);
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