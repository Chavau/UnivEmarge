package com.chavau.univ_angers.univemarge.view.activities;


import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.intermediaire.Cours;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;


import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;

public class ListeEvenementsCours extends AppCompatActivity {

    RecyclerView _recyclerview;
    AdapterCours _adapterCours;
    ArrayList<Cours> _cours = new ArrayList<>();

    DatePickerDialog _datepickerdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_evenements_cours);

        _recyclerview = findViewById(R.id.recyclerview_cours);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));

        _cours = creeCours();

        _adapterCours = new AdapterCours(this, _cours);

        _recyclerview.setAdapter(_adapterCours);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendrier, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option_calendrier:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                _datepickerdialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int annee, int mois, int jour) {
                        mois++; // le mois selectionné correspond à mois+1
                        String date = String.valueOf(jour) + "/" + ((mois < 10) ? "0" + (mois) : String.valueOf(mois)) + "/" + String.valueOf(annee);
                        ArrayList<Cours> cours = new ArrayList<>();

                        for (Cours c : creeCours()) {
                            if (c.get_date() != null && c.get_date().contains(date.trim())) {
                                cours.add(c);
                            }
                        }

                        // Si la liste n'est pas vide alors affiche la/les date(s) correspondante(s) sinon re-affiche la liste complete
                        if (cours.size() > 0) {
                            _adapterCours.setListeCours(cours);
                        } else {
                            Toast.makeText(ListeEvenementsCours.this, "Aucune date correspondante.", Toast.LENGTH_SHORT).show();
                            _adapterCours.setListeCours(_cours);
                        }
                    }
                }, year, month, day);

                _datepickerdialog.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // CREATION DE COURS

    ArrayList<Cours> creeCours() {
        ArrayList<Cours> cours = new ArrayList<>();

        String nomCours[] = {"Logique et techniques de preuve", "Programmation avancée en C++",
                "Analyse et conception de systèmes d'information", "Introduction à la programmation",
                "Pratique du génie logiciel", "Réseaux pour ingénieurs", "Mathématiques pour informaticien",
                "Informatique théorique", "Développement d'applications Web", "Introduction à la robotique mobile",
                "Architecture logicielle", "Bases de données avancées", "Assurance qualité du logiciel",
                "Réseaux mobiles", "Projet expérimental", "Traitement automatique de la langue naturelle",
                "Proposition de projet de thèse", "Examen de connaissances fondamentales", "Projet expérimental",
                "Sécurité et méthodes formelles", "Interface personne-machine"};
        String joursSemaine[] = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String heuresDebut[] = {"8h00", "8h30", "9h00", "9h30", "10h00", "11h00", "13h30", "14h00", "15h20", "16h00", "17h30", "18h00", "19h10", "20h30"};
        String heureFin[] = {"11h00", "10h30", "12h00", "11h00", "11h00", "12h20", "17h30", "15h40", "18h20", "20h00", "21h00", "19h00", "22h10", "23h00"};
        String dates[] = {"9/10/2019", "12/10/2019", "25/10/2019", "2/11/2019", "16/11/2019", "11/11/2019", "24/11/2019", "6/12/2019", "7/12/2019", "11/12/2019",
                "9/01/2020", "12/01/2020", "25/01/2020", "2/02/2020", "16/02/2020", "11/02/2020", "24/02/2020", "6/03/2020", "7/03/2020", "11/03/2020"};


        ArrayList<Etudiant> etu = new ArrayList();

        etu.add(new Etudiant("André", "Bertrand", "KLJFE8EF", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Blanc", "Bertrand", "ZDZJ56BD", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Bonnet", "Chevaliere", "MP54ZDZE", "Loisirs", R.drawable.woman));
        etu.add(new Etudiant("Paul", "Durand", "NBX865NV", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Faure", "Blanche", "RZS5ZSEX", "Loisirs", R.drawable.woman));
        etu.add(new Etudiant("Fontaine", "Dumont", "IUH8GD5Z", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Fournier", "Dupont", "IZCKS8DE", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Francois", "Durand", "FRELS84S", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("François", "Blanc", "KIE85DVS", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Garnier", "Fontaine", "POI58EDS", "Loisirs", R.drawable.woman));
        etu.add(new Etudiant("Girard", "Fournier", "XCV86ZYT", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Henry", "Francois", "NKO868ZD", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Jean", "Garnier", "DEZ85ESQ", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Lambert", "Gauthier", "ERT58YUE", "Loisirs", R.drawable.man));
        etu.add(new Etudiant("Pierre", "Bernard", "RTY894PO", "Loisirs", R.drawable.man));

        etu.sort(new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant e1, Etudiant e2) {
                return (e1.get_nom().compareTo(e2.get_nom()));
            }
        });

        for (int i = 0; i < 20; ++i) {
            Random rand = new Random();
            Random rand2 = new Random();
            int j = rand2.nextInt(14);
            Cours c = new Cours(nomCours[i], dates[i], joursSemaine[rand.nextInt(6)], heuresDebut[j], heureFin[j], etu);
            cours.add(c);
        }

        return cours;
    }
}
