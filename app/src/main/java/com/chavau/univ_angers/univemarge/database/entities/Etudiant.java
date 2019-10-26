package com.chavau.univ_angers.univemarge.database.entities;

import java.sql.Blob;
import java.util.Date;

public class Etudiant extends Entity {
    private int numeroEtudiant;
    private String nom;
    private String prenom;
    private String no_mifare;
    private String email;
    private Blob photo;
    private Date dateMaj;
    private boolean deleted;

    public Etudiant(int numeroEtudiant, String nom, String prenom, String no_mifare, String email, Blob photo, Date dateMaj, boolean deleted) {
        this.numeroEtudiant = numeroEtudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.no_mifare = no_mifare;
        this.email = email;
        this.photo = photo;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNo_mifare() {
        return no_mifare;
    }

    public String getEmail() {
        return email;
    }

    public Blob getPhoto() {
        return photo;
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
