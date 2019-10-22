package com.chavau.univ_angers.univemarge.database.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.RoulantParametre;
import com.chavau.univ_angers.univemarge.utils.Utils;

public class RoulantParametreDAO extends DAO<RoulantParametre> {
    private static final String[] PROJECTION = {
            DBTables.RoulantParametre.COLONNE_ID_EVENEMENT,
            DBTables.RoulantParametre.COLONNE_TEMPS_SEANCE,
            DBTables.RoulantParametre.COLONNE_MAX_PERSONNES,
            DBTables.RoulantParametre.COLONNE_DATE_MAJ
    };

    public RoulantParametreDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(RoulantParametre item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.RoulantParametre.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.RoulantParametre.COLONNE_TEMPS_SEANCE, Utils.convertDateToString(item.getTempsSeance()));
        values.put(DBTables.RoulantParametre.COLONNE_MAX_PERSONNES, item.getMaxPersonnes());
        values.put(DBTables.RoulantParametre.COLONNE_DATE_MAJ, Utils.convertDateToString(item.getDateMaj()));
        return values;
    }

    @Override
    public long insertItem(RoulantParametre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.RoulantParametre.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, RoulantParametre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.RoulantParametre.TABLE_NAME, this.getContentValues(item),
                DBTables.RoulantParametre.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.RoulantParametre.COLONNE_ID_EVENEMENT))});
    }

    @Override
    public int removeItem(Identifiant id, RoulantParametre item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.RoulantParametre.TABLE_NAME,
                DBTables.RoulantParametre.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.RoulantParametre.COLONNE_ID_EVENEMENT))});
    }

    @Override
    public RoulantParametre getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.RoulantParametre.TABLE_NAME,
                PROJECTION,
                DBTables.RoulantParametre.COLONNE_ID_EVENEMENT + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.RoulantParametre.COLONNE_ID_EVENEMENT))},
                null,
                null,
                DBTables.RoulantParametre.COLONNE_ID_EVENEMENT
        );
        return this.cursorToType(cursor);
    }

    @Override
    public RoulantParametre cursorToType(Cursor cursor) {
        int idEvenement = cursor.getColumnIndex(DBTables.RoulantParametre.COLONNE_ID_EVENEMENT);
        int tempsSeance = cursor.getColumnIndex(DBTables.RoulantParametre.COLONNE_TEMPS_SEANCE);
        int maxPersonnes = cursor.getColumnIndex(DBTables.RoulantParametre.COLONNE_MAX_PERSONNES);
        int dateMaj = cursor.getColumnIndex(DBTables.RoulantParametre.COLONNE_DATE_MAJ);

        return new RoulantParametre(
                cursor.getInt(idEvenement),
                Utils.convertStringToDate(cursor.getString(tempsSeance)),
                cursor.getInt(maxPersonnes),
                Utils.convertStringToDate(cursor.getString(dateMaj))
        );
    }
}
