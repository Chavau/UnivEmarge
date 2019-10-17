package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Inscription {
    private int idPersonnel;
    private int idInscription;
    private int idEvenement;
    private int numeroEtudiant;
    private Date dateMaj;
    private boolean deleted;

    public Inscription(int idPersonnel, int idInscription, int idEvenement, int numeroEtudiant, Date dateMaj, boolean deleted) {
        this.idPersonnel = idPersonnel;
        this.idInscription = idInscription;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
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

    public Date getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
