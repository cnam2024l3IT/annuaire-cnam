package com.example.annuairecnam.activities.classes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairecnam.R;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Classe;

public class ClasseDetailActivity extends AppCompatActivity {
    private EditText intituleEt, promotionEt; // Définir les champs EditText pour l'intitulé et la promotion
    private Button updateBtn, deleteBtn, returnBtn; // Définir les boutons pour mettre à jour, supprimer et retourner
    private DbManager dbManager; // Gestionnaire de la base de données
    private Classe classe; // Instance de la classe à afficher
    private long classeId; // Identifiant de la classe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_detail);

        dbManager = new DbManager(this).open(); // Initialisation du gestionnaire de base de données

        // Liaison des vues avec les éléments du layout
        intituleEt = findViewById(R.id.intitule_et);
        promotionEt = findViewById(R.id.promotion_et);
        updateBtn = findViewById(R.id.update_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        returnBtn = findViewById(R.id.return_btn);

        // Récupération de l'ID de la classe depuis l'intent
        classeId = getIntent().getLongExtra("CLASSE_ID", -1);

        // Vérification si l'ID est valide et récupération des détails de la classe depuis la base de données
        if (classeId != -1) {
            classe = dbManager.getClasse(classeId);
            if (classe != null) {
                intituleEt.setText(classe.getIntitule());
                promotionEt.setText(classe.getPromotion());
            }
        }

        // Gestionnaire de clic sur le bouton de mise à jour
        updateBtn.setOnClickListener(v -> {
            if (classe != null) {
                classe.setIntitule(intituleEt.getText().toString());
                classe.setPromotion(promotionEt.getText().toString());

                dbManager.updateClasse(classeId, classe); // Mise à jour de la classe dans la base de données
                Toast.makeText(ClasseDetailActivity.this, "Classe mise à jour", Toast.LENGTH_SHORT).show();
                navigateToList(); // Redirection vers la liste des classes
            }
        });

        // Gestionnaire de clic sur le bouton de suppression
        deleteBtn.setOnClickListener(v -> {
            dbManager.deleteClasse(classeId); // Suppression de la classe depuis la base de données
            Toast.makeText(ClasseDetailActivity.this, "Classe supprimée", Toast.LENGTH_SHORT).show();
            navigateToList(); // Redirection vers la liste des classes
        });

        // Gestionnaire de clic sur le bouton de retour
        returnBtn.setOnClickListener(v -> {
            navigateToList(); // Redirection vers la liste des classes
        });
    }

    // Méthode pour naviguer vers la liste des classes
    private void navigateToList() {
        Intent intent = new Intent(ClasseDetailActivity.this, ClasseListActivity.class);
        startActivity(intent);
        finish(); // Fermeture de l'activité actuelle
    }

    @Override
    protected void onDestroy() {
        dbManager.close(); // Fermeture du gestionnaire de base de données
        super.onDestroy();
    }
}
