package com.example.annuairecnam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.activities.classes.ClasseListActivity;
import com.example.annuairecnam.activities.eleves.EleveAddActivity;
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
//        startActivity(new Intent(MainActivity.this, ClasseListActivity.class));

        findViewById(R.id.add_btn).setOnClickListener(v -> startActivity(new Intent(this, EleveAddActivity.class)));
//        findViewById(R.id.edit_btn).setOnClickListener(v -> startActivity(new Intent(this)));

        DbManager dbManager = new DbManager(this).open();

        dbManager.getAllEleves().forEach(e -> Log.d("eleve", "onCreate: " + e.get_id() + " - " + e.getNom() + " "
                + e.getPrenom() + " - " + e.getDateNaissance() + " - " + e.getEmail() + " - " + e.getTelephone()));

        dbManager.getElevesByClasseId(1).forEach(e -> Log.d("eleve_classe_1", "onCreate: " + e.get_id() + " - " + e.getNom()));

        dbManager.close();
    }

}