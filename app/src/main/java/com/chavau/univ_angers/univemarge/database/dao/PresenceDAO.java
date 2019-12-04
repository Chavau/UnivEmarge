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
import com.chavau.univ_angers.univemarge.database.entities.Personne;
import com.chavau.univ_angers.univemarge.database.entities.Presence;
import com.chavau.univ_angers.univemarge.database.entities.StatutPresence;
import com.chavau.univ_angers.univemarge.utils.Utils;

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
//        values.put(DBTables.Presence.COLONNE_ID_PRESENCE, item.getIdPresence());
        values.put(DBTables.Presence.COLONNE_ID_EVENEMENT, item.getIdEvenement());
        values.put(DBTables.Presence.COLONNE_STATUT_PRESENCE, item.getStatutPresence().getValue());
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
                StatutPresence.fromInt(cursor.getInt(statutPresence)),
                (cursor.getInt(deleted) == 1),
                cursor.getInt(idPersonnel),
                cursor.getInt(idAutre),
                Utils.convertStringToDate(cursor.getString(dateMaj))
        );
    }

    /**
     * insère la nouvelle présence dans la table si la personne n'a pas encore badgé.
     * sinon effectue un update.
     * @param mifare
     * @param idEvenement
     * @param sp
     * @return
     */
    public boolean insererPresence(String mifare, int idEvenement, StatutPresence sp) {

        // nom de la colonne qui est clée primaire de la table
        String colunmName = "";
        // valeur de l'identifiant de la personne à qui appartient le mifare
        int idPersonne = 0;


        InscriptionDAO inscriptionDAO = new InscriptionDAO(this.helper);

        // de quelle classe est la personne à qui appartient le mifare
        Class<? extends Personne> className = inscriptionDAO.fromMiFareGetType(mifare);


        // nous avons affaire à un étudiant
        if(className == Etudiant.class) {
            colunmName = DBTables.Presence.COLONNE_NUMERO_ETUDIANT;
            idPersonne = new EtudiantDAO(this.helper).fromMifareGetId(mifare);
        }
        // nous avons affaire à un personnel
        else {
            colunmName = DBTables.Presence.COLONNE_NUMERO_ETUDIANT;
            idPersonne = new PersonnelDAO(this.helper).fromMifareGetId(mifare);
        }


        // si la personne n'est pas inscrite on ne peut pas insérer sa présence
        if(!inscriptionDAO.estInscrit(idEvenement, idPersonne))
            return false;


        // suppression d'une potentielle précédente présence, évite une erreur d'insertion
        deletePresenceForPersonne(idEvenement, colunmName, idPersonne);

        // insertion de la présence de la personne
        Presence presence = null;
        // etudiant
        if(className == Etudiant.class) { presence = new Presence(idEvenement, idPersonne, sp, false, 0, 0);}
        // personnel
        else { presence = new Presence(idEvenement, 0, sp, false, idPersonne, 0);}
        insertItem(presence);

        return true;
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

    private int deletePresenceForPersonne(int idEvenement, String columnName, int idPersonne) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.Presence.TABLE_NAME, DBTables.Presence.COLONNE_ID_EVENEMENT + " = ? " + columnName + " = ?", new String[]{String.valueOf(idEvenement), String.valueOf(idPersonne)});
    }

    private int deleteItem(int idPresence) {
        SQLiteDatabase db = super.helper.getWritableDatabase();
        return db.delete(DBTables.Presence.TABLE_NAME, DBTables.Presence.COLONNE_ID_PRESENCE + " = ?", new String[]{String.valueOf(idPresence)});
    }
}