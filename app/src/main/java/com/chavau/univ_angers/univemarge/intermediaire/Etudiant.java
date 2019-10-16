package com.chavau.univ_angers.univemarge.intermediaire;

import android.os.Parcel;
import android.os.Parcelable;

public class Etudiant implements Parcelable {
    private String _nom;
    private String _prenom;
    private String _numEtud;
    private String _typeActivite;
    private int _imageId;

    public static final  Creator<Etudiant> CREATOR = new Creator<Etudiant>() {

        @Override
        public Etudiant createFromParcel(Parcel parcel) {
            return new Etudiant(parcel);
        }

        @Override
        public Etudiant[] newArray(int i) {
            return new Etudiant[i];
        }
    };

    // Constructeur avec paramettre

    public Etudiant (String _nom, String _prenom, String _numEtud, String _typeActivite, int _imageId) {
        this._nom = _nom;
        this._prenom = _prenom;
        this._numEtud = _numEtud;
        this._typeActivite = _typeActivite;
        this._imageId = _imageId;
    }

    // Constructeur pour le Parcelable

    public Etudiant(Parcel parcel) {
        _nom = parcel.readString();
        _prenom = parcel.readString();
        _numEtud = parcel.readString();
        _typeActivite = parcel.readString();
        _imageId = parcel.readInt();
    }

    public String get_nom() {
        return _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

    public String get_numEtud() {
        return _numEtud;
    }

    public int get_imageId() {
        return _imageId;
    }

    public String get_typeActivite() {
        return _typeActivite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(get_nom());
        parcel.writeString(get_prenom());
        parcel.writeString(get_numEtud());
        parcel.writeString(get_typeActivite());
        parcel.writeInt(get_imageId());
    }
}
