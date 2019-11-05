package com.chavau.univ_angers.univemarge.database.entities;

public class Autre extends Personne {
    private int idAutre;

    public Autre(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }

    public int getIdAutre() {
        return idAutre;
    }
}
