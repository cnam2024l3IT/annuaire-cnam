package com.example.annuairecnam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.activities.matieres.MatiereListActivity;
import com.example.annuairecnam.databases.DbManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Context c = this;
        DbManager dbManager = new DbManager(c);
        dbManager.open();

//        dbManager.insertMatiere(new Matiere("Sytèmes d'exploitation", "Système d'exploitation", "Luc Howan"));
//        dbManager.insertMatiere(new Matiere("Programmation avancée", "Programmation avancée", "Benjeamin De Dardel"));
//        dbManager.insertMatiere(new Matiere("Réseaux", "Réseaux", "Anne Sophie QQCH"));

        dbManager.getAllMatieres().forEach(m -> Log.d("matiere", "onCreate: " + m.get_id() + " - " + m.getIntitule()));
        Button testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener(v -> startActivity(new Intent(c, MatiereListActivity.class)));

    }

}