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
import com.chavau.univ_angers.univemarge.database.entities.Inscription;
import com.chavau.univ_angers.univemarge.database.entities.Personne;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;

public class InscriptionDAO extends DAO<Inscription> implements IMergeable {
    private static final String[] PROJECTION = {
            DBTables.Inscription.COLONNE_ID_PERSONNEL,
            DBTables.Inscription.COLONNE_ID_INSCRIPTION,
            DBTables.Inscription.COLONNE_ID_EVENEMENT,
            DBTables.Inscription.COLONNE_NUMERO_ETUDIANT,
            DBTables.Inscription.COLONNE_DELETED,
            DBTables.Inscription.COLONNE_TYPE_INSCRIPTION,
            DBTables.Inscription.COLONNE_ID_AUTRE
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
        values.put(DBTables.Inscription.COLONNE_DELETED, item.isDeleted());
        values.put(DBTables.Inscription.COLONNE_TYPE_INSCRIPTION, item.getTypeInscription());
        values.put(DBTables.Inscription.COLONNE_ID_AUTRE, item.getIdAutre());
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
                DBTables.Inscription.COLONNE_ID_INSCRIPTION + " = ?",
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
                DBTables.Inscription.COLONNE_ID_INSCRIPTION + " = ?",
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
        int deleted = cursor.getColumnIndex(DBTables.Inscription.COLONNE_DELETED);
        int typeInscription = cursor.getColumnIndex(DBTables.Inscription.COLONNE_TYPE_INSCRIPTION);
        int idAutre = cursor.getColumnIndex(DBTables.Inscription.COLONNE_ID_AUTRE);

        return new Inscription(
                cursor.getInt(idPersonnel),
                cursor.getInt(idInscription),
                cursor.getInt(idEvenement),
                cursor.getInt(numeroEtudiant),
                (cursor.getInt(deleted) == 1),
                cursor.getString(typeInscription),
                cursor.getInt(idAutre)
        );
    }

    @Override
    public void merge(Entity[] entities) {
        for (Entity e : entities) {
            Inscription inscription = (Inscription) e;
            deleteItem(inscription.getIdInscription());
            long res = insertItem(inscription);
            if (res == -1) {
                throw new SQLException("Unable to merge Inscription Table");
            }
        }
    }

    private int deleteItem(int idInscription) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.Inscription.TABLE_NAME, DBTables.Inscription.COLONNE_ID_INSCRIPTION + " = ?", new String[]{String.valueOf(idInscription)});
    }

    /**
     * Cette méthode prend un mifare et renvoit la classe de l'objet a qui appartient ce mifare
     * @param mifare identifiant de la carte scannée
     */
    public Class<? extends Personne> fromMiFareGetType(String mifare) {

        // cas pour un étudiant
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DBTables.Etudiant.COLONNE_NUMERO_ETUDIANT + " FROM " + DBTables.Etudiant.TABLE_NAME +
                        " WHERE " + DBTables.Etudiant.COLONNE_NO_MIFARE + " = ?",
                new String[]{mifare});

        // si le nombre d'entrées est supérieur à 0,
        // ce mifare correspond donc à un étudiant
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return Etudiant.class;
        }

        // cas pour un personnel
        cursor = db.rawQuery(
                "SELECT " + DBTables.Personnel.COLONNE_ID_PERSONNEL + " FROM " + DBTables.Personnel.TABLE_NAME +
                        " WHERE " + DBTables.Personnel.COLONNE_NO_MIFARE + " = ?",
                new String[]{mifare});

        // si le nombre d'entrées est supérieur à 0,
        // ce mifare correspond donc à un personnel
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return Personnel.class;
        }
        cursor.close();

        throw new IllegalArgumentException("Unknown mifare");
    }

    /**
     * retourne un booléen décrivant si la personne est inscrite au cours
     * indiféremment de si la personne est un étudiant, un personnel ou autre
     * @param idPersonne valeur associée à l'identifiant de la personne (soit un num étudiant, soit is personnel)
     * @return si la personne est bien inscrite au cours
     */
    public boolean estInscrit(int idEvenement, int idPersonne) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBTables.Inscription.TABLE_NAME +
                        " WHERE " + DBTables.Inscription.COLONNE_ID_EVENEMENT + " = ?" +
                        " AND (" + DBTables.Inscription.COLONNE_ID_PERSONNEL + " = ? " +
                        " OR " + DBTables.Inscription.COLONNE_NUMERO_ETUDIANT + " = ? " +
                        " OR " + DBTables.Inscription.COLONNE_ID_AUTRE + " = ? )",
                new String[]{Integer.toString(idEvenement), Integer.toString(idPersonne), Integer.toString(idPersonne), Integer.toString(idPersonne)});

        // si le nombre d'entrées est supérieur à 0,
        // la personne est bel et bien inscrite
        int res = cursor.getCount();
        cursor.close();
        return res > 0;
    }
}