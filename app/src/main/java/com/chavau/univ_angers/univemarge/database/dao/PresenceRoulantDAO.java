package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Entity;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Personne;
import com.chavau.univ_angers.univemarge.database.entities.PresenceRoulant;
import com.chavau.univ_angers.univemarge.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.chavau.univ_angers.univemarge.utils.Utils.convertStringToDate;

public class PresenceRoulantDAO extends DAO<PresenceRoulant> implements IMergeable {
    private static final String[] PROJECTION = {
            DBTables.PresenceRoulant.COLONNE_ID_ROULANT,
            DBTables.PresenceRoulant.COLONNE_ID_EVENEMENT,
            DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT,
            DBTables.PresenceRoulant.COLONNE_TEMPS,
            DBTables.PresenceRoulant.COLONNE_DATE_ENTREE,
            DBTables.PresenceRoulant.COLONNE_DATE_SORTIE,
            DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL,
            DBTables.PresenceRoulant.COLONNE_ID_AUTRE
    };

    public PresenceRoulantDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(PresenceRoulant item) {
        ContentValues values = new ContentValues();
//        values.put(DBTables.PresenceRoulant.COLONNE_ID_ROULANT, item.getIdRoulant());
        values.put(DBTables.PresenceRoulant.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT, item.getNumeroEtudiant());
        values.put(DBTables.PresenceRoulant.COLONNE_TEMPS, Utils.convertDateToString(item.getTemps()));
        values.put(DBTables.PresenceRoulant.COLONNE_DATE_ENTREE, Utils.convertDateToString(item.getDateEntree()));
        values.put(DBTables.PresenceRoulant.COLONNE_DATE_SORTIE, Utils.convertDateToString(item.getDateSortie()));
        values.put(DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL, item.getIdPersonnel());
        values.put(DBTables.PresenceRoulant.COLONNE_ID_AUTRE, item.getIdAutre());
        return values;
    }

    @Override
    public long insertItem(PresenceRoulant item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.PresenceRoulant.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, PresenceRoulant item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.PresenceRoulant.TABLE_NAME, this.getContentValues(item),
                DBTables.PresenceRoulant.COLONNE_ID_ROULANT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.PresenceRoulant.COLONNE_ID_ROULANT))});
    }

    @Override
    public int removeItem(Identifiant id, PresenceRoulant item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.PresenceRoulant.TABLE_NAME,
                DBTables.PresenceRoulant.COLONNE_ID_ROULANT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.PresenceRoulant.COLONNE_ID_ROULANT))});
    }

    @Override
    public PresenceRoulant getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.PresenceRoulant.TABLE_NAME,
                PROJECTION,
                DBTables.PresenceRoulant.COLONNE_ID_ROULANT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.PresenceRoulant.COLONNE_ID_ROULANT))},
                null,
                null,
                DBTables.PresenceRoulant.COLONNE_DATE_ENTREE
        );
        return this.cursorToType(cursor);
    }

    public Identifiant getIdFromEvenementId(int idEvenement, int idPersonne) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DBTables.PresenceRoulant.COLONNE_ID_ROULANT + " FROM " + DBTables.PresenceRoulant.TABLE_NAME +
                        " WHERE " + DBTables.PresenceRoulant.COLONNE_ID_EVENEMENT + " = ? " +
                        " AND (" + DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT + " = ? " +
                        " OR " + DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL + " = ? " +
                        " OR " + DBTables.PresenceRoulant.COLONNE_ID_AUTRE + " = ?)",
                new String[]{Integer.toString(idEvenement), Integer.toString(idPersonne), Integer.toString(idPersonne), Integer.toString(idPersonne)});

        int res = cursor.getInt(cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_ID_ROULANT));
        cursor.close();
        Identifiant identifiant = new Identifiant();

        if(new EtudiantDAO(this.helper).isEtudiant(idPersonne)) {
            identifiant.ajoutId(DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT, res);
        }
        // personnel
        else if(new PersonnelDAO(this.helper).isPersonnel(idPersonne)){
            identifiant.ajoutId(DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL, res);
        }
        else {
            identifiant.ajoutId(DBTables.PresenceRoulant.COLONNE_ID_AUTRE, res);
        }

        return identifiant;
    }

    @Override
    public PresenceRoulant cursorToType(Cursor cursor) {
        int idRoulant = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_ID_ROULANT);
        int idEvenement = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_ID_EVENEMENT);
        int numeroEtudiant = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT);
        int temps = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_TEMPS);
        int dateEntree = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_DATE_ENTREE);
        int dateSortie = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_DATE_SORTIE);
        int idPersonnel = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL);
        int idAutre = cursor.getColumnIndex(DBTables.PresenceRoulant.COLONNE_ID_AUTRE);

        return new PresenceRoulant(
                cursor.getInt(idRoulant),
                cursor.getInt(idEvenement),
                cursor.getInt(numeroEtudiant),
                convertStringToDate(cursor.getString(temps)),
                convertStringToDate(cursor.getString(dateEntree)),
                convertStringToDate(cursor.getString(dateSortie)),
                cursor.getInt(idPersonnel),
                cursor.getInt(idAutre)
        );
    }

    /**
     * retourne si la personne est déjà entré dans cette séance mais pas encore sorti.
     * @return
     */
    public boolean estDejaEntre(int idEvenement, int idPersonne) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DBTables.PresenceRoulant.COLONNE_DATE_ENTREE + " FROM " + DBTables.PresenceRoulant.TABLE_NAME +
                        " WHERE " + DBTables.PresenceRoulant.COLONNE_ID_EVENEMENT + " = ?" +
                        " AND (" + DBTables.PresenceRoulant.COLONNE_DATE_SORTIE + " = NULL " +
                        " OR " + DBTables.PresenceRoulant.COLONNE_DATE_SORTIE + " = 0)" +
                        " AND (" + DBTables.PresenceRoulant.COLONNE_ID_PERSONNEL + " = ? " +
                        " OR " + DBTables.PresenceRoulant.COLONNE_NUMERO_ETUDIANT + " = ? " +
                        " OR " + DBTables.PresenceRoulant.COLONNE_ID_AUTRE + " = ? )",
                new String[]{Integer.toString(idEvenement), Integer.toString(idPersonne), Integer.toString(idPersonne), Integer.toString(idPersonne)});

        // si le nombre d'entrées est supérieur à 0,
        // la personne est entrée mais pas sortie

        while(cursor.moveToNext()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = convertStringToDate(Integer.toString(cursor.getInt(cursor.getColumnIndex(DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT))));
            long dateDifferenceInMinutes = (new Date().getTime() - date.getTime()) / 60000;
            // si on a une entrée de cette personne sans sortie aujourd'hui pour cet evenement, alors il est déjà entrée
            if(sdf.format(new Date()).equals(sdf.format(date))
                    && dateDifferenceInMinutes > 1) { // le badgeage 2 fois d'affilée ne sera pas impacté si l'on met 1 minute minimum entre l'arrivée et le départ
                return true;
            }
        }

        return false;
    }

    /**
     * ajoute l'entrée d'une personne dans le cours
     * @return
     */
    private boolean addEntree(int idEvenement, int idPersonne) {
        if(estDejaEntre(idEvenement, idPersonne)) {
            return false;
        }

        PresenceRoulant presenceRoulant;
        // etudiant
        if(new EtudiantDAO(this.helper).isEtudiant(idPersonne)) {
            presenceRoulant = new PresenceRoulant(idEvenement, idPersonne, null, new Date(), null, 0, 0);
        }
        // personnel
        else if(new PersonnelDAO(this.helper).isPersonnel(idPersonne)){
            presenceRoulant = new PresenceRoulant(idEvenement, 0, null, new Date(), null, idPersonne, 0);
        }
        else {
            presenceRoulant = new PresenceRoulant(idEvenement, 0, null, new Date(), null, 0, idPersonne);
        }
        insertItem(presenceRoulant);

        return true;
    }

    /**
     * ajoute la sortir d'une personne du cours
     * @return
     */
    private boolean addSortie(int idEvenement, int idPersonne) {
        // TODO : faire le contenu
        if(estDejaEntre(idEvenement, idPersonne)) {
            return false;
        }

        Identifiant identifiant = getIdFromEvenementId(idEvenement, idPersonne);

        PresenceRoulant presenceRoulant = getItemById(identifiant);
        presenceRoulant.setTemps(new Date());
        presenceRoulant.setDateSortie(new Date());

        updateItem(identifiant, presenceRoulant);

        return false;
    }

    public boolean setPresenceRoulantMifare(String mifare, int idEvenement) {

        SQLiteDatabase db = super.helper.getWritableDatabase();
        Class<? extends Personne> className = new InscriptionDAO(this.helper).fromMiFareGetType(mifare);
        int idPersonne = 0;

        if(className == Etudiant.class) {
            idPersonne = new EtudiantDAO(this.helper).fromMifareGetId(mifare);
        }
        else {
            idPersonne = new PersonnelDAO(this.helper).fromMifareGetId(mifare);
        }

        boolean res;
        if(!estDejaEntre(idEvenement, idPersonne)) {
            res = addEntree(idEvenement, idPersonne);
        }
        else {
            res = addSortie(idEvenement, idPersonne);
        }

        return res;
    }

    @Override
    public void merge(Entity[] entities) {
        for (Entity e : entities) {
            PresenceRoulant presenceRoulant = (PresenceRoulant) e;
            deleteItem(presenceRoulant.getIdRoulant());
            long res = insertItem(presenceRoulant);
            if (res == -1) {
                throw new SQLException("Unable to merge PresenceRoulant Table");
            }
        }
    }

    private int deleteItem(int idRoulant) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.PresenceRoulant.TABLE_NAME, DBTables.PresenceRoulant.COLONNE_ID_ROULANT + " = ?", new String[]{String.valueOf(idRoulant)});
    }
}