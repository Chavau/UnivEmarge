package com.chavau.univ_angers.univemarge.sync;

import com.chavau.univ_angers.univemarge.database.dao.CoursDAO;
import com.chavau.univ_angers.univemarge.database.dao.EtudiantDAO;
import com.chavau.univ_angers.univemarge.database.dao.EvenementDAO;
import com.chavau.univ_angers.univemarge.database.dao.IMergeable;
import com.chavau.univ_angers.univemarge.database.dao.InscriptionDAO;
import com.chavau.univ_angers.univemarge.database.dao.PersonnelDAO;
import com.chavau.univ_angers.univemarge.database.dao.PresenceDAO;
import com.chavau.univ_angers.univemarge.database.dao.RoulantParametreDAO;
import com.chavau.univ_angers.univemarge.database.entities.Cours;
import com.chavau.univ_angers.univemarge.database.entities.Entity;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Evenement;
import com.chavau.univ_angers.univemarge.database.entities.Inscription;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;
import com.chavau.univ_angers.univemarge.database.entities.Presence;
import com.chavau.univ_angers.univemarge.database.entities.RoulantParametre;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public enum ElementToSync {
    COURS("/cours", Cours[].class, new CoursDAO()),
    ETUDIANT("/etudiants",Etudiant[].class, new EtudiantDAO()),
    EVENEMENTS("/evenements", Evenement[].class, new EvenementDAO()),
    INSCRIPTIONS("/inscriptions", Inscription[].class, new InscriptionDAO()),
    PERSONNELS("/personnels", Personnel[].class, new PersonnelDAO()),
    PRESENCES("/presences", Presence[].class, new PresenceDAO()),
    ROULANT_PARAMETRE("/roulant_parametre", RoulantParametre[].class, new RoulantParametreDAO());

    private String urlLink;
    private Class<Entity[]> entityClass;
    private IMergeable iMergeable;

    ElementToSync(String urlLink, Class<? extends Entity[]> entityClass, IMergeable iMergeable) {
        this.urlLink = urlLink;
        this.entityClass = (Class<Entity[]>) entityClass;
        this.iMergeable = iMergeable;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public Class getEntity() {
        return entityClass;
    }

    public void merge(String jsonResponse) throws IOException {
        // parse response with Jackson
        ObjectMapper mapper = new ObjectMapper();
        Entity[] objects = mapper.readValue(jsonResponse, entityClass);

        System.out.println(objects.toString());
        iMergeable.merge(objects);
    }
}
