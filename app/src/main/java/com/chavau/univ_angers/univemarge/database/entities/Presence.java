package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class Presence extends Entity {
    @JsonProperty("id")
    private int idPresence;

    @JsonProperty("id_evenement")
    private int idEvenement;

    @JsonProperty("id_etudiant")
    private int numeroEtudiant;

    @JsonProperty("etat")
    private int statutPresence;
    /*
    0 = absent
    1 = present
    2 = excuse
    3 = retard
    4 = hors creneau
     */

    @JsonProperty("dateMaj")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    private Date dateMaj;

    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("id_personnel")
    private int idPersonnel;

    @JsonProperty("id_autre")
    private int idAutre;

    // needed for jackson parser
    public Presence() {}

    public Presence(int idEvenement, int numeroEtudiant, int statutPresence, boolean deleted, int idPersonnel, int idAutre) {
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.statutPresence = statutPresence;
        this.deleted = deleted;
        this.idPersonnel = idPersonnel;
        this.idAutre = idAutre;
    }

    public Presence(int idPresence, int idEvenement, int numeroEtudiant, int statutPresence, boolean deleted, int idPersonnel, int idAutre, Date dateMaj) {
        this.idPresence = idPresence;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.statutPresence = statutPresence;
        this.deleted = deleted;
        this.idPersonnel = idPersonnel;
        this.idAutre = idAutre;
        this.dateMaj = dateMaj;
    }

    public int getIdPresence() {
        return idPresence;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public int getStatutPresence() {
        return statutPresence;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public int getIdAutre() {
        return idAutre;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public void setIdEtudiant(int numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }

    public void setStatutPresence(int statutPresence) {
        this.statutPresence = statutPresence;
    }

    public void setIdPersonnel(int idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public void setIdAutre(int idAutre) {
        this.idAutre = idAutre;
    }
}
