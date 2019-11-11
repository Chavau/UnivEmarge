package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Presence extends Entity {
    private int idPresence;
    private int idEvenement;

    @JsonProperty("idSeance")
    private int idSeance;

    @JsonProperty("idInscription")
    private int idInscription;

    @JsonProperty("numeroEtudiant")
    private int numeroEtudiant;

    @JsonProperty("statutPresence")
    private StatutPresence statutPresence;

    @JsonProperty("dateMaj")
    private Date dateMaj;

    @JsonProperty("deleted")
    private String deleted;

    private int idPersonnel;
    private int idAutre;

    // needed for jackson parser
    public Presence() {}

    public Presence(int idPresence, int idEvenement, int numeroEtudiant, StatutPresence statutPresence, boolean deleted, int idPersonnel, int idAutre) {
        this.idPresence = idPresence;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.statutPresence = statutPresence;
        this.deleted = deleted;
        this.idPersonnel = idPersonnel;
        this.idAutre = idAutre;
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

    public StatutPresence getStatutPresence() {
        return statutPresence;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public int getIdAutre() {
        return idAutre;
    }

    public String isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
