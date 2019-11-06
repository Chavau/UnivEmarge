package com.chavau.univ_angers.univemarge.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.intermediaire.Cours;
import com.chavau.univ_angers.univemarge.view.activities.ListePersonnesInscrites;

import java.util.ArrayList;
import java.util.List;

public class AdapterCours extends RecyclerView.Adapter<AdapterCours.ViewHolderCours> {

    private List<Cours> _cours;
    private Context _context; //pouvoir utiliser des layouts plus tard

    private final static String nomAct = "NOM_ACTIVITE";
    private final static String listeEtud = "LISTE_ETUDIANT";

    public AdapterCours(Context context, ArrayList<Cours> cours) {
        _context = context;
        _cours = cours;
    }

    static class ViewHolderCours extends RecyclerView.ViewHolder {

        private CardView _cardview;

        public ViewHolderCours(CardView cv) {
            super(cv);
            _cardview = cv;
        }

    }

    @Override
    public ViewHolderCours onCreateViewHolder(ViewGroup parent, int i) {
        CardView cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_cours,parent,false);
        return new ViewHolderCours(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderCours viewHolderCours, final int i) {
        final Cours cours = _cours.get(i);
        final CardView cardview = viewHolderCours._cardview;

        //Recuperation du TEXTVIEW pour l'intitulé de cours
        TextView tv = (TextView) cardview.findViewById(R.id.tv_intituleCours);
        //Recuperation de donnée correspondante
        tv.setText(cours.get_intitule());

        // Affectation des différentes textview avec les bonnes données
        tv = (TextView) cardview.findViewById(R.id.tv_jourSemaine);
        tv.setText(cours.get_jourDeLaSemaine());

        tv = (TextView) cardview.findViewById(R.id.tv_date);
        tv.setText(cours.get_date());

        tv = (TextView) cardview.findViewById(R.id.tx_details);
        tv.setText("De "+cours.get_heureDebut()+" à "+cours.get_heureFin());

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Préparation des données à envoyer au deuxième activité
                Intent intent = new Intent(_context, ListePersonnesInscrites.class);
                intent.putExtra(AdapterCours.getNomAct(),((TextView) cardview.findViewById(R.id.tv_intituleCours)).getText().toString());

                // Envoie la liste des étudiant(e)s inscrit(e)s dans l'activité
                intent.putParcelableArrayListExtra(AdapterCours.getListeEtud(),cours.get_listeEtudiantInscrit());

                //Commencer la deuxième activité
                _context.startActivity(intent);
            }
        });

    }

    public void setListeCours(ArrayList<Cours> lc) {
        _cours = lc;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return _cours.size();
    }

    public static String getNomAct() {
        return nomAct;
    }

    public static String getListeEtud() {
        return listeEtud;
    }
}
