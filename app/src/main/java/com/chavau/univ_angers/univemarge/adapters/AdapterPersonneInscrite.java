package com.chavau.univ_angers.univemarge.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;



public class AdapterPersonneInscrite extends RecyclerView.Adapter<AdapterPersonneInscrite.ViewHolderPI> {

    private Context _context;
    private ArrayList<Etudiant> _etudIns = new ArrayList<>();

    private final static String nomListePresence = "Liste Presence";

    public static int cpt = 1;

    public enum VueChoix {PI, NS, MS};// pour le choix de vue qui va être gonflée,
                                     // PI correspond à la vue des personnes inscrites
                                    // NS correspond à la vue d'une nouvelle séance
                                    // MS correspond à la vue d'une modification de séance
    private static VueChoix _vueChoix;
    private ArrayList<String> _listePresence = new ArrayList<>();

    // Lors d'un clic sur un cardview dans l'activité "Nouvelle Séance"

    private Listener _listener;

    public interface Listener {
        void onClick(int position);
    }

    public void set_listener(Listener listener) {
        this._listener = listener;
    }

    static class ViewHolderPI extends RecyclerView.ViewHolder {
        private CardView _cardview;

        public ViewHolderPI(CardView cv) {
            super(cv);
            _cardview = cv;
        }
    }
    public AdapterPersonneInscrite(Context context, ArrayList<Etudiant> listeEtudIns, VueChoix vc) {
        _context = context;
        _etudIns = listeEtudIns;
        _vueChoix = vc;
    }

    @Override
    public ViewHolderPI onCreateViewHolder(ViewGroup parent, int i) {
        CardView cv = new CardView(_context);
        switch (_vueChoix) {
            case PI:
                cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_personneinscrite,parent,false);
                break;
            case NS:
                cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_creer_seance,parent,false);
                break;
            case MS:
                cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_modifier_seance,parent,false);
                break;

            default:
                Toast.makeText(_context,"La vue selectionnée n'a pas été créee ou gonflée",Toast.LENGTH_SHORT).show();
        }
        return new ViewHolderPI(cv);
    }

    public static String getNomListePresence() {
        return nomListePresence;
    }

    public ArrayList<Etudiant> get_etudIns() {
        return _etudIns;
    }

    public ArrayList<String> get_listePresence() {
        return _listePresence;
    }

    public void set_listePresence(ArrayList<String> listePresence) {
        this._listePresence = listePresence;
    }

    @Override
    public void onBindViewHolder(ViewHolderPI viewHolder, final int i) {

        ImageView iv_light = (ImageView)viewHolder._cardview.findViewById(R.id.iv_lighning);
        ImageView iv_pict = (ImageView)viewHolder._cardview.findViewById(R.id.imageview_man_woman);
        TextView tv_nom = (TextView)viewHolder._cardview.findViewById(R.id.textview_nom_persInscr);
        TextView tv_prenom = (TextView)viewHolder._cardview.findViewById(R.id.textview_prenom_persInscr);
        TextView tv_numetu = (TextView)viewHolder._cardview.findViewById(R.id.textview_num_etudiant);
        TextView tv_typeact = (TextView)viewHolder._cardview.findViewById(R.id.textview_activite_type);

        // Affectation de l'Id image de l'étudiant(e) inscrit(e) à l'activité
        iv_pict.setImageResource(_etudIns.get(i).get_imageId());

        // Affectation du nom de l'étudiant(e) inscrit(e) à l'activité
        tv_nom.setText(_etudIns.get(i).get_nom());

        // Affectation du prenom de l'étudiant(e) inscrit(e) à l'activité
        tv_prenom.setText(_etudIns.get(i).get_prenom());

        // Affectation du numéro de l'étudiant(e) inscrit(e) à l'activité
        tv_numetu.setText(_etudIns.get(i).get_numEtud());

        // Affectation du type d'activité de l'étudiant(e) inscrit(e) à l'activité
        tv_typeact.setText(_etudIns.get(i).get_typeActivite());

        switch (_vueChoix) {

            case PI:

                iv_light.setColorFilter(Color.BLACK, PorterDuff.Mode.DST_IN);

                break;

            case NS:

                if (_listePresence.size() > 0 && _listePresence.contains(_etudIns.get(i).get_numEtud())) {
                  //  Log.i("Position",String.valueOf(_listePresence.size()));
                    iv_light.setColorFilter(Color.GREEN,PorterDuff.Mode.DST_IN);
                }
                else {
                   iv_light.setColorFilter(Color.RED, PorterDuff.Mode.DST_IN);
                }

                viewHolder._cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!_listePresence.contains(_etudIns.get(i).get_numEtud())) {
                            if (_listener != null) {
                                _listener.onClick(i);
                            }
                        }
                    }
                });

                break;

            case MS:

                if (_listePresence.size() > 0 && _listePresence.contains(_etudIns.get(i).get_numEtud())) {
                    iv_light.setColorFilter(Color.GREEN,PorterDuff.Mode.DST_IN);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return _etudIns.size();
    }

    public void setPresence(int i) {
        _etudIns.get(i).set_etat(Etudiant.STATUE_ETUDIANT.PRESENT);
    }

    public void ajoutPosition(int position) {
        _listePresence.add(_etudIns.get(position).get_numEtud());

        notifyDataSetChanged();
    }



}
