package com.chavau.univ_angers.univemarge.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class AdapterPersonneInscrite extends RecyclerView.Adapter<AdapterPersonneInscrite.ViewHolderPI> {

    private Context _context;
    private ArrayList<Etudiant> _etudIns = new ArrayList<>();


    static class ViewHolderPI extends RecyclerView.ViewHolder {
        private CardView _cardview;

        public ViewHolderPI(CardView cv) {
            super(cv);
            _cardview = cv;
        }
    }
    public AdapterPersonneInscrite(Context context, ArrayList<Etudiant> listeEtudIns) {
        _context = context;
        _etudIns = listeEtudIns;
    }

    @Override
    public ViewHolderPI onCreateViewHolder(ViewGroup parent, int i) {
        CardView cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_personneinscrite,parent,false);
        return new ViewHolderPI(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderPI viewHolder, int i) {
        // Affectation de l'Id image de l'étudiant(e) inscrit(e) à l'activité
        ImageView iv = (ImageView) viewHolder._cardview.findViewById(R.id.imageview_man_woman);
        iv.setImageResource(_etudIns.get(i).get_imageId());

        // Affectation du nom de l'étudiant(e) inscrit(e) à l'activité
       TextView tv = (TextView)viewHolder._cardview.findViewById(R.id.textview_nom_persInscr);
       tv.setText(_etudIns.get(i).get_nom());

        // Affectation du prenom de l'étudiant(e) inscrit(e) à l'activité
       tv = (TextView)viewHolder._cardview.findViewById(R.id.textview_prenom_persInscr);
       tv.setText(_etudIns.get(i).get_prenom());

        // Affectation du numéro de l'étudiant(e) inscrit(e) à l'activité
       tv = (TextView)viewHolder._cardview.findViewById(R.id.textview_num_etudiant);
       tv.setText(_etudIns.get(i).get_numEtud());

        // Affectation du type d'activité de l'étudiant(e) inscrit(e) à l'activité
       tv = (TextView)viewHolder._cardview.findViewById(R.id.textview_activite_type);
       tv.setText(_etudIns.get(i).get_typeActivite());
    }

    @Override
    public int getItemCount() {
        return _etudIns.size();
    }






}
