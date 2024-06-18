package com.example.annuairecnam.activities.eleves;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.R;
import com.example.annuairecnam.models.Eleve;

public class EleveFormActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleve_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView eleveFormTv = findViewById(R.id.eleve_form_tv);
        Eleve eleve = new Eleve();
        eleve.setPrenom("Nouvel Eleve");

        if (getIntent().hasExtra(String.valueOf(R.string.eleve_tag)))
            eleve = getIntent().getParcelableExtra(String.valueOf(R.string.eleve_tag));

        if (eleve != null) eleveFormTv.setText(eleve.getNom());
    }
}
