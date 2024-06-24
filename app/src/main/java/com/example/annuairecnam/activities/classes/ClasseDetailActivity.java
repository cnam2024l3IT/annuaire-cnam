package com.example.annuairecnam.activities.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.adapters.MatiereListAdapter;
import com.example.annuairecnam.databases.DbManager;
import com.example.annuairecnam.models.Classe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClasseDetailActivity extends AppCompatActivity {
    private EditText intituleEt, promotionEt;
    private Context context;
    private DbManager dbManager;
    private Classe classe;
    private long classeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_detail);

        classeId = getIntent().getLongExtra("CLASSE_ID", -1);

        dbManager = new DbManager(this);
        dbManager.open();

        initContext();
        initListEle();
        initListMat();


        intituleEt = findViewById(R.id.intitule_et);
        promotionEt = findViewById(R.id.promotion_et);
        FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.add_btn);
        FloatingActionButton updateBtn = (FloatingActionButton) findViewById(R.id.update_btn);
        FloatingActionButton deleteBtn = (FloatingActionButton) findViewById(R.id.delete_btn);
        FloatingActionButton returnBtn = (FloatingActionButton) findViewById(R.id.return_btn);

        if (classeId != -1) {
            classe = dbManager.getClasse(classeId);
            if (classe != null) {
                intituleEt.setText(classe.getIntitule());
                promotionEt.setText(classe.getPromotion());
            }
        }

        updateBtn.setOnClickListener(v -> {
            classe.setIntitule(intituleEt.getText().toString());
            classe.setPromotion(promotionEt.getText().toString());

            dbManager.updateClasse(classeId, classe);
            Toast.makeText(ClasseDetailActivity.this, "Classe mise à jour", Toast.LENGTH_SHORT).show();
            navigateToList();
        });

        deleteBtn.setOnClickListener(v -> {
            dbManager.deleteClasse(classeId);
            Toast.makeText(ClasseDetailActivity.this, "Classe supprimée", Toast.LENGTH_SHORT).show();
            navigateToList();
        });

        returnBtn.setOnClickListener(v -> {
            navigateToClasseList();
        });

    }

    private void initContext() {
        context = this;
    }

    private void initListEle() {
        RecyclerView listEle = findViewById(R.id.recyclerViewLstEle);
       // listEle.setAdapter(new EleveListAdapter(context, dbManager.getAllEleves()));
       // listEle.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void initListMat() {
        RecyclerView listMat = findViewById(R.id.recyclerViewLstMat);
        listMat.setAdapter(new MatiereListAdapter(context, dbManager.getMatieresByClasseId(classeId)));
        listMat.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void navigateToList() {
        Intent intent = new Intent(ClasseDetailActivity.this, ClasseDetailActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToClasseList() {
         Intent intent = new Intent(this, ClasseListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}
