package com.example.annuairecnam.activities.eleves;

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
import com.example.annuairecnam.activities.matieres.MatiereListActivity;
import com.example.annuairecnam.adapters.EleveListAdapter;
import com.example.annuairecnam.databases.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EleveListActivity extends AppCompatActivity {
    private Context context;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleve_list);
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
        FloatingActionButton addBtn = findViewById(R.id.floatingActionButton_addItem);
        addBtn.setOnClickListener(v -> startActivity(new Intent(context, EleveFormActivity.class)));
    }
    private void initListClBtn() {
        Button lisBtn = findViewById(R.id.button_classes);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, ClasseListActivity.class)));
    }

    private void initListMatBtn() {
        Button lisBtn = findViewById(R.id.button_matieres);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, MatiereListActivity.class)));
    }

    private void initListEleBtn() {
        Button lisBtn = findViewById(R.id.button_eleves);
        lisBtn.setOnClickListener(v -> startActivity(new Intent(context, EleveListActivity.class)));
    }
    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.RecyclerView_list);
        listRc.setAdapter(new EleveListAdapter(context, dbManager.getAllEleves()));
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
