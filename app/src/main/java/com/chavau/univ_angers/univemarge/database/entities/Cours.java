package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cours extends Entity {

    @JsonProperty("id")
    private int idCours;

    @JsonProperty("libelle")
    private String libelle_cours;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonProperty("dateMaj")
    private Date dateMaj;

    private boolean deleted;

    // needed for jackson parser
    public Cours() {}

    public Cours(int idCours, String libelle_cours, Date dateMaj, boolean deleted) {
        this.idCours = idCours;
        this.libelle_cours = libelle_cours;
        this.dateMaj = dateMaj;
        this.deleted = deleted;
    }

    public int getIdCours() {
        return idCours;
    }

    public String getLibelle_cours() {
        return libelle_cours;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
