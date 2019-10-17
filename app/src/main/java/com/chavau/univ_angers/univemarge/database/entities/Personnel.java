package com.chavau.univ_angers.univemarge.database.entities;

import java.sql.Blob;

public class Personnel {
    private int idPersonnel;
    private String nom;
    private String prenom;
    private String login;
    private String email;
    private Blob photo;
    private String no_mifare;
    private String pin;
    private String dateMaj;
    private boolean deleted;

    public Personnel(int idPersonnel, String nom, String prenom, String login, String email, Blob photo, String no_mifare, String pin, String dateMaj, boolean deleted) {
        this.idPersonnel = idPersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.email = email;
        this.photo = photo;
        this.no_mifare = no_mifare;
        this.pin = pin;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Blob getPhoto() {
        return photo;
    }

    public String getNo_mifare() {
        return no_mifare;
    }

    public String getPin() {
        return pin;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
