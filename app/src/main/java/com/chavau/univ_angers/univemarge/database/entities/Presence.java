package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Presence {
    private int idPresence;
    private int idEvenement;
    private int numeroEtudiant;
    private StatutPresence statutPresence;
    private Date dateMaj;
    private boolean deleted;

    public Presence(int idPresence, int idEvenement, int numeroEtudiant, StatutPresence statutPresence, Date dateMaj, boolean deleted) {
        this.idPresence = idPresence;
        this.idEvenement = idEvenement;
        this.numeroEtudiant = numeroEtudiant;
        this.statutPresence = statutPresence;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getidPresence() {
        return idPresence;
    }

    public int getidEvenement() {
        return idEvenement;
    }

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public StatutPresence getStatutPresence() {
        return statutPresence;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
