package com.chavau.univ_angers.univemarge.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class Authentification_Fragment extends Fragment {

    static interface CallBacks{
        public void onItemDone();
    }

    private CallBacks mainListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainListener = (CallBacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // pour garder le fragment actif lorsque l'activité est détruite (ex: rotation)
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainListener = null;
    }

    protected void onPostExecute(){
        if(mainListener != null)
            mainListener.onItemDone();
    }


    /**
     * TACHE DE FOND
     */

    class AsyncAuthentification extends AsyncTask<Void, Void, Void>{
        protected Authentification_Fragment fragment = null;

        AsyncAuthentification(Authentification_Fragment authentification_fragment){
            fragment = authentification_fragment;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fragment.onPostExecute();
        }
    }
}
