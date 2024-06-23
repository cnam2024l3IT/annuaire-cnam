package com.example.annuairecnam.activities.eleves;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairecnam.R;
import com.example.annuairecnam.models.Eleve;

public class EleveFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve_form);

        TextView eleveFormTv = findViewById(R.id.eleve_form_tv);

        // Récupère l'objet Eleve envoyé en extra depuis EleveListActivity
        Eleve eleve = getIntent().getParcelableExtra(getString(R.string.eleve_tag));

        // Si il trouve un éléve -> Affiche le nom de l'élève dans le TextView
        if (eleve != null) {
            eleveFormTv.setText(eleve.getNom());
        } else {
            eleveFormTv.setText(getString(R.string.eleve_form_txt)); // Si non, Affiche  le texte par défaut du widget
        }
    }
}
