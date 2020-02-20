package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Entity;
import com.chavau.univ_angers.univemarge.database.entities.Presence;
import com.chavau.univ_angers.univemarge.database.entities.StatutPresence;
import com.chavau.univ_angers.univemarge.utils.Utils;

import java.sql.PreparedStatement;

public class PresenceDAO extends DAO<Presence> implements IMergeable {
    private static final String[] PROJECTION = {
            DBTables.Presence.COLONNE_ID_PRESENCE,
            DBTables.Presence.COLONNE_ID_EVENEMENT,
            DBTables.Presence.COLONNE_STATUT_PRESENCE,
            DBTables.Presence.COLONNE_DATE_MAJ,
            DBTables.Presence.COLONNE_DELETED,
            DBTables.Presence.COLONNE_NUMERO_ETUDIANT,
            DBTables.Presence.COLONNE_ID_PERSONNEL,
            DBTables.Presence.COLONNE_ID_AUTRE
    };

    public PresenceDAO(DatabaseHelper helper) {
        super(helper);
    }

    @Override
    public ContentValues getContentValues(Presence item) {
        ContentValues values = new ContentValues();
        //values.put(DBTables.Presence.COLONNE_ID_PRESENCE, item.getIdPresence());
        values.put(DBTables.Presence.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.Presence.COLONNE_STATUT_PRESENCE, item.getStatutPresence());
        values.put(DBTables.Presence.COLONNE_DATE_MAJ, Utils.convertDateToString(item.getDateMaj()));
        values.put(DBTables.Presence.COLONNE_DELETED, item.isDeleted());
        values.put(DBTables.Presence.COLONNE_NUMERO_ETUDIANT, item.getNumeroEtudiant());
        values.put(DBTables.Presence.COLONNE_ID_PERSONNEL, item.getIdPersonnel());
        values.put(DBTables.Presence.COLONNE_ID_AUTRE, item.getIdAutre());
        return values;
    }

    @Override
    public long insertItem(Presence item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.insert(DBTables.Presence.TABLE_NAME, null, this.getContentValues(item));
    }

    @Override
    public int updateItem(Identifiant id, Presence item) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.update(DBTables.Presence.TABLE_NAME, this.getContentValues(item),
                DBTables.Presence.COLONNE_ID_PRESENCE + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Presence.COLONNE_ID_PRESENCE))});
    }

    @Override
    public int removeItem(Identifiant id, Presence item) {
        item.setDeleted(true);
        return this.updateItem(id, item);
    }

    @Override
    public Presence getItemById(Identifiant id) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        Cursor cursor = db.query(
                DBTables.Presence.TABLE_NAME,
                PROJECTION,
                DBTables.Presence.COLONNE_ID_PRESENCE + " = ?",
                new String[]{String.valueOf(id.getId(DBTables.Presence.COLONNE_ID_PRESENCE))},
                null,
                null,
                DBTables.Presence.COLONNE_STATUT_PRESENCE
        );
        return this.cursorToType(cursor);
    }

    @Override
    public Presence cursorToType(Cursor cursor) {
        int idPresence = cursor.getColumnIndex(DBTables.Presence.COLONNE_ID_PRESENCE);
        int idEvenement = cursor.getColumnIndex(DBTables.Presence.COLONNE_ID_EVENEMENT);
        int numeroEtudiant = cursor.getColumnIndex(DBTables.Presence.COLONNE_NUMERO_ETUDIANT);
        int statutPresence = cursor.getColumnIndex(DBTables.Presence.COLONNE_STATUT_PRESENCE);
        int dateMaj = cursor.getColumnIndex(DBTables.Presence.COLONNE_DATE_MAJ);
        int deleted = cursor.getColumnIndex(DBTables.Presence.COLONNE_DELETED);
        int idPersonnel = cursor.getColumnIndex(DBTables.Presence.COLONNE_ID_PERSONNEL);
        int idAutre = cursor.getColumnIndex(DBTables.Presence.COLONNE_ID_AUTRE);

        return new Presence(
                cursor.getInt(idPresence),
                cursor.getInt(idEvenement),
                cursor.getInt(numeroEtudiant),
                cursor.getInt(statutPresence),
                (cursor.getInt(deleted) == 1),
                cursor.getInt(idPersonnel),
                cursor.getInt(idAutre),
                Utils.convertStringToDate(cursor.getString(dateMaj))
        );
    }

    /**
     * insère la nouvelle présence dans la table si la personne n'a pas encore badgé.
     *  sinon effectue un update.
     *
     *
     * @return
     */
    public boolean majPresence(Presence presence) { // Il faudrait surement uiliser le update plus haut mais c'est galère à implémenter et moins facile à débugguer
        if(presence.getIdPresence() > 0) { // UPDATE
            Identifiant identifiant = new Identifiant();
            identifiant.ajoutId(DBTables.Presence.COLONNE_ID_PRESENCE, presence.getIdPresence());
            updateItem(identifiant, presence);
            return true;
        }else { // CREATE
            insertItem(presence);
            return true;
        }
    }

    // TODO : dev : factoriser les 3 prochaine fonction si possible
    public Presence getPresenceEtud(int idEvenement, int idEtudiant){
        SQLiteDatabase db = super.helper.getWritableDatabase();

        String requete = "SELECT " +
                " p." + DBTables.Presence.COLONNE_ID_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_STATUT_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_DATE_MAJ + ", " +
                " p." + DBTables.Presence.COLONNE_DELETED + ", " +
                " p." + DBTables.Presence.COLONNE_ID_AUTRE + ", " +
                " p." + DBTables.Presence.COLONNE_ID_PERSONNEL + ", " +
                " p." + DBTables.Presence.COLONNE_NUMERO_ETUDIANT + ", " +
                " p." + DBTables.Presence.COLONNE_ID_EVENEMENT +
                " FROM " + DBTables.Presence.TABLE_NAME + " p " +
                " WHERE p." + DBTables.Presence.COLONNE_ID_EVENEMENT + " = ? " +
                " AND p." + DBTables.Presence.COLONNE_NUMERO_ETUDIANT + " = ? ";

        Cursor cursor = db.rawQuery(requete, new String[]{String.valueOf(idEvenement),String.valueOf(idEtudiant)});

        if(cursor.moveToNext()) {
            return this.cursorToType(cursor);
        }
        return null;
    }

    public Presence getPresencePersonnel(int idEvenement, int idPersonnel){
        SQLiteDatabase db = super.helper.getWritableDatabase();

        String requete = "SELECT " +
                " p." + DBTables.Presence.COLONNE_ID_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_STATUT_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_DATE_MAJ + ", " +
                " p." + DBTables.Presence.COLONNE_DELETED + ", " +
                " p." + DBTables.Presence.COLONNE_ID_AUTRE + ", " +
                " p." + DBTables.Presence.COLONNE_ID_PERSONNEL + ", " +
                " p." + DBTables.Presence.COLONNE_NUMERO_ETUDIANT + ", " +
                " p." + DBTables.Presence.COLONNE_ID_EVENEMENT +
                " FROM " + DBTables.Presence.TABLE_NAME + " p " +
                " WHERE p." + DBTables.Presence.COLONNE_ID_EVENEMENT + " = ? " +
                " AND p." + DBTables.Presence.COLONNE_ID_PERSONNEL + " = ? ";

        Cursor cursor = db.rawQuery(requete, new String[]{String.valueOf(idEvenement),String.valueOf(idPersonnel)});

        if(cursor.moveToNext()) {
            return this.cursorToType(cursor);
        }
        return null;
    }

    public Presence getPresenceAutre(int idEvenement, int idAutre){
        SQLiteDatabase db = super.helper.getWritableDatabase();

        String requete = "SELECT " +
                " p." + DBTables.Presence.COLONNE_ID_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_STATUT_PRESENCE + ", " +
                " p." + DBTables.Presence.COLONNE_DATE_MAJ + ", " +
                " p." + DBTables.Presence.COLONNE_DELETED + ", " +
                " p." + DBTables.Presence.COLONNE_ID_AUTRE + ", " +
                " p." + DBTables.Presence.COLONNE_ID_PERSONNEL + ", " +
                " p." + DBTables.Presence.COLONNE_NUMERO_ETUDIANT + ", " +
                " p." + DBTables.Presence.COLONNE_ID_EVENEMENT +
                " FROM " + DBTables.Presence.TABLE_NAME + " p " +
                " WHERE p." + DBTables.Presence.COLONNE_ID_EVENEMENT + " = ? " +
                " AND p." + DBTables.Presence.COLONNE_ID_AUTRE + " = ? ";

        Cursor cursor = db.rawQuery(requete, new String[]{String.valueOf(idEvenement),String.valueOf(idAutre)});

        if(cursor.moveToNext()) {
            return this.cursorToType(cursor);
        }
        return null;
    }

    @Override
    public void merge(Entity[] entities) {
        for (Entity e : entities) {
            Presence presence = (Presence) e;
            deleteItem(presence.getIdPresence());
            long res = insertItem(presence);
            if (res == -1) {
                throw new SQLException("Unable to merge Presence Table");
            }
        }
    }

    private int deleteItem(int idPresence) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.Presence.TABLE_NAME, DBTables.Presence.COLONNE_ID_PRESENCE + " = ?", new String[]{String.valueOf(idPresence)});
    }
}