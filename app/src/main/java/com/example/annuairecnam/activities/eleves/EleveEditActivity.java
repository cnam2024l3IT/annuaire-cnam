package com.example.annuairecnam.activities.eleves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private Button validerBtn, supprimerBtn;
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
        initContext();
        initCtrls();
        initListRv();
        initBtns();
        initDbManager();
        retrieveEleve();
        assignEleve();
        assignBtnsEvents();
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void assignBtnsEvents() {
        validerBtn.setOnClickListener(this::saveEleve);
        supprimerBtn.setOnClickListener(this::deleteEleve);
    }

    private void assignEleve() {
        if(eleve == null) eleve = new Eleve();

        assignCtrlsValues();
        assignListRvValues();
    }

    private void assignListRvValues() {
        listRv.setAdapter(new EleveClasseListAdapter(context, eleve, dbManager.getClassesByEleveId(eleve.get_id())));
        listRv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void assignCtrlsValues() {
        nomCtrl.setText(eleve.getNom());
        prenomCtrl.setText(eleve.getPrenom());
        dateNaissanceCtrl.setText(eleve.getDateNaissance());
        emailCtrl.setText(eleve.getEmail());
        telephoneCtrl.setText(eleve.getTelephone());
    }

    private void retrieveEleve() {
        if(getIntent().hasExtra(String.valueOf(R.string.eleve_tag)))
            eleve = getIntent().getParcelableExtra(String.valueOf(R.string.eleve_tag));
    }

    private void initDbManager() {
        dbManager = new DbManager(context).open();
    }

    private void initBtns() {
        validerBtn = findViewById(R.id.eef_valider_btn);
        supprimerBtn = findViewById(R.id.eef_supprimer_btn);
    }

    private void initListRv() {
        listRv = findViewById(R.id.eef_classes_rv);
    }

    private void initCtrls() {
        nomCtrl = findViewById(R.id.eef_nom_ctrl);
        prenomCtrl = findViewById(R.id.eef_prenom_ctrl);
        dateNaissanceCtrl = findViewById(R.id.eef_date_naissance_ctrl);
        emailCtrl = findViewById(R.id.eef_email_ctrl);
        telephoneCtrl = findViewById(R.id.eef_telephone_ctrl);
    }

    private void initContext() {
        context = this;
    }

    private void saveEleve(View v) {
        eleve.setNom(nomCtrl.getText().toString());
        eleve.setPrenom(prenomCtrl.getText().toString());
        eleve.setDateNaissance(dateNaissanceCtrl.getText().toString());
        eleve.setEmail(emailCtrl.getText().toString());
        eleve.setTelephone(telephoneCtrl.getText().toString());
        dbManager.updateEleve(eleve.get_id(), eleve);
    }

    private void deleteEleve(View v) {
        dbManager.deleteEleve(eleve.get_id());
        Toast.makeText(EleveEditActivity.this, "Elève supprimé", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(context, EleveListActivity.class));
    }

}