package com.chavau.univ_angers.univemarge.view.fragment;

import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Authentification_Fragment extends Fragment {

    public static interface CallBacks{
        public void onItemDone(boolean result,String login);
    }

    private String login;
    private CallBacks mainListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainListener = (CallBacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login = "";
        // pour garder le fragment actif lorsque l'activité est détruite (ex: rotation)
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainListener = null;
    }

    protected void onPostExecute(boolean result){
        if(mainListener != null)
            mainListener.onItemDone(result,login);
    }

    public void requete_connexion(String login, String mdp){

        this.login = login;

        ArrayList<String> tab = new ArrayList<>();
        tab.add(login);
        tab.add(mdp);

        AsyncAuthentification requete = new AsyncAuthentification(this);
        requete.execute(tab);
    }


    /**
     * TACHE DE FOND
     */

    class AsyncAuthentification extends AsyncTask<ArrayList<String>, Void, Boolean>{
        protected Authentification_Fragment fragment = null;

        private static final String URL_CAS = "https://casv6.univ-angers.fr/cas/v1/tickets";


        AsyncAuthentification(Authentification_Fragment authentification_fragment){
            fragment = authentification_fragment;
        }

        /**
         * Tache de fond
         * @param arrayLists
         * @return true si la personne est reconnu par le CAS, false sinon
         */
        @Override
        protected Boolean doInBackground(ArrayList<String>... arrayLists) {
            Boolean retour = false;
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("username", arrayLists[0].get(0)) // recupération du login
                    .add("password", arrayLists[0].get(1)) // recuperation du mdp
                    .build();

            Request requestok = new Request.Builder()
                    .url(URL_CAS)
                    .post(formBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cache-Control", "no-cache")
                    .build();

            try {

                Response responseok = client.newCall(requestok).execute();
                retour =  responseok.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return retour;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            fragment.onPostExecute(result);
        }
    }
}
