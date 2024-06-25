package com.example.annuairecnam.activities.eleves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.annuairecnam.R;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Eleve;

public class EleveAddActivity extends AppCompatActivity {
    private EditText nomCtrl, prenomCtrl, dateNaissanceCtrl, emailCtrl, telephoneCtrl;
    private Button validerBtn, retourBtn;
    private Context context;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleve_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initContext();
        initCtrls();
        initBtns();
        initDbManager();
        assignBtnsEvents();

    }

    private void initBtns() {
        validerBtn = findViewById(R.id.ea_save_btn);
        retourBtn = findViewById(R.id.ea_back_to_list_btn);
    }

    private void assignBtnsEvents() {
        validerBtn.setOnClickListener(this::saveEleve);
        retourBtn.setOnClickListener(this::goToList);
    }

    private void initDbManager() {
        dbManager = new DbManager(context).open();
    }

    private void initCtrls() {
        nomCtrl = findViewById(R.id.ea_nom_ctrl);
        prenomCtrl = findViewById(R.id.ea_prenom_ctrl);
        dateNaissanceCtrl = findViewById(R.id.ea_date_naissance_ctrl);
        emailCtrl = findViewById(R.id.ea_email_ctrl);
        telephoneCtrl = findViewById(R.id.ea_telephone_ctrl);
    }

    private void initContext() {
        context = this;
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void saveEleve(View v) {
        Eleve eleve = new Eleve(nomCtrl.getText().toString(), prenomCtrl.getText().toString(),
                dateNaissanceCtrl.getText().toString(), emailCtrl.getText().toString(), telephoneCtrl.getText().toString());
        dbManager.insertEleve(eleve);
        goToList(v);
    }

    private void goToList(View v) {
        startActivity(new Intent(context, EleveListActivity.class));
    }
}
