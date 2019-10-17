package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class PresenceRoulant {
    private int idRoulant;
    private int idEvenement;
    private int numeroEtudiant;
    private Date temps;
    private Date dateEntree;
    private Date dateSortie;

    public PresenceRoulant(int idRoulant, int idEvenement, int numeroEtudiant, Date temps, Date dateEntree, Date dateSortie) {
        this.idRoulant = idRoulant;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.temps = temps;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;
    }

    public int getIdRoulant() {
        return idRoulant;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public Date getTemps() {
        return temps;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public Date getDateSortie() {
        return dateSortie;
    }
}
