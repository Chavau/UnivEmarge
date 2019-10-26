package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Evenement extends Entity {
    private int idEvenement;
    private Date dateDebut;
    private Date dateFin;
    private String lieu;
    private int typeEmargement;
    private String libelleEvenement;
    private int idCours;
    private boolean deleted;

    public Evenement(int idEvenement, Date dateDebut, Date dateFin, String lieu, int typeEmargement, String libelleEvenement, int idCours, boolean deleted) {
        this.idEvenement = idEvenement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.typeEmargement = typeEmargement;
        this.libelleEvenement = libelleEvenement;
        this.idCours = idCours;
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

    public int getTypeEmargement() {
        return typeEmargement;
    }

    public String getLibelleEvenement() {
        return libelleEvenement;
    }

    public int getIdCours() {
        return idCours;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

