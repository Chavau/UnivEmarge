package com.chavau.univ_angers.univemarge.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.dao.PresenceDAO;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;
import com.chavau.univ_angers.univemarge.database.entities.Presence;
import com.chavau.univ_angers.univemarge.view.activities.BadgeageEtudiant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AdapterPersonneInscrite extends RecyclerView.Adapter<AdapterPersonneInscrite.ViewHolderPI> {

    private Context _context;
    private boolean _isModeEnseignant; // Si true alors il faut le menu pour modifier à la main (false par défaut)
    private ArrayList<PersonneInscrite> _listePersonnes; //= new ArrayList<>();

    public static int ABSENT   = 0;
    public static int PRESENT  = 1;
    public static int EXCUSE   = 2;
    public static int RETARD   = 3;
    public static int PRESENT_HORS_CRENEAU = 4 ;

    private int idEvenement;

    //private final static String nomListePresence = "Liste Presence";

    //public enum VueChoix {NS, MS}
    // TODO : dev :ménage
    ;// pour le choix de vue qui va être gonflée,
    // NS correspond à la vue d'une nouvelle séance
    // MS correspond à la vue d'une modification de séance
    //private static VueChoix _vueChoix;

    // Lors d'un clic sur un cardview dans l'activité "Nouvelle Séance"

    private BadgeageEtudiant.Listener _listener;

    /*public interface Listener {
        void onClick(int position);
    }*/

    public void set_listener(BadgeageEtudiant.Listener listener) {
        this._listener = listener;
    }

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

    public AdapterPersonneInscrite(Context context) {
        _context = context;
        _isModeEnseignant = false;
        _listePersonnes = new ArrayList<>(); // TODO : dev : voir si possible de trier par nom
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

    public ArrayList<PersonneInscrite> getlistePersonneInscrites() {
        return _listePersonnes;
    }

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
        tv_nom.setText(_listePersonnes.get(i).getNom());

        // Affectation du prenom de l'étudiant(e) inscrit(e) à l'activité
        tv_prenom.setText(_listePersonnes.get(i).getPrenom());

        // Affectation du numéro de l'étudiant(e) inscrit(e) à l'activité
        int num_etud = _listePersonnes.get(i).getNumeroEtudiant();
        switch(num_etud) {
            case 1 : tv_numetu.setText("Personnel"); break;
            case 2 : tv_numetu.setText("Externe"); break;
            default:tv_numetu.setText(Integer.toString(_listePersonnes.get(i).getNumeroEtudiant())); break;
        }
        // TEST : tv_numetu.setText(_listePersonnes.get(i).getMifare());


        // Affectation du type d'activité de l'étudiant(e) inscrit(e) à l'activité
        //tv_typeact.setText(_listePersonnes.get(i).get_typeActivite());

        //Coloration de l'ampoule en fonction de la présence d'une personne

        if (_listePersonnes.get(i).getStatutPresence() == ABSENT) {
            iv_light.setColorFilter(_context.getResources().getColor(R.color.rouge));
        }
        else if (_listePersonnes.get(i).getStatutPresence() == PRESENT) {
            iv_light.setColorFilter(_context.getResources().getColor(R.color.vert));
        }
        else if (_listePersonnes.get(i).getStatutPresence() == RETARD) {
            iv_light.setColorFilter(_context.getResources().getColor(R.color.bleu));
        }
        else if (_listePersonnes.get(i).getStatutPresence() == EXCUSE) {
            iv_light.setColorFilter(_context.getResources().getColor(R.color.orange));
        }
        else if (_listePersonnes.get(i).getStatutPresence() == PRESENT_HORS_CRENEAU) {
            iv_light.setColorFilter(_context.getResources().getColor(R.color.violet)); // TODO : dev : trouver le violet UA (ou autre couleur)
        }
        else {
            iv_light.setColorFilter(Color.alpha(_context.getResources().getColor(R.color.noir)), PorterDuff.Mode.SRC_ATOP);
        }


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
                                setPresence(i, PRESENT);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_absent:
                                setPresence(i, ABSENT);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_retard:
                                setPresence(i, RETARD);
                                notifyDataSetChanged();
                                break;
                            case R.id.id_excuse:
                                setPresence(i, EXCUSE);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        _listener.majPresence();
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

    /**
     * add fonction pour chaque type de personne
     * @param personne
     */
    public void addPersonne(Etudiant personne){
        PersonneInscrite pers = new PersonneInscrite(personne);
        finishAdd(pers);
    }
    public void addPersonne(Personnel personne){
        PersonneInscrite pers = new PersonneInscrite(personne);
        finishAdd(pers);
    }
    public void addPersonne(Autre personne){
        PersonneInscrite pers = new PersonneInscrite(personne);
        finishAdd(pers);
    }

    public void finishAdd(PersonneInscrite personne){
        _listePersonnes.add(personne);
        Collections.sort(_listePersonnes, new Comparator<PersonneInscrite>() {
            @Override
            public int compare(PersonneInscrite o1, PersonneInscrite o2) {
                return o1.getNom().compareTo(o2.getNom());
            }
        });
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return _listePersonnes.size();
    }

    public int getNbPresent(){
        int nb = 0 ;
        for (PersonneInscrite p : _listePersonnes){
            if (p.getStatutPresence() == PRESENT || p.getStatutPresence() == PRESENT_HORS_CRENEAU || p.getStatutPresence() == RETARD)
                nb++;
        }
        return nb;
    }

    public int getNbHorsCreneau(){
        int nb = 0 ;
        for (PersonneInscrite p : _listePersonnes){
            if (p.getStatutPresence() == PRESENT_HORS_CRENEAU)
                nb++;
        }
        return nb;
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

    public void setPresence(int position, int presence) {
        _listePersonnes.get(position).setPresence(presence);
        notifyDataSetChanged();
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public class PersonneInscrite {
        Etudiant etudiant;
        Personnel personnel;
        Autre autre;
        Presence presence;
        int statut_presence; // voir constantes de classe adapter pour signification. Le paramètre est utile car il peut ne pas encore y avoir de présence
        PresenceDAO dao_presence = new PresenceDAO(new DatabaseHelper(_context));

        public PersonneInscrite(Etudiant etud) {
            etudiant = etud;
            personnel = null;
            autre = null;

            presence = dao_presence.getPresenceEtud(idEvenement,etudiant.getIdEtudiant());

            if(presence == null) {
                statut_presence = 0;// 0 = absent
                Log.i("debug personne incrites" , " presence null : " + presence);
            }
            else {
                Log.i("debug personne incrites" , " presence non null : " + presence.getStatutPresence());
                statut_presence = presence.getStatutPresence();
            }
        }

        public PersonneInscrite(Personnel pers) {
            etudiant = null;
            personnel = pers;
            autre = null;

            presence = dao_presence.getPresencePersonnel(idEvenement,personnel.getIdPersonnel());

            if(presence == null) {
                statut_presence = 0;// 0 = absent
                Log.i("debug personne incrites" , " presence null : " + presence);
            }
            else {
                Log.i("debug personne incrites" , " presence non null : " + presence.getStatutPresence());
                statut_presence = presence.getStatutPresence();
            }
        }

        public PersonneInscrite(Autre aut) {
            etudiant = null;
            personnel = null;
            autre = aut;

            presence = dao_presence.getPresenceAutre(idEvenement,autre.getIdAutre());

            if(presence == null) {
                statut_presence = 0;// 0 = absent
                Log.i("debug personne incrites" , " presence null : " + presence);
            }
            else {
                Log.i("debug personne incrites" , " presence non null : " + presence.getStatutPresence());
                statut_presence = presence.getStatutPresence();
            }
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
            else if (personnel != null) return 1;
            else if (autre != null) return 2;
            else return 0;
        }

        public String getMifare(){
                if (etudiant != null) return etudiant.getNo_mifare();
                else if (personnel != null) return personnel.getNo_mifare();
                else return "";
        }

        public int getStatutPresence() {
            return statut_presence;
        }

        public void setPresence(int pres) {
            statut_presence = pres;

            if(presence == null){
                presence = new Presence();
                presence.setIdEvenement(idEvenement);
                presence.setDeleted(false);
            }

            presence.setDateMaj(new Date());
            presence.setStatutPresence(pres);


            if(etudiant != null) {
                // TODO : dev : normalement ce qui suit c'est pour une présence null
                presence.setIdPersonnel(0);
                presence.setIdAutre(0);
                presence.setIdEtudiant(etudiant.getIdEtudiant());

                dao_presence.majPresence(presence);
                // récupération de l'identifiant de la présence pour éviter de créer un doublon en cas de modification à la création de la présence
                presence = dao_presence.getPresenceEtud(idEvenement,etudiant.getIdEtudiant());


            }
            else if (personnel != null) {
                presence.setIdPersonnel(personnel.getIdPersonnel());
                presence.setIdAutre(0);
                presence.setIdEtudiant(0);

                dao_presence.majPresence(presence);
                // récupération de l'identifiant de la présence pour éviter de créer un doublon en cas de modification à la création de la présence
                presence = dao_presence.getPresencePersonnel(idEvenement,personnel.getIdPersonnel());
            }
            else if (autre != null) {
                presence.setIdPersonnel(0);
                presence.setIdAutre(autre.getIdAutre());
                presence.setIdEtudiant(0);

                dao_presence.majPresence(presence);
                // récupération de l'identifiant de la présence pour éviter de créer un doublon en cas de modification à la création de la présence
                presence = dao_presence.getPresenceAutre(idEvenement,autre.getIdAutre());
            }
            else
                Toast.makeText(_context,"on devrait jamais arriver la, BUG", Toast.LENGTH_LONG).show();
        }

    }

}
