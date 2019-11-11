package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class RoulantParametre {
    private int idCours;
    private Date tempsSeance;
    private int maxPersonnes;

    public RoulantParametre(int idCours, Date tempsSeance, int maxPersonnes) {
        this.idCours = idCours;
        this.tempsSeance = tempsSeance;
        this.maxPersonnes = maxPersonnes;
    }

    public int getIdCours() {
        return idCours;
    }

    public Date getTempsSeance() {
        return tempsSeance;
    }

    public void setTempsSeance(Date tempsSeance) {
        this.tempsSeance = tempsSeance;
    }

    public int getMaxPersonnes() {
        return maxPersonnes;
    }

    public void setMaxPersonnes(int maxPersonnes) {
        this.maxPersonnes = maxPersonnes;
    }
}
