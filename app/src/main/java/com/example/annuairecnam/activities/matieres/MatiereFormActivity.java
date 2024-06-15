package com.example.annuairecnam.activities.matieres;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.R;
import com.example.annuairecnam.models.Matiere;

public class MatiereFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matiere_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView matiereFormTv = findViewById(R.id.matiere_form_tv);
        Matiere matiere = new Matiere();
        matiere.setIntitule("Nouvelle matière");

        if (getIntent().hasExtra(String.valueOf(R.string.matiere_tag)))
            matiere = getIntent().getParcelableExtra(String.valueOf(R.string.matiere_tag));

        if (matiere != null) matiereFormTv.setText(matiere.getIntitule());
    }
}