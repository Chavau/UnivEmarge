package com.chavau.univ_angers.univemarge.intermediaire;

import java.util.ArrayList;

public class Cours {
    private String _intitule;
    private String _date;
    private String _jourDeLaSemaine;
    private String _heureDebut;
    private String _heureFin;
    private ArrayList<Etudiant> _listeEtudiantInscrit = new ArrayList<>();



    public Cours(String i, String d, String jds, String hd, String hf, ArrayList<Etudiant> et) {
        this._intitule = i;
        this._date = d;
        this._jourDeLaSemaine = jds;
        this._heureDebut = hd;
        this._heureFin = hf;
        this._listeEtudiantInscrit = et;
    }

    public String get_date() {
        return _date;
    }

    public String get_heureDebut() {
        return _heureDebut;
    }

    public String get_heureFin() {
        return _heureFin;
    }

    public String get_intitule() {
        return _intitule;
    }

    public String get_jourDeLaSemaine() {
        return _jourDeLaSemaine;
    }

    public ArrayList<Etudiant> get_listeEtudiantInscrit() {
        return _listeEtudiantInscrit;
    }

}
