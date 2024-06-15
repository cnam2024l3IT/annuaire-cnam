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
import com.example.annuairecnam.adapters.MatiereListAdapter;
import com.example.annuairecnam.databases.DbManager;

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
    }

    private void initAddBtn() {
        Button addBtn = findViewById(R.id.add_matiere_btn);
        addBtn.setOnClickListener(v -> startActivity(new Intent(context, MatiereFormActivity.class)));
    }

    private void initListRc() {
        RecyclerView listRc = findViewById(R.id.matiere_list_rc);
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