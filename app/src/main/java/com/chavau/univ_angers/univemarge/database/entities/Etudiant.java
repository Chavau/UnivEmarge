package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Blob;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Etudiant extends Entity {

    @JsonProperty("numero")
    private int numeroEtudiant;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("no_mifare")
    private String no_mifare;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private Blob photo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    @JsonProperty("dateMaj")
    private Date dateMaj;

    @JsonProperty("deleted")
    private String deleted;

    // needed for jackson parser
    public Etudiant() {}

    public Etudiant(int numeroEtudiant, String nom, String prenom, String no_mifare, String email, Blob photo, Date dateMaj, String deleted) {
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

    public String isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
