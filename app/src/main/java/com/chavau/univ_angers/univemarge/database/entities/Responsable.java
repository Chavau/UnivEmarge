package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Responsable extends Entity {

    @JsonProperty("")
    private int idEvenement;

    @JsonProperty("")
    private int idPersonnelResponsable;

    @JsonProperty("")
    private Date dateMaj;

    @JsonProperty("")
    private boolean deleted;

    // needed for jackson parser
    public Responsable() {}

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
