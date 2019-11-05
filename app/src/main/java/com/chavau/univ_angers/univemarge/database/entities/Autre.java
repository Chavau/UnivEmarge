package com.chavau.univ_angers.univemarge.database.entities;

public class Autre extends Personne {
    private int idAutre;

    public Autre(String nom, String prenom, String email, int idAutre) {
        super(nom, prenom, email);
        this.idAutre = idAutre;
    }

    public int getIdAutre() {
        return idAutre;
    }
}
