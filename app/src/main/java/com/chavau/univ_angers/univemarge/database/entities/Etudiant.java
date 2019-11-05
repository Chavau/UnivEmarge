package com.chavau.univ_angers.univemarge.database.entities;

import java.sql.Blob;
import java.util.Date;

public class Etudiant extends Personne {
    private int numeroEtudiant;
    private String no_mifare;
    private Blob photo;
    private Date dateMaj;
    private boolean deleted;

    public Etudiant(int numeroEtudiant, String nom, String prenom, String no_mifare, String email, Blob photo, Date dateMaj, boolean deleted) {
        super(nom, prenom, email);
        this.numeroEtudiant = numeroEtudiant;
        this.no_mifare = no_mifare;
        this.photo = photo;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public String getNo_mifare() {
        return no_mifare;
    }

    public Blob getPhoto() {
        return photo;
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
