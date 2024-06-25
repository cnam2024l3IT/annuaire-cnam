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
import com.example.annuairecnam.activities.eleves.EleveEditActivity;
import com.example.annuairecnam.models.Eleve;

import java.util.ArrayList;

public class EleveListAdapter extends RecyclerView.Adapter<EleveListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Eleve> eleves;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final TextView intituleTv;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.eli_rl);
            intituleTv = view.findViewById(R.id.eli_intitule_tv);

        }
        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getIntituleTv() {
            return intituleTv;
        }
    }

    public EleveListAdapter(Context context, ArrayList<Eleve> eleves) {
        this.context = context;
        this.eleves = eleves;
    }

    @NonNull
    @Override
    public EleveListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eleve_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EleveListAdapter.ViewHolder holder, int position) {
        Eleve eleve = eleves.get(position);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, EleveEditActivity.class);
            intent.putExtra(String.valueOf(R.string.eleve_tag), eleve);
            context.startActivity(intent);
        });
        holder.getIntituleTv().setText(eleve.getNom());
    }

    @Override
    public int getItemCount() {
        return eleves.size();
    }
}