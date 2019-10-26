package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Inscription extends Entity {

    @JsonProperty("numero_personnel")
    private int idPersonnel;

    @JsonProperty("id")
    private int idInscription;

    @JsonProperty("id_evenement")
    private int idEvenement;

    @JsonProperty("numero_etudiant")
    private int numeroEtudiant;

    @JsonProperty("typeInscription")
    private boolean typeInscription;

    @JsonProperty("")
    private boolean deleted;
    private String typeInscription;
    private int idAutre;

    // needed for jackson parser
    public Inscription() {}

    public Inscription(int idPersonnel, int idInscription, int idEvenement, int numeroEtudiant, Date dateMaj, boolean deleted) {
        this.idPersonnel = idPersonnel;
        this.idInscription = idInscription;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.deleted = deleted;
        this.typeInscription = typeInscription;
        this.idAutre = idAutre;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public int getIdInscription() {
        return idInscription;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public String getTypeInscription() {
        return typeInscription;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getIdAutre() {
        return idAutre;
    }
}
