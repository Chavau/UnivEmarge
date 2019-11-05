package com.chavau.univ_angers.univemarge.database.entities;

import java.util.Date;

public class Responsable {
    private int idEvenement;
    private int idPersonnelResponsable;
    private Date dateMaj;
    private boolean deleted;

    public Responsable(int idEvenement, int idPersonnelResponsable, Date dateMaj, boolean deleted) {
        this.idEvenement = idEvenement;
        this.idPersonnelResponsable = idPersonnelResponsable;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getIdPersonnelResponsable() {
        return idPersonnelResponsable;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
