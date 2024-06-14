package com.example.annuairecnam;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Classe;
import com.example.annuairecnam.models.Eleve;
import com.example.annuairecnam.models.Matiere;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DbManager dbManager;
    private Context context;

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
        context = this;
        dbManager = new DbManager(context);

        dbManager.open();

//        Classe classe = new Classe("L3 informatique", "2023-2024");
//        dbManager.insertClasse(classe);

//        ArrayList<Eleve> eleves = new ArrayList<>();
//        eleves.add(new Eleve("DOE", "John", "2004-06-13", "djohn@auditeur.cnam"));
//        eleves.add(new Eleve("IAM", "Groot", "2004-06-13", "igroot@auditeur.cnam"));
//        eleves.add(new Eleve("BRUH", "Lee", "2004-06-13", "blee@auditeur.cnam"));
//        eleves.forEach(e -> dbManager.insertEleve(e));

//        Matiere matiere = new Matiere("Mobile", "Développement d'application mobile", "RIVET Vaki");
//        dbManager.insertMatiere(matiere);

        ArrayList<Classe> classesList = dbManager.getAllClasses();
        classesList.forEach(c -> Log.d("classes list", "onCreate: " + c.get_id() + " - " + c.getIntitule()));

        ArrayList<Eleve> elevesList = dbManager.getAllEleves();
        elevesList.forEach(e -> Log.d("eleves list", "onCreate: " + e.get_id() + " - " + e.getNom() + " " + e.getPrenom()));

        ArrayList<Matiere> matieresList = dbManager.getAllMatieres();
        matieresList.forEach(m -> Log.d("matieres list", "onCreate: " + m.get_id() + " - " + m.getIntitule()));
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

}