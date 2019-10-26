package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Evenement extends Entity {

    @JsonProperty("id")
    private int idEvenement;

    @JsonProperty("dateDebut")
    private Date dateDebut;

    @JsonProperty("dateFin")
    private Date dateFin;

    @JsonProperty("lieu")
    private String lieu;
    private int typeEmargement;

    @JsonProperty("")
    private int temoinRoulant;

    @JsonProperty("libelleEvenement")
    private String libelleEvenement;

    @JsonProperty("idCours")
    private int idCours;

    @JsonProperty("dateMaj")
    private Date dateMaj;

    @JsonProperty("typeEmargement")
    private boolean typeEmargement;

    @JsonProperty("")
    private boolean deleted;

    // needed for jackson parser
    public Evenement() {}

    public Evenement(int idEvenement, Date dateDebut, Date dateFin, String lieu, int temoinRoulant, String libelleEvenement, int idCours, Date dateMaj, boolean deleted) {
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