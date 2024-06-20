package com.example.annuairecnam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.activities.matieres.MatiereDetailActivity;
import com.example.annuairecnam.models.Matiere;

import java.util.ArrayList;

public class MatiereListAdapter extends RecyclerView.Adapter<MatiereListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Matiere> matieres;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final TextView intituleTv;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.matiere_list_item_rl);
            intituleTv = view.findViewById(R.id.mli_intitule_tv);
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getIntituleTv() {
            return intituleTv;
        }
    }

    public MatiereListAdapter(Context context, ArrayList<Matiere> matieres) {
        this.context = context;
        this.matieres = matieres;
    }

    @NonNull
    @Override
    public MatiereListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matiere_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatiereListAdapter.ViewHolder holder, int position) {
        Matiere matiere = matieres.get(position);
        holder.getLayout().setOnClickListener(v -> {
            Intent intent = new Intent(context, MatiereDetailActivity.class);
            intent.putExtra("matiere_id", matiere.get_id());
            context.startActivity(intent);
        });
        holder.getIntituleTv().setText(matiere.getIntitule());
    }

    @Override
    public int getItemCount() {
        return matieres.size();
    }
}
