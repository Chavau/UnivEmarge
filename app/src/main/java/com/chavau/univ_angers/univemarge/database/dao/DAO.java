package com.chavau.univ_angers.univemarge.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Personne;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;

import java.util.ArrayList;

public abstract class DAO<Type> {

    protected DatabaseHelper helper;

    public DAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    public abstract ContentValues getContentValues(Type item);

    public abstract long insertItem(Type item);
    public abstract int updateItem(Identifiant id, Type item);
    public abstract int removeItem(Identifiant id, Type item);

    public abstract Type getItemById(Identifiant id);

    public abstract Type cursorToType(Cursor cursor);
/*
    public final ArrayList<Personne> listePersonneInscrit(ArrayList<Etudiant> etudiants,
                                                    ArrayList<Personnel> personnels,
                                                    ArrayList<Autre> autres) {
        ArrayList<Personne> list = new ArrayList<>();
        list.addAll(etudiants);
        list.addAll(personnels);
        list.addAll(autres);
        return list;
    }
*/
}
