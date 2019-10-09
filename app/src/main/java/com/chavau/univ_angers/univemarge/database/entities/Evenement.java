package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Evenement {
    private int idEvenement;
    private Date dateDebut;
    private Date dateFin;
    private String lieu;
    private int temoinRoulant;
    private String libelleEvenement;
    private int idCours;
    private Date dateMaj;
    private boolean deleted;

    public Evenement(int idEvenement, Date dateDebut, Date dateFin, String lieu, int temoinRoulant, String libelleEvenement, int idCours, Date dateMaj, boolean deleted) {
        this.idEvenement = idEvenement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.temoinRoulant = temoinRoulant;
        this.libelleEvenement = libelleEvenement;
        this.idCours = idCours;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public int getTemoinRoulant() {
        return temoinRoulant;
    }

    public String getLibelleEvenement() {
        return libelleEvenement;
    }

    public int getIdCours() {
        return idCours;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
