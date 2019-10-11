package com.chavau.univ_angers.univemarge.database.dao;

import java.util.Date;

public class ResponsableDAO {
    private int idEvenement;
    private int idPersonnelResponsable;
    private Date dateMaj;
    private boolean deleted;

    public ResponsableDAO(int idEvenement, int idPersonnelResponsable, Date dateMaj, boolean deleted) {
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

    public boolean isDeleted() {
        return deleted;
    }
}
