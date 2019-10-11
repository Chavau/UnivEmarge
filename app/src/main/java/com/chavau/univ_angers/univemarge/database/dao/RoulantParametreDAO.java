package com.chavau.univ_angers.univemarge.database.dao;

import java.util.Date;

public class RoulantParametreDAO {
    private int idCours;
    private Date tempsSeance;
    private int maxPersonnes;
    private Date dateMaj;

    public RoulantParametreDAO(int idCours, Date tempsSeance, int maxPersonnes, Date dateMaj) {
        this.idCours = idCours;
        this.tempsSeance = tempsSeance;
        this.maxPersonnes = maxPersonnes;
        this.dateMaj = dateMaj;
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

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }
}
