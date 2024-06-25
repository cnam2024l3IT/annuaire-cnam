package com.example.annuairecnam.activities.eleves;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.adapters.EleveClasseListAdapter;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Eleve;

public class EleveEditActivity extends AppCompatActivity {
    private Context context;
    private DbManager dbManager;
    private EditText nomCtrl, prenomCtrl, dateNaissanceCtrl, emailCtrl, telephoneCtrl;
    private RecyclerView listRv;
    private Button validerBtn;
    private Eleve eleve;

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

        context = this;

        nomCtrl = findViewById(R.id.eef_nom_ctrl);
        prenomCtrl = findViewById(R.id.eef_prenom_ctrl);
        dateNaissanceCtrl = findViewById(R.id.eef_date_naissance_ctrl);
        emailCtrl = findViewById(R.id.eef_email_ctrl);
        telephoneCtrl = findViewById(R.id.eef_telephone_ctrl);
        listRv = findViewById(R.id.eef_classes_rv);


        validerBtn = findViewById(R.id.eef_valider_btn);

        dbManager = new DbManager(context).open();
        if(getIntent().hasExtra(String.valueOf(R.string.eleve_tag))) {
            eleve = getIntent().getParcelableExtra(String.valueOf(R.string.eleve_tag));
            nomCtrl.setText(eleve.getNom());
            prenomCtrl.setText(eleve.getPrenom());
            dateNaissanceCtrl.setText(eleve.getDateNaissance());
            emailCtrl.setText(eleve.getEmail());
            telephoneCtrl.setText(eleve.getTelephone());
            listRv.setAdapter(new EleveClasseListAdapter(context, eleve, dbManager.getClassesByEleveId(eleve.get_id())));
            listRv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        }

        validerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Eleve eleve = new Eleve(nomCtrl.getText().toString(), prenomCtrl.getText().toString(),
//                        dateNaissanceCtrl.getText().toString(), emailCtrl.getText().toString(), telephoneCtrl.getText().toString());
                dbManager.updateEleve(eleve.get_id(), eleve);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}