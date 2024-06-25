package com.example.annuairecnam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annuairecnam.R;
import com.example.annuairecnam.models.Eleve;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EleveListAdapter2 extends RecyclerView.Adapter<EleveListAdapter2.ViewHolder> {

    private final Context context;
    private final ArrayList<Eleve> eleves;
    private final Set<Long> selectedEleveIds = new HashSet<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final CheckBox checkBox;
        private final TextView intituleTv;
        private final TextView prenomTv;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.eli2_rl);
            intituleTv = view.findViewById(R.id.eli2_nom_tv);
            prenomTv = view.findViewById(R.id.eli2_prenom_tv);
            checkBox = view.findViewById(R.id.eli2_checkbox);
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getIntituleTv() {
            return intituleTv;
        }

        public TextView getPrenomTv() {
            return prenomTv;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public EleveListAdapter2(Context context, ArrayList<Eleve> eleves) {
        this.context = context;
        this.eleves = eleves;
    }

    @NonNull
    @Override
    public EleveListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eleve_list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EleveListAdapter2.ViewHolder holder, int position) {
        Eleve eleve = eleves.get(position);
        holder.getIntituleTv().setText(eleve.getNom());
        holder.getPrenomTv().setText(eleve.getPrenom());

        holder.getCheckBox().setOnCheckedChangeListener(null);
        holder.getCheckBox().setChecked(selectedEleveIds.contains(eleve.get_id()));
        holder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedEleveIds.add(eleve.get_id());
            } else {
                selectedEleveIds.remove(eleve.get_id());
            }
        });
    }


    @Override
    public int getItemCount() {
        return eleves.size();
    }

    public Set<Long> getSelectedEleveIds() {
        return selectedEleveIds;
    }

}
