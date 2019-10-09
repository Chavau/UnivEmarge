package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class PresenceRoulant {
    private int idRoulant;
    private int idInscription;
    private Date temps;
    private String entrees_sorties;
//  TODO:
//    private Date dateEntree;
//    private Date dateSortie;

    public PresenceRoulant(int idRoulant, int idInscription, Date temps, String entrees_sorties) {
        this.idRoulant = idRoulant;
        this.idInscription = idInscription;
        this.temps = temps;
        this.entrees_sorties = entrees_sorties;
    }

    public int getIdRoulant() {
        return idRoulant;
    }

    public int getIdInscription() {
        return idInscription;
    }

    public Date getTemps() {
        return temps;
    }

    public String getEntrees_sorties() {
        return entrees_sorties;
    }
}
