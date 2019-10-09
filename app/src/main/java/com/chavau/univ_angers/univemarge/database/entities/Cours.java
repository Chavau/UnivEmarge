package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Cours {
    private int idCours;
    private String libelle_cours;
    private Date dateMaj;
    private boolean deleted;

    public Cours(int idCours, String libelle_cours, Date dateMaj, boolean deleted) {
        this.idCours = idCours;
        this.libelle_cours = libelle_cours;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdCours() {
        return idCours;
    }

    public String getLibelle_cours() {
        return libelle_cours;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
