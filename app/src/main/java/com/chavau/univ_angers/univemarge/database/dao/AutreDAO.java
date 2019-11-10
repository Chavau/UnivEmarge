package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;

import java.util.ArrayList;

public class AutreDAO extends DAO<Autre> {
    private static final String[] PROJECTION = {
            DBTables.Autre.COLONNE_ID_AUTRE,
            DBTables.Autre.COLONNE_NOM,
            DBTables.Autre.COLONNE_PRENOM,
            DBTables.Autre.COLONNE_EMAIL
    };

    public AutreDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(Autre item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.Autre.COLONNE_ID_AUTRE, item.getIdAutre());
        values.put(DBTables.Autre.COLONNE_NOM, item.getNom());
        values.put(DBTables.Autre.COLONNE_PRENOM, item.getPrenom());
        values.put(DBTables.Autre.COLONNE_EMAIL, item.getEmail());
        return values;
    }

    @Override
    public long insertItem(Autre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Autre.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Autre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Autre.TABLE_NAME, this.getContentValues(item),
                DBTables.Autre.COLONNE_ID_AUTRE + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Autre.COLONNE_ID_AUTRE))});
    }

    @Override
    public int removeItem(Identifiant id, Autre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.Autre.TABLE_NAME,
                DBTables.Autre.COLONNE_ID_AUTRE + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Autre.COLONNE_ID_AUTRE))});
    }

    @Override
    public Autre getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Autre.TABLE_NAME,
                PROJECTION,
                DBTables.Autre.COLONNE_ID_AUTRE + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Autre.COLONNE_ID_AUTRE))},
                null,
                null,
                DBTables.Autre.COLONNE_NOM
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Autre cursorToType(Cursor cursor) {
        int idAutre = cursor.getColumnIndex(DBTables.Autre.COLONNE_ID_AUTRE);
        int nom = cursor.getColumnIndex(DBTables.Autre.COLONNE_NOM);
        int prenom = cursor.getColumnIndex(DBTables.Autre.COLONNE_PRENOM);
        int email = cursor.getColumnIndex(DBTables.Autre.COLONNE_EMAIL);

        return new Autre(
                cursor.getString(nom),
                cursor.getString(prenom),
                cursor.getString(email),
                cursor.getInt(idAutre)
        );
    }

    public ArrayList<Autre> listePersonnelInscrit(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBTables.Autre.TABLE_NAME +
                        " INNER JOIN " + DBTables.Inscription.TABLE_NAME +
                        " WHERE " + DBTables.Autre.COLONNE_ID_AUTRE + " = ? ",
                new String[]{String.valueOf(id.getId(DBTables.Inscription.COLONNE_ID_AUTRE))});

        ArrayList<Autre> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(this.cursorToType(cursor));
        }
        return list;
    }

}