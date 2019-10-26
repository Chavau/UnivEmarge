package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Evenement;
import com.chavau.univ_angers.univemarge.utils.Utils;
import com.chavau.univ_angers.univemarge.database.entities.Entity;

public class EvenementDAO extends DAO<Evenement> implements IMergeable {

    private static final String[] PROJECTION = {
            DBTables.Evenement.COLONNE_ID_EVENEMENT,
            DBTables.Evenement.COLONNE_DATE_DEBUT,
            DBTables.Evenement.COLONNE_DATE_FIN,
            DBTables.Evenement.COLONNE_LIEU,
            DBTables.Evenement.COLONNE_TYPE_EMARGEMENT,
            DBTables.Evenement.COLONNE_LIBELLE_EVENEMENT,
            DBTables.Evenement.COLONNE_ID_COURS,
            DBTables.Evenement.COLONNE_DELETED
    };

    public EvenementDAO(DatabaseHelper helper) {
        super(helper);
    }

    // needed to merge entities
    public EvenementDAO() {}

    @Override
    public void merge(Entity[] entities) {

    }

    @Override
    public ContentValues getContentValues(Evenement item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.Evenement.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.Evenement.COLONNE_DATE_DEBUT, Utils.convertDateToString(item.getDateDebut()));
        values.put(DBTables.Evenement.COLONNE_DATE_FIN, Utils.convertDateToString(item.getDateFin()));
        values.put(DBTables.Evenement.COLONNE_LIEU, item.getLieu());
        values.put(DBTables.Evenement.COLONNE_TYPE_EMARGEMENT, item.getTypeEmargement());
        values.put(DBTables.Evenement.COLONNE_LIBELLE_EVENEMENT, item.getLibelleEvenement());
        values.put(DBTables.Evenement.COLONNE_ID_COURS, item.getIdCours());
        values.put(DBTables.Evenement.COLONNE_DELETED, item.isDeleted());
        return values;
    }

    @Override
    public long insertItem(Evenement item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Evenement.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Evenement item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Evenement.TABLE_NAME, this.getContentValues(item),
                DBTables.Evenement.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Evenement.COLONNE_ID_EVENEMENT))});
    }

    @Override
    public int removeItem(Identifiant id, Evenement item) {
        item.setDeleted(true);
        return this.updateItem(id, item);
    }

    @Override
    public Evenement getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Evenement.TABLE_NAME,
                PROJECTION,
                DBTables.Evenement.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Evenement.COLONNE_ID_EVENEMENT))},
                null,
                null,
                DBTables.Evenement.COLONNE_DATE_DEBUT
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Evenement cursorToType(Cursor cursor) {
        int idEvenement = cursor.getColumnIndex(DBTables.Evenement.COLONNE_ID_EVENEMENT);
        int dateDebut = cursor.getColumnIndex(DBTables.Evenement.COLONNE_DATE_DEBUT);
        int dateFin = cursor.getColumnIndex(DBTables.Evenement.COLONNE_DATE_FIN);
        int lieu = cursor.getColumnIndex(DBTables.Evenement.COLONNE_LIEU);
        int typeEmargement = cursor.getColumnIndex(DBTables.Evenement.COLONNE_TYPE_EMARGEMENT);
        int libelleEvenement = cursor.getColumnIndex(DBTables.Evenement.COLONNE_LIBELLE_EVENEMENT);
        int idCours = cursor.getColumnIndex(DBTables.Evenement.COLONNE_ID_COURS);
        int deleted = cursor.getColumnIndex(DBTables.Evenement.COLONNE_DELETED);
        return new Evenement(
                cursor.getInt(idEvenement),
                Utils.convertStringToDate(cursor.getString(dateDebut)),
                Utils.convertStringToDate(cursor.getString(dateFin)),
                cursor.getString(lieu),
                cursor.getInt(typeEmargement),
                cursor.getString(libelleEvenement),
                cursor.getInt(idCours),
                (cursor.getInt(deleted) == 1)
        );
    }

    // TODO: tester listeEvenementsPourPersonnel
    public ArrayList<Evenement> listeEvenementsPourPersonnel(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBTables.Evenement.TABLE_NAME +
                        " INNER JOIN " + DBTables.Responsable.TABLE_NAME +
                        " WHERE " + DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE + " = ? ",
                new String[]{String.valueOf(id.getId(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE))});

        ArrayList<Evenement> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(this.cursorToType(cursor));
        }
        return list;
    }
}