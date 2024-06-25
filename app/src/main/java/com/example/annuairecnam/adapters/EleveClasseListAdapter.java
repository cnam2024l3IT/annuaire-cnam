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
import com.example.annuairecnam.activities.notes.NoteActivity;
import com.example.annuairecnam.models.Classe;
import com.example.annuairecnam.models.Eleve;

import java.util.ArrayList;

public class EleveClasseListAdapter extends RecyclerView.Adapter<EleveClasseListAdapter.ViewHolder> {
    private final Context context;
    private final Eleve eleve;
    private final ArrayList<Classe> classes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final TextView intituleTv;
        private final TextView promotionTv;

        public ViewHolder(@NonNull View view) {
            super(view);
            layout = view.findViewById(R.id.ecli_rl);
            intituleTv = view.findViewById(R.id.ecli_intitule_tv);
            promotionTv = view.findViewById(R.id.ecli_promotion_tv);
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getIntituleTv() {
            return intituleTv;
        }

        public TextView getPromotionTv() {
            return promotionTv;
        }
    }

    public EleveClasseListAdapter(Context context, Eleve eleve, ArrayList<Classe> classes) {
        this.context = context;
        this.eleve = eleve;
        this.classes = classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eleve_classe_list_item, parent, false);
        return new EleveClasseListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Classe classe = classes.get(position);
        holder.getLayout().setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteActivity.class);
            intent.putExtra(String.valueOf(R.string.eleve_tag), eleve);
            intent.putExtra(String.valueOf(R.string.classe_tag), classe);
            context.startActivity(intent);
        });
        holder.getIntituleTv().setText(classe.getIntitule());
        holder.getPromotionTv().setText(classe.getPromotion());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

}
