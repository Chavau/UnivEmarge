package com.chavau.univ_angers.univemarge.database.entities;

import java.sql.Blob;

public class Personnel extends Personne{
    private int idPersonnel;
    private String login;
    private Blob photo;
    private String no_mifare;
    private String pin;
    private boolean deleted;

    public Personnel(String nom, String prenom, String email, int idPersonnel, String login, Blob photo, String no_mifare, String pin, boolean deleted) {
        super(nom, prenom, email);
        this.idPersonnel = idPersonnel;
        this.login = login;
        this.photo = photo;
        this.no_mifare = no_mifare;
        this.pin = pin;
        this.deleted = deleted;
    }

    public int getIdPersonnel() {
        return idPersonnel;
    }

    public String getLogin() {
        return login;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
