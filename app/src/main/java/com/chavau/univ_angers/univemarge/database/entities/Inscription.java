package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Inscription {
    private int idPersonnel;
    private int idInscription;
    private int idEvenement;
    private int numeroEtudiant;
    private Date dateMaj;
    private boolean deleted;
    private String typeInscription;
    private int idAutre;

    public Inscription(int idPersonnel, int idInscription, int idEvenement, int numeroEtudiant, Date dateMaj, boolean deleted, String typeInscription, int idAutre) {
        this.idPersonnel = idPersonnel;
        this.idInscription = idInscription;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.dateMaj = dateMaj;
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
}
