package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Presence {
    private int idSeance;
    private int idInscription;
    private int numeroEtudiant;
    private StatutPresence statutPresence;
    private Date dateMaj;
    private boolean deleted;

    public Presence(int idSeance, int idInscription, int numeroEtudiant, StatutPresence statutPresence, Date dateMaj, boolean deleted) {
        this.idSeance = idSeance;
        this.idInscription = idInscription;
        this.numeroEtudiant = numeroEtudiant;
        this.statutPresence = statutPresence;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdSeance() {
        return idSeance;
    }

    public int getIdInscription() {
        return idInscription;
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
}
