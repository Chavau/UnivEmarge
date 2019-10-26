package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Inscription extends Entity {
    private int idPersonnel;
    private int idInscription;
    private int idEvenement;
    private int numeroEtudiant;
    private boolean deleted;
    private String typeInscription;
    private int idAutre;

    public Inscription(int idPersonnel, int idInscription, int idEvenement, int numeroEtudiant, boolean deleted, String typeInscription, int idAutre) {
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
