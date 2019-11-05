package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.utils.Utils;

public class EtudiantDAO extends DAO<Etudiant> {

    private static final String[] PROJECTION = {
            DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT,
            DBTables.Etudiant.COLONNE_NOM,
            DBTables.Etudiant.COLONNE_PRENOM,
            DBTables.Etudiant.COLONNE_NO_MIFARE,
            DBTables.Etudiant.COLONNE_EMAIL,
            DBTables.Etudiant.COLONNE_PHOTO,
            DBTables.Etudiant.COLONNE_DATE_MAJ,
            DBTables.Etudiant.COLONNE_DELETED
    };

    public EtudiantDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(Etudiant item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT, item.getNumeroEtudiant());
        values.put(DBTables.Etudiant.COLONNE_NOM, item.getNom());
        values.put(DBTables.Etudiant.COLONNE_PRENOM, item.getPrenom());
        values.put(DBTables.Etudiant.COLONNE_NO_MIFARE, item.getNo_mifare());
        values.put(DBTables.Etudiant.COLONNE_EMAIL, item.getEmail());
        // TODO: convert java.Blob to sql.blob
//        values.put(DBTables.Etudiant.COLONNE_PHOTO, item.getPhoto());
        values.put(DBTables.Etudiant.COLONNE_DATE_MAJ, Utils.convertDateToString(item.getDateMaj()));
        values.put(DBTables.Etudiant.COLONNE_DELETED, item.isDeleted());
        return values;
    }

    @Override
    public long insertItem(Etudiant item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Etudiant.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Etudiant item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Etudiant.TABLE_NAME, this.getContentValues(item),
                DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT))});
    }

    @Override
    public int removeItem(Identifiant id, Etudiant item) {
        item.setDeleted(true);
        return this.updateItem(id, item);
    }

    @Override
    public Etudiant getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Etudiant.TABLE_NAME,
                PROJECTION,
                DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT))},
                null,
                null,
                DBTables.Etudiant.COLONNE_NOM
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Etudiant cursorToType(Cursor cursor) {
        int numeroEtudiant = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT);
        int nom = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_NOM);
        int prenom = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_PRENOM);
        int no_mifare = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_NO_MIFARE);
        int email = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_EMAIL);
        int photo = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_EMAIL);
        int dateMaj = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_DATE_MAJ);
        int deleted = cursor.getColumnIndex(DBTables.Etudiant.COLONNE_DELETED);

        // TODO: convert sql.blob to java.Blob
//                cursor.getBlob(photo),
        return new Etudiant(
                cursor.getInt(numeroEtudiant),
                cursor.getString(nom),
                cursor.getString(prenom),
                cursor.getString(no_mifare),
                cursor.getString(email),
                null,
                Utils.convertStringToDate(cursor.getString(dateMaj)),
                (cursor.getInt(deleted) == 1)
        );
    }
}
