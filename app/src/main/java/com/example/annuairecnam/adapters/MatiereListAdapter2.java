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
import com.example.annuairecnam.models.Matiere;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MatiereListAdapter2 extends RecyclerView.Adapter<MatiereListAdapter2.ViewHolder> {

    private final Context context;
    private final ArrayList<Matiere> matieres;
    private final Set<Long> selectedMatiereIds = new HashSet<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout layout;
        private final TextView intituleTv;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.matiere2_list_item_rl);
            intituleTv = view.findViewById(R.id.mli_intitule_tv);
            checkBox = view.findViewById(R.id.matiere_checkbox);
        }


        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getIntituleTv() {
            return intituleTv;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public MatiereListAdapter2(Context context, ArrayList<Matiere> matieres) {
        this.context = context;
        this.matieres = matieres;
    }

    @NonNull
    @Override
    public MatiereListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matiere_list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatiereListAdapter2.ViewHolder holder, int position) {
        Matiere matiere = matieres.get(position);
//        holder.getLayout().setOnClickListener(v -> {
//            Intent intent = new Intent(context, MatiereDetailActivity.class);
//            intent.putExtra("matiere_id", matiere.get_id());
//            context.startActivity(intent);
//        });
        holder.getIntituleTv().setText(matiere.getIntitule());
        holder.getIntituleTv().setText(matiere.getIntitule());
        holder.getCheckBox().setOnCheckedChangeListener(null);
        holder.getCheckBox().setChecked(selectedMatiereIds.contains(matiere.get_id()));
        holder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedMatiereIds.add(matiere.get_id());
            } else {
                selectedMatiereIds.remove(matiere.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return matieres.size();
    }

    public Set<Long> getSelectedMatiereIds() {
        return selectedMatiereIds;
    }

}
