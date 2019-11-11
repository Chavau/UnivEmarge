package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Blob;

public class Personnel extends Entity {

    @JsonProperty("numero")
    private int idPersonnel;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("login")
    private String login;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private Blob photo;

    @JsonProperty("no_mifare")
    private String no_mifare;

    @JsonIgnore
    private String pin;

    @JsonProperty("deleted")
    private boolean deleted;

    // needed for jackson parser
    public Personnel() {}

    public Personnel(int idPersonnel, String nom, String prenom, String login, String email, Blob photo, String no_mifare, String pin, String dateMaj, String deleted) {
        this.idPersonnel = idPersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.email = email;
        this.photo = photo;
        this.no_mifare = no_mifare;
        this.pin = pin;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
