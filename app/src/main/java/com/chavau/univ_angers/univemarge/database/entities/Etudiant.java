package com.chavau.univ_angers.univemarge.database.entities;

import java.sql.Blob;

public class Etudiant extends Personne {
    private int numeroEtudiant;
    private String no_mifare;
    private Blob photo;
    private boolean deleted;

    public Etudiant(String nom, String prenom, String email, int numeroEtudiant, String no_mifare, Blob photo, boolean deleted) {
        super(nom, prenom, email);
        this.numeroEtudiant = numeroEtudiant;
        this.no_mifare = no_mifare;
        this.photo = photo;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
