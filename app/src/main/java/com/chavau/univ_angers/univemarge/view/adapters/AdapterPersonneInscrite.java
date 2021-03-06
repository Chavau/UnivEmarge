package com.chavau.univ_angers.univemarge.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;

import java.util.ArrayList;

public class AdapterPersonneInscrite extends RecyclerView.Adapter<AdapterPersonneInscrite.ViewHolderPI> {

    private Context _context;
    private boolean _isModeEnseignant; // Si true alors il faut le menu pour modifier à la main (false par défaut)
    private ArrayList<Etudiant> _etudIns ; //= new ArrayList<>();

    static int ABSENT   = 0;
    static int PRESENT  = 1;
    static int EXCUSE   = 2;
    static int RETARD   = 3;

    //private final static String nomListePresence = "Liste Presence";

    //public enum VueChoix {NS, MS}
    // TODO : dev :ménage
    ;// pour le choix de vue qui va être gonflée,
    // NS correspond à la vue d'une nouvelle séance
    // MS correspond à la vue d'une modification de séance
    //private static VueChoix _vueChoix;

    // Lors d'un clic sur un cardview dans l'activité "Nouvelle Séance"

    //private Listener _listener;

    /*public interface Listener {
        void onClick(int position);
    }*/

   /* public void set_listener(Listener listener) {
        this._listener = listener;
    }*/

    /**
     * Chargement de le vue de chaque ligne
     */
    static class ViewHolderPI extends RecyclerView.ViewHolder {
        private CardView _cardview;

        public ViewHolderPI(CardView cv) {
            super(cv);
            _cardview = cv;
        }
    }

    public AdapterPersonneInscrite(Context context, ArrayList<Etudiant> listeEtudIns) {
        _context = context;
        _isModeEnseignant = false;
        _etudIns = listeEtudIns;
    }

    @Override
    public ViewHolderPI onCreateViewHolder(ViewGroup parent, int i) {
        //CardView cv = new CardView(_context);

        CardView cv = (CardView) LayoutInflater.from(_context).inflate(R.layout.vue_modifier_seance, parent, false);

        return new ViewHolderPI(cv);
    }

    /*public static String getNomListePresence() {
        return nomListePresence;
    }*/

    /*public ArrayList<Etudiant> get_etudIns() {
        return _etudIns;
    }*/

    @Override
    public void onBindViewHolder(final ViewHolderPI viewHolder, final int i) {

        ImageView iv_light = (ImageView) viewHolder._cardview.findViewById(R.id.iv_lighning);
        ImageView iv_pict = (ImageView) viewHolder._cardview.findViewById(R.id.imageview_man_woman);
        TextView tv_nom = (TextView) viewHolder._cardview.findViewById(R.id.textview_nom_persInscr);
        TextView tv_prenom = (TextView) viewHolder._cardview.findViewById(R.id.textview_prenom_persInscr);
        TextView tv_numetu = (TextView) viewHolder._cardview.findViewById(R.id.textview_num_etudiant);
        TextView tv_typeact = (TextView) viewHolder._cardview.findViewById(R.id.textview_activite_type); // TODO : voir si on garde

        // Affectation de l'Id image de l'étudiant(e) inscrit(e) à l'activité
        iv_pict.setImageResource(R.drawable.man);

        // Affectation du nom de l'étudiant(e) inscrit(e) à l'activité
        tv_nom.setText(_etudIns.get(i).getNom());

        // Affectation du prenom de l'étudiant(e) inscrit(e) à l'activité
        tv_prenom.setText(_etudIns.get(i).getPrenom());

        // Affectation du numéro de l'étudiant(e) inscrit(e) à l'activité
        tv_numetu.setText(Integer.toString(_etudIns.get(i).getNumeroEtudiant()));

        // Affectation du type d'activité de l'étudiant(e) inscrit(e) à l'activité
        //tv_typeact.setText(_etudIns.get(i).get_typeActivite());

        //Coloration de l'ampoule en fonction de la présence d'un(e) étudiant(e)

        //if (_etudIns.get(i).get_etat() == Etudiant.STATUE_ETUDIANT.ABSENT) {

        iv_light.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        //}

       /* else if (_etudIns.get(i).get_etat() == Etudiant.STATUE_ETUDIANT.PRESENT) {

            iv_light.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
        }

        else if (_etudIns.get(i).get_etat() == Etudiant.STATUE_ETUDIANT.RETARD) {

            iv_light.setColorFilter(Color.BLUE,PorterDuff.Mode.SRC_ATOP);
        }

        else {
            iv_light.setColorFilter(Color.rgb(252, 186, 3), PorterDuff.Mode.SRC_ATOP);
        }
*/
        /**
         * Création du menu sur chaque personne
         */
        TextView tv_menu_presence = (TextView) viewHolder._cardview.findViewById(R.id.tv_menu_presence);
        tv_menu_presence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creation de PopMenu dans chaque CardView
                final PopupMenu _popupmenu = new PopupMenu(_context, viewHolder._cardview);
                _popupmenu.setGravity(Gravity.RIGHT);

                // Affichage des icones
                try {
                    Field[] fields = _popupmenu.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(_popupmenu);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                _popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.id_present:
                                //setPresence(i, Etudiant.STATUE_ETUDIANT.PRESENT);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_absent:
                                //setPresence(i, Etudiant.STATUE_ETUDIANT.ABSENT);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_retard:
                                //setPresence(i, Etudiant.STATUE_ETUDIANT.RETARD);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_excuse:
                                //setPresence(i, Etudiant.STATUE_ETUDIANT.EXCUSE);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                _popupmenu.getMenuInflater().inflate(R.menu.menu_statue_presence, _popupmenu.getMenu());

                _popupmenu.show();
            }
        });

        // Enlève le menu si pas dans mode enseignant ou le remet visible
        if (_isModeEnseignant) {
            tv_menu_presence.setVisibility(View.VISIBLE);
        }
        else {
            tv_menu_presence.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return _etudIns.size();
    }

    /**
     * Met à jour le mode Enseignant : true = ON , false = OFF
     *
     * @param bool
     */
    public void set_isModeEnseignant (boolean bool){
        _isModeEnseignant = bool;
        notifyDataSetChanged();
    }

    public boolean get_isModeEnseignant (){
        return  _isModeEnseignant;
    }

   /* public void setPresence(int position, int presence) {
        _etudIns.get(position).setPresence(presence);
    }
*/

    public class PersonneInscrite {
        Etudiant etudiant;
        Personnel personnel;
        Autre autre;
        int statut_presence;

        public PersonneInscrite(Etudiant etud) {
            etudiant = etud;
            personnel = null;
            autre = null;
            // TODO : dev : mettre la présence automatiquement en fonction de bdd
            statut_presence = 0;// 0 = absent
        }

        public PersonneInscrite(Personnel pers) {
            etudiant = null;
            personnel = pers;
            autre = null;
            // TODO : dev : mettre la présence automatiquement en fonction de bdd
            statut_presence = 0;// 0 = absent
        }

        public PersonneInscrite(Autre aut) {
            etudiant = null;
            personnel = null;
            autre = aut;
            // TODO : dev : mettre la présence automatiquement en fonction de bdd
            statut_presence = 0;// 0 = absent
        }

        public String getNom() {
            if (etudiant != null) return etudiant.getNom();
            else if (personnel != null) return personnel.getNom();
            else if (autre != null) return autre.getNom();
            else return "Erreur nom Personne inscrite";
        }

        public String getPrenom() {
            if (etudiant != null) return etudiant.getPrenom();
            else if (personnel != null) return personnel.getPrenom();
            else if (autre != null) return autre.getPrenom();
            else return "Erreur prenom Personne inscrite";
        }

        public int getNumeroEtudiant() {
            if (etudiant != null) return etudiant.getNumeroEtudiant();
            else if (personnel != null) return 0;
            else if (autre != null) return 0;
            else return 0;
        }

        public int getPresence() {
            return statut_presence;
        }

        public void setPresence(int presence) {
            // TODO : dev : mettre à jour bdd avec la présence en paramètre
            statut_presence = presence;
        }

    }
}
