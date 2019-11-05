package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Responsable;
import com.chavau.univ_angers.univemarge.utils.Utils;

public class ResponsableDAO extends DAO<Responsable> {
    private static final String[] PROJECTION = {
            DBTables.Responsable.COLONNE_ID_EVENEMENT,
            DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE,
            DBTables.Responsable.COLONNE_DATE_MAJ,
            DBTables.Responsable.COLONNE_DELETED
    };

    public ResponsableDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(Responsable item) {
        ContentValues values = new ContentValues();
        values.put(DBTables.Responsable.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE, item.getIdPersonnelResponsable());
        values.put(DBTables.Responsable.COLONNE_DATE_MAJ, Utils.convertDateToString(item.getDateMaj()));
        values.put(DBTables.Responsable.COLONNE_DELETED, item.isDeleted());
        return values;
    }

    @Override
    public long insertItem(Responsable item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Responsable.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Responsable item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Responsable.TABLE_NAME, this.getContentValues(item),
                DBTables.Responsable.COLONNE_ID_EVENEMENT + " = ? AND " +
                        DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE + " = ? ",
                new String[]{String.valueOf(id.getId(DBTables.Responsable.COLONNE_ID_EVENEMENT)),
                        String.valueOf(id.getId(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE))});
    }

    @Override
    public int removeItem(Identifiant id, Responsable item) {
        item.setDeleted(true);
        return this.updateItem(id, item);
    }

    @Override
    public Responsable getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Responsable.TABLE_NAME,
                PROJECTION,
                DBTables.Responsable.COLONNE_ID_EVENEMENT + " = ? AND " +
                        DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE + " = ? ",
                new String[]{String.valueOf(id.getId(DBTables.Responsable.COLONNE_ID_EVENEMENT)),
                        String.valueOf(id.getId(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE))},
                null,
                null,
                null
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Responsable cursorToType(Cursor cursor) {
        int idEvenement = cursor.getColumnIndex(DBTables.Responsable.COLONNE_ID_EVENEMENT);
        int idPersonnelResponsable = cursor.getColumnIndex(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE);
        int dateMaj = cursor.getColumnIndex(DBTables.Responsable.COLONNE_DATE_MAJ);
        int deleted = cursor.getColumnIndex(DBTables.Responsable.COLONNE_DELETED);

        return new Responsable(
                cursor.getInt(idEvenement),
                cursor.getInt(idPersonnelResponsable),
                Utils.convertStringToDate(cursor.getString(dateMaj)),
                (cursor.getInt(deleted) == 1)
        );
    }
}
