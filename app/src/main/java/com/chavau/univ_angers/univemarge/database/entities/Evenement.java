package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Evenement extends Entity {

    @JsonProperty("id")
    private int idEvenement;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    @JsonProperty("dateDebut")
    private Date dateDebut;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    @JsonProperty("dateFin")
    private Date dateFin;

    @JsonProperty("lieu")
    private String lieu;
    private int typeEmargement;

    @JsonIgnore
    private int temoinRoulant;

    @JsonProperty("libelleEvenement")
    private String libelleEvenement;

    @JsonProperty("idCours")
    private int idCours;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    @JsonProperty("dateMaj")
    private Date dateMaj;

    @JsonProperty("typeEmargement")
    private String typeEmargement;

    @JsonProperty("deleted")
    private String deleted;

    // needed for jackson parser
    public Evenement() {}

    public Evenement(int idEvenement, Date dateDebut, Date dateFin, String lieu, int temoinRoulant, String libelleEvenement, int idCours, Date dateMaj, String deleted) {
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