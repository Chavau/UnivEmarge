package com.chavau.univ_angers.univemarge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "univemarge.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBTables.Personnel.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Etudiant.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Cours.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.RoulantParametre.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Evenement.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Responsable.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Inscription.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.Presence.SQL_CREATE_ENTRIES);
        db.execSQL(DBTables.PresenceRoulant.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DBTables.Personnel.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Etudiant.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Cours.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.RoulantParametre.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Evenement.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Responsable.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Inscription.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.Presence.SQL_DELETE_ENTRIES);
        db.execSQL(DBTables.PresenceRoulant.SQL_DELETE_ENTRIES);
        this.onCreate(db);
    }
}
