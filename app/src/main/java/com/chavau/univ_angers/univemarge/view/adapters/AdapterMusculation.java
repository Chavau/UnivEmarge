package com.chavau.univ_angers.univemarge.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.intermediaire.MusculationData;
import com.chavau.univ_angers.univemarge.intermediaire.Personnel;

import java.util.ArrayList;

public class AdapterMusculation extends RecyclerView.Adapter<AdapterMusculation.MonViewHolder> {

    private ArrayList<Personnel> personnels = new ArrayList<>();
    private Context context;
    private MusculationData musculationData;

    public AdapterMusculation(Context context, ArrayList<Personnel> personnels, MusculationData musculationData) {
        this.personnels = personnels;
        this.context = context;
        this.musculationData = musculationData;
    }

    public void updateList(ArrayList<Personnel> newlist){
        personnels.clear();
        personnels.addAll(newlist);
        this.notifyDataSetChanged();
    }

    static class MonViewHolder extends RecyclerView.ViewHolder {

        private CardView cardview;

        public MonViewHolder(CardView cardview) {
            super(cardview);
            this.cardview = cardview;
        }
    }

    // Déclaration d'un listener quand on clic sur une cardview

    private AdapterMusculation.Listener listener;

    public interface Listener {
        void onClick(int position);
    }

    public void set_listener(AdapterMusculation.Listener listener) {
        this.listener = listener;
    }

    @Override
    public MonViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        CardView cv = (CardView)LayoutInflater.from(context).inflate(R.layout.vue_presence_musculation,parent,false);
        return new MonViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(MonViewHolder monViewHolder, final int i) {

        CardView cv = monViewHolder.cardview;

        TextView tv_nomPers = cv.findViewById(R.id.tv_nomPers);
        TextView tv_prenomPers = cv.findViewById(R.id.tv_prenomPers);
        TextView tv_heurePasee = cv.findViewById(R.id.tv_tempsPasse);
        ImageView iv_light = cv.findViewById(R.id.iv_lighning_muscu);

        tv_nomPers.setText(personnels.get(i).getNom());
        tv_prenomPers.setText(personnels.get(i).getPrenom());
        tv_heurePasee.setText(personnels.get(i).getHeurePassee());

        // Vérification si le personnel n'pas dépassé le temps minimum
        if ( (personnels.get(i).getHeure() > musculationData.getHeure()) ||
                ((personnels.get(i).getHeure() == musculationData.getHeure()) && (personnels.get(i).getMinute() >= musculationData.getMinute()))
        ) {
            iv_light.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        else {
            iv_light.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }

        //Récupération de la position pour une suppression par la suite
        monViewHolder.itemView.setTag(i);

    }

    @Override
    public int getItemCount() {
        return personnels.size();
    }

    // Suppression d'un personnel à une position donnéee
    public void enlever(int position) {
        personnels.remove(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    // TODO: demo
    public void setPresenceDemo() {
        personnels.add(new Personnel("Le Quec", "Vincent"));
    }

}
