package com.example.annuairecnam.activities.matieres;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.activities.classes.ClasseListActivity;
import com.example.annuairecnam.activities.eleves.EleveListActivity;
import com.example.annuairecnam.adapters.MatiereListAdapter;
import com.example.annuairecnam.databases.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MatiereListActivity extends AppCompatActivity {

    private Context context;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matiere_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initContext();
        initDbManager();
        initListRc();
        initAddBtn();
        initListMatBtn();
        initListEleBtn();
        initListClBtn();
    }

    private void initAddBtn() {
        FloatingActionButton addBtn = findViewById(R.id.ml_add_btn);
        addBtn.setOnClickListener(v -> startActivity(new Intent(context, MatiereFormActivity.class)));
    }

    private void initListClBtn() {
        Button lisBtn = findViewById(R.id.ml_classes_btn);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, ClasseListActivity.class)));
    }

    private void initListMatBtn() {
        Button lisBtn = findViewById(R.id.ml_matieres_btn);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, MatiereListActivity.class)));
    }

    private void initListEleBtn() {
        Button lisBtn = findViewById(R.id.ml_eleves_btn);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, EleveListActivity.class)));
    }

    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.ml_list_rv);
        listRc.setAdapter(new MatiereListAdapter(context, dbManager.getAllMatieres()));
        listRc.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void initDbManager() {
        dbManager = new DbManager(context).open();
    }

    private void initContext() {
        context = this;
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}