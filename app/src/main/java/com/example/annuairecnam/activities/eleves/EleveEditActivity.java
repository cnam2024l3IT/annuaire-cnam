package com.example.annuairecnam.activities.eleves;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.R;
import com.example.annuairecnam.activities.matieres.MatiereDetailActivity;
import com.example.annuairecnam.databases.DbManager;

public class EleveEditActivity extends AppCompatActivity {
    private Context context;
    private DbManager dbManager;
    private Button btnSupprimerEleve;
    private long eleveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleve_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSupprimerEleve = findViewById(R.id.eef_supprimer_btn);

        eleveId = getIntent().getLongExtra("eleve_id", -1);

        btnSupprimerEleve.setOnClickListener(v -> {
            dbManager.deleteEleve(eleveId);
            Toast.makeText(EleveEditActivity.this, "Eleve supprimé", Toast.LENGTH_SHORT).show();
        });

    }


}