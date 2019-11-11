package com.chavau.univ_angers.univemarge.database.entities;

public abstract class Personne {
    private String nom;
    private String prenom;
    private String email;

    public Personne(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }
}
