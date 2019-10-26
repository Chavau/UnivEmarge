package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Entity;
import com.chavau.univ_angers.univemarge.database.entities.PresenceRoulant;
import com.chavau.univ_angers.univemarge.utils.Utils;

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
        values.put(DBTables.PresenceRoulant.COLONNE_ID_ROULANT, item.getIdRoulant());
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
                Utils.convertStringToDate(cursor.getString(temps)),
                Utils.convertStringToDate(cursor.getString(dateEntree)),
                Utils.convertStringToDate(cursor.getString(dateSortie)),
                cursor.getInt(idPersonnel),
                cursor.getInt(idAutre)
        );
    }

    // needed to merge entities
    public PresenceRoulantDAO() {}

    @Override
    public void merge(Entity[] entities) {

    }
}
