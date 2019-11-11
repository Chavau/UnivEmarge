package com.chavau.univ_angers.univemarge.database.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cours extends Entity {

    @JsonProperty("idCours")
    private int idCours;

    @JsonProperty("libelle")
    private String libelle_cours;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.S", timezone="Europe/Paris")
    @JsonProperty("dateMaj")
    private Date dateMaj;

    @JsonProperty("deleted")
    private String deleted;

    // needed for jackson parser
    public Cours() {}

    public Cours(int idCours, String libelle_cours, Date dateMaj, String deleted) {
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

    public String isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "idCours=" + idCours +
                ", libelle_cours='" + libelle_cours + '\'' +
                ", dateMaj=" + dateMaj +
                ", deleted=" + deleted +
                '}';
    }
}
