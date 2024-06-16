package com.example.annuairecnam.activities.classes;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.adapters.ClasseListAdapter;
import com.example.annuairecnam.databases.DbManager;

public class ClasseListActivity extends AppCompatActivity {
    private Context context;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_classe_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("TEVA classeListActivity", "onCreate: ");
        initContext();
        initDbManager();
        initListCl();
        initAddBtn();
    }


        private void initAddBtn() {
     //   Button addBtn = findViewById(R.id.floatingActionButton_addItem);
     //   addBtn.setOnClickListener(v -> startActivity(new Intent(context, ClasseFormActivity.class)));
    }

    private void initListCl() {
        RecyclerView listCl = findViewById(R.id.RecyclerView_list);
        listCl.setAdapter(new ClasseListAdapter(context, dbManager.getAllClasses()));
        listCl.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
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