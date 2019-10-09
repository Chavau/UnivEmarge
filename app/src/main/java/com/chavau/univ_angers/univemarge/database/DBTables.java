package com.chavau.univ_angers.univemarge.database;

import android.provider.BaseColumns;

public final class DBTables {

    // Empeche la classe d'etre instancié
    private DBTables() {
    }

    // Table Personnel
    public static class Personnel implements BaseColumns {
        public static final String TABLE_NAME = "personnel";
        public static final String COLONNE_ID_PERSONNEL = "idPersonnel";
        public static final String COLONNE_NOM = "nom";
        public static final String COLONNE_PRENOM = "prenom";
        public static final String COLONNE_LOGIN = "login";
        public static final String COLONNE_EMAIL = "email";
        public static final String COLONNE_PHOTO = "photo";
        public static final String COLONNE_NO_MIFARE = "no_mifare";
        public static final String COLONNE_PIN = "pin";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Personnel.TABLE_NAME + " (" +
                        Personnel.COLONNE_ID_PERSONNEL + " INTEGER PRIMARY KEY, " +
                        Personnel.COLONNE_NOM + " varchar(255), " +
                        Personnel.COLONNE_PRENOM + " varchar(255), " +
                        Personnel.COLONNE_LOGIN + " varchar(255), " +
                        Personnel.COLONNE_EMAIL + " varchar(255), " +
                        Personnel.COLONNE_PHOTO + " blob, " +
                        Personnel.COLONNE_NO_MIFARE + " varchar(20), " +
                        Personnel.COLONNE_PIN + " varchar(20), " +
                        Personnel.COLONNE_DATE_MAJ + " varchar(255), " +
                        Personnel.COLONNE_DELETED + " integer)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Personnel.TABLE_NAME;

    }

    // Table Etudiant
    public static class Etudiant implements BaseColumns {
        public static final String TABLE_NAME = "etudiant";
        public static final String COLONNE_NUMERO_ETUDIANT = "numeroEtudiant";
        public static final String COLONNE_NOM = "nom";
        public static final String COLONNE_PRENOM = "prenom";
        public static final String COLONNE_NO_MIFARE = "no_mifare";
        public static final String COLONNE_EMAIL = "email";
        public static final String COLONNE_PHOTO = "photo";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Etudiant.TABLE_NAME + " (" +
                        Etudiant.COLONNE_NUMERO_ETUDIANT + " integer, " +
                        Etudiant.COLONNE_NOM + " varchar(200), " +
                        Etudiant.COLONNE_PRENOM + " varchar(200), " +
                        Etudiant.COLONNE_NO_MIFARE + " varchar(20), " +
                        Etudiant.COLONNE_EMAIL + " varchar(200), " +
                        Etudiant.COLONNE_PHOTO + " blob, " +
                        Etudiant.COLONNE_DATE_MAJ + " varchar(255), " +
                        Etudiant.COLONNE_DELETED + " integer)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Etudiant.TABLE_NAME;

    }

    // Table Cours
    public static class Cours implements BaseColumns {
        public static final String TABLE_NAME = "cours";
        public static final String COLONNE_ID_COURS = "idCours";
        public static final String COLONNE_LIBELLE_COURS = "libelle_cours";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Cours.TABLE_NAME + " (" +
                        Cours.COLONNE_ID_COURS + " INTEGER PRIMARY KEY, " +
                        Cours.COLONNE_LIBELLE_COURS + " varchar(255), " +
                        Cours.COLONNE_DATE_MAJ + " varchar(255), " +
                        Cours.COLONNE_DELETED + " integer)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Cours.TABLE_NAME;

    }

    // Table Roulant Parametre
    public static class RoulantParametre implements BaseColumns {
        public static final String TABLE_NAME = "roulant_parametre";
        public static final String COLONNE_ID_COURS = "idCours";
        public static final String COLONNE_TEMPS_SEANCE = "tempsSeance";
        public static final String COLONNE_MAX_PERSONNES = "maxPersonnes";
        public static final String COLONNE_DATE_MAJ = "dateMaj";


        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + RoulantParametre.TABLE_NAME + " (" +
                        RoulantParametre._ID + " INTEGER PRIMARY KEY, " +
                        RoulantParametre.COLONNE_ID_COURS + " integer, " +
                        RoulantParametre.COLONNE_TEMPS_SEANCE + " varchar(255), " +
                        RoulantParametre.COLONNE_MAX_PERSONNES + " integer, " +
                        RoulantParametre.COLONNE_DATE_MAJ + " varchar(255), " +
                        "CONSTRAINT fk_cours FOREIGN KEY (" + RoulantParametre.COLONNE_ID_COURS + ") REFERENCES " + Cours.TABLE_NAME + "(" + Cours.COLONNE_ID_COURS + "))";


        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + RoulantParametre.TABLE_NAME;

    }

    // Table Evenement
    public static class Evenement implements BaseColumns {
        public static final String TABLE_NAME = "evenement";
        public static final String COLONNE_ID_EVENEMENT = "idEvenement";
        public static final String COLONNE_DATE_DEBUT = "dateDebut";
        public static final String COLONNE_DATE_FIN = "dateFin";
        public static final String COLONNE_LIEU = "lieu";
        public static final String COLONNE_TEMOIN_ROULANT = "temoinRoulant";
        public static final String COLONNE_LIBELLE_EVENEMENT = "libelleEvenement";
        public static final String COLONNE_ID_COURS = "idCours";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Evenement.TABLE_NAME + " (" +
                        Evenement.COLONNE_ID_EVENEMENT + " integer PRIMARY KEY, " +
                        Evenement.COLONNE_DATE_DEBUT + " varchar(255), " +
                        Evenement.COLONNE_DATE_FIN + " varchar(255), " +
                        Evenement.COLONNE_LIEU + " varchar(200), " +
                        Evenement.COLONNE_TEMOIN_ROULANT + " integer, " +
                        Evenement.COLONNE_LIBELLE_EVENEMENT + " varchar(200), " +
                        Evenement.COLONNE_ID_COURS + " integer, " +
                        Evenement.COLONNE_DATE_MAJ + " varchar(255), " +
                        Evenement.COLONNE_DELETED + " integer, " +
                        "CONSTRAINT fk_cours FOREIGN KEY (" + Evenement.COLONNE_ID_COURS + ") REFERENCES " + Cours.TABLE_NAME + "(" + Cours._ID + "))";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Evenement.TABLE_NAME;

    }

    // Table Responsable
    public static class Responsable implements BaseColumns {
        public static final String TABLE_NAME = "responsable";
        public static final String COLONNE_ID_EVENEMENT = "idEvenement";
        public static final String COLONNE_ID_PERSONNEL_RESPONSABLE = "idPersonnelResponsable";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Responsable.TABLE_NAME + " (" +
                        Responsable.COLONNE_ID_EVENEMENT + " integer, " +
                        Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE + " integer, " +
                        Responsable.COLONNE_DATE_MAJ + " varchar(255), " +
                        Responsable.COLONNE_DELETED + " integer, " +
                        "CONSTRAINT pk_responsable PRIMARY KEY (" + Responsable.COLONNE_ID_EVENEMENT + ", " + Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE + ")" +
                        "CONSTRAINT fk_evenement FOREIGN KEY (" + Responsable.COLONNE_ID_EVENEMENT + ") REFERENCES " + Evenement.TABLE_NAME + "(" + Evenement.COLONNE_ID_EVENEMENT + "))";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Responsable.TABLE_NAME;

    }

    // Table Inscription
    public static class Inscription implements BaseColumns {
        public static final String TABLE_NAME = "inscription";
        public static final String COLONNE_ID_PERSONNEL = "idPersonnel";
        public static final String COLONNE_ID_INSCRIPTION = "idInscription";
        public static final String COLONNE_ID_EVENEMENT = "idEvenement";
        public static final String COLONNE_NUMERO_ETUDIANT = "numeroEtudiant";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Inscription.TABLE_NAME + " (" +
                        Inscription.COLONNE_ID_PERSONNEL + " INTEGER, " +
                        Inscription.COLONNE_ID_INSCRIPTION + " INTEGER PRIMARY KEY, " +
                        Inscription.COLONNE_ID_EVENEMENT + " integer, " +
                        Inscription.COLONNE_NUMERO_ETUDIANT + " integer, " +
                        Inscription.COLONNE_DATE_MAJ + " varchar(255), " +
                        Inscription.COLONNE_DELETED + " integer, " +
                        "CONSTRAINT fk_personnel FOREIGN KEY (" + Inscription.COLONNE_ID_PERSONNEL + ") REFERENCES " + Personnel.TABLE_NAME + "(" + Personnel.COLONNE_ID_PERSONNEL + "), " +
                        "CONSTRAINT fk_evenement FOREIGN KEY (" + Inscription.COLONNE_ID_EVENEMENT + ") REFERENCES " + Evenement.TABLE_NAME + "(" + Evenement.COLONNE_ID_EVENEMENT + "))";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Inscription.TABLE_NAME;

    }

    // Table Presence
    public static class Presence implements BaseColumns {
        public static final String TABLE_NAME = "presence";
        public static final String COLONNE_ID_PRESENCE = "idSeance";
        public static final String COLONNE_ID_INSCRIPTION = "idInscription";
        public static final String COLONNE_NUMERO_ETUDIANT = "numeroEtudiant";
        public static final String COLONNE_STATUT_PRESENCE = "statutPresence";
        public static final String COLONNE_DATE_MAJ = "dateMaj";
        public static final String COLONNE_DELETED = "deleted";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Presence.TABLE_NAME + " (" +
                        Presence.COLONNE_ID_PRESENCE + " INTEGER PRIMARY KEY, " +
                        Presence.COLONNE_ID_INSCRIPTION + " integer, " +
                        Presence.COLONNE_NUMERO_ETUDIANT + " integer, " +
                        Presence.COLONNE_STATUT_PRESENCE + " integer, " +
                        Presence.COLONNE_DATE_MAJ + " varchar(255), " +
                        Presence.COLONNE_DELETED + " integer, " +
                        "CONSTRAINT fk_inscription FOREIGN KEY (" + Presence.COLONNE_ID_INSCRIPTION + ") REFERENCES " + Inscription.TABLE_NAME + "(" + Inscription.COLONNE_ID_INSCRIPTION + "))";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Presence.TABLE_NAME;

    }

    // Table Presence_roulant
    public static class PresenceRoulant implements BaseColumns {
        public static final String TABLE_NAME = "presence_roulant";
        public static final String COLONNE_ID_ROULANT = "idRoulant";
        public static final String COLONNE_ID_INSCRIPTION = "idInscription";
        public static final String COLONNE_TEMPS = "temps";
        public static final String COLONNE_ENTREES_SORTIES = "entrees_sorties";
//        TODO: Update this table
//        public static final String COLONNE_DATE_ENTREE = "dateEntree";
//        public static final String COLONNE_DATE_SORTIE = "dateSortie";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PresenceRoulant.TABLE_NAME + " (" +
                        PresenceRoulant.COLONNE_ID_ROULANT + " INTEGER PRIMARY KEY, " +
                        PresenceRoulant.COLONNE_ID_INSCRIPTION + " INTEGER, " +
                        PresenceRoulant.COLONNE_TEMPS + " varchar(255), " +
                        PresenceRoulant.COLONNE_ENTREES_SORTIES + " varchar(20)," +
                        "CONSTRAINT fk_inscription FOREIGN KEY (" + PresenceRoulant.COLONNE_ID_INSCRIPTION + ") REFERENCES " + Inscription.TABLE_NAME + "(" + Inscription.COLONNE_ID_INSCRIPTION + "))";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + PresenceRoulant.TABLE_NAME;

    }
}
