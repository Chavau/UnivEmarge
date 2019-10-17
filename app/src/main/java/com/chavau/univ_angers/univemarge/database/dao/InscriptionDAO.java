package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Inscription;
import com.chavau.univ_angers.univemarge.utils.Utils;

public class InscriptionDAO extends DAO<Inscription> {
    private static final String[] PROJECTION = {
            DBTables.Inscription.COLONNE_ID_PERSONNEL,
            DBTables.Inscription.COLONNE_ID_INSCRIPTION,
            DBTables.Inscription.COLONNE_ID_EVENEMENT,
            DBTables.Inscription.COLONNE_NUMERO_ETUDIANT,
            DBTables.Inscription.COLONNE_DATE_MAJ,
            DBTables.Inscription.COLONNE_DELETED
    };

    public InscriptionDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(Inscription item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.Inscription.COLONNE_ID_PERSONNEL, item.getIdPersonnel());
        values.put(DBTables.Inscription.COLONNE_ID_INSCRIPTION, item.getIdInscription());
        values.put(DBTables.Inscription.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.Inscription.COLONNE_NUMERO_ETUDIANT, item.getNumeroEtudiant());
        values.put(DBTables.Inscription.COLONNE_DATE_MAJ, Utils.convertDateToString(item.getDateMaj()));
        values.put(DBTables.Inscription.COLONNE_DELETED, item.isDeleted());
        return values;
    }

    @Override
    public long insertItem(Inscription item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Inscription.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Inscription item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Inscription.TABLE_NAME, this.getContentValues(item),
                DBTables.Inscription.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Inscription.COLONNE_ID_INSCRIPTION))});
    }

    @Override
    public int removeItem(Identifiant id, Inscription item) {
        item.setDeleted(true);
        return this.updateItem(id, item);
    }

    @Override
    public Inscription getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Inscription.TABLE_NAME,
                PROJECTION,
                DBTables.Inscription.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Inscription.COLONNE_ID_INSCRIPTION))},
                null,
                null,
                null
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Inscription cursorToType(Cursor cursor) {
        int idPersonnel = cursor.getColumnIndex(DBTables.Inscription.COLONNE_ID_PERSONNEL);
        int idInscription = cursor.getColumnIndex(DBTables.Inscription.COLONNE_ID_INSCRIPTION);
        int idEvenement = cursor.getColumnIndex(DBTables.Inscription.COLONNE_ID_EVENEMENT);
        int numeroEtudiant = cursor.getColumnIndex(DBTables.Inscription.COLONNE_NUMERO_ETUDIANT);
        int dateMaj = cursor.getColumnIndex(DBTables.Inscription.COLONNE_DATE_MAJ);
        int deleted = cursor.getColumnIndex(DBTables.Inscription.COLONNE_DELETED);

        return new Inscription(
                cursor.getInt(idPersonnel),
                cursor.getInt(idInscription),
                cursor.getInt(idEvenement),
                cursor.getInt(numeroEtudiant),
                Utils.convertStringToDate(cursor.getString(dateMaj)),
                (cursor.getInt(deleted) == 1)
        );
    }
}
