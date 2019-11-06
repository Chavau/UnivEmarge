package com.chavau.univ_angers.univemarge.database.entities;

public class Responsable {
    private int idEvenement;
    private int idPersonnelResponsable;
    private boolean deleted;

    public Responsable(int idEvenement, int idPersonnelResponsable, boolean deleted) {
        this.idEvenement = idEvenement;
        this.idPersonnelResponsable = idPersonnelResponsable;
        this.deleted = deleted;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getIdPersonnelResponsable() {
        return idPersonnelResponsable;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
