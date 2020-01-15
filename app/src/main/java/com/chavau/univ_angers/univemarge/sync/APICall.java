package com.chavau.univ_angers.univemarge.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.dao.AutreDAO;
import com.chavau.univ_angers.univemarge.database.dao.EtudiantDAO;
import com.chavau.univ_angers.univemarge.database.dao.EvenementDAO;
import com.chavau.univ_angers.univemarge.database.dao.InscriptionDAO;
import com.chavau.univ_angers.univemarge.database.dao.PersonnelDAO;
import com.chavau.univ_angers.univemarge.database.dao.PresenceDAO;
import com.chavau.univ_angers.univemarge.database.dao.PresenceRoulantDAO;
import com.chavau.univ_angers.univemarge.database.dao.ResponsableDAO;
import com.chavau.univ_angers.univemarge.database.dao.RoulantParametreDAO;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Evenement;
import com.chavau.univ_angers.univemarge.database.entities.Inscription;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;
import com.chavau.univ_angers.univemarge.database.entities.Presence;
import com.chavau.univ_angers.univemarge.database.entities.PresenceRoulant;
import com.chavau.univ_angers.univemarge.database.entities.Responsable;
import com.chavau.univ_angers.univemarge.database.entities.RoulantParametre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICall extends Fragment {

    boolean mReady = false;
    boolean mQuiting = false;
    private static final String API_URL = "https://api-emaua.univ-angers.fr/api-emaua/";
    private static final String LOGIN_INPUT_NAME = "login=";
    private static final String DATE_INPUT_NAME = "datemaj=";
    private static final int WAIT_TIME_SEC = 7200000;
    private Thread mThread;
    private Context context;
    private Date dateMaj;

    private void sendRequest(SyncElement element) {
        System.out.println("synchronising " + element.getLink());

        // TODO : remplacer par l'appel qui permet de récupérer ces 2 valeurs
        final String login = "t.delestang";
        //final String dateMaj = "201910010000";
        final String dateMaj = null;


        // set up parameters & url
        String parameters = LOGIN_INPUT_NAME + login;
        if(dateMaj != null && !"".equals(dateMaj)) {
            parameters += "&" + DATE_INPUT_NAME + dateMaj;
        }
        final String url = API_URL + element.getLink() + "?" + parameters;


        // set up the http request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();


        // make the call to the api
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("ERROR : cannot merge " + element.getLink());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    // send the response to merge to out data
                    if (response.body() != null) {
                        final String reponse = Objects.requireNonNull(response.body()).string();
                        System.out.println("Response received for " + element.getLink());

                        if("[]".equals(reponse)) {
                            System.out.println("WARNING : empty data for " + element.getLink());
                        } else {
                            element.merge(reponse);
                            System.out.println(element.getLink() + " has successfully been merged");
                        }
                    }
                }
            }
        });
    }

    public boolean testInternetConnection() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }

        return false;
    }

    /**
     * Fragment initialization. We way we want to be retained and
     * start our thread.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell the framework to try to keep this fragment around
        // during a configuration change.
        setRetainInstance(true);

        // create the thread
        this.mThread = new Thread() {
            @Override
            public void run() {

                // This thread runs almost forever.
                while (true) {

                    // update only if connected to internet
                    if(testInternetConnection()) {
                        sendRequest(new SyncElement("/autres", Autre[].class, new AutreDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/etudiants", Etudiant[].class, new EtudiantDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/evenements", Evenement[].class, new EvenementDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/inscriptions", Inscription[].class, new InscriptionDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/personnels", Personnel[].class, new PersonnelDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/presences", Presence[].class, new PresenceDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/presence_roulants", PresenceRoulant[].class, new PresenceRoulantDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/responsables", Responsable[].class, new ResponsableDAO(new DatabaseHelper(context))));
                        sendRequest(new SyncElement("/roulant_parametre", RoulantParametre[].class, new RoulantParametreDAO(new DatabaseHelper(context))));

                        // TODO : re-assign the new DateMaj
                        setDateMaj(new Date());
                    }

                    // we wait 2h to do another sync
                    synchronized (this) {
                        try {
                            wait(WAIT_TIME_SEC);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        };
        // Start up the worker thread.
        mThread.start();
    }

    /**
     * This is called when the Fragment's Activity is ready to go, after
     * its content view has been installed; it is called both after
     * the initial fragment creation and after the fragment is re-attached
     * to a new activity.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We are ready for our thread to go.
        synchronized (mThread) {
            mReady = true;
            mThread.notify();
        }
    }

    /**
     * This is called when the fragment is going away.  It is NOT called
     * when the fragment is being propagated between activity instances.
     */
    @Override
    public void onDestroy() {
        // Make the thread go away.
        synchronized (mThread) {
            mReady = false;
            mQuiting = true;
            mThread.notify();
        }

        super.onDestroy();
    }

    /**
     * This is called right before the fragment is detached from its
     * current activity instance.
     */
    @Override
    public void onDetach() {
        // This fragment is being detached from its activity.  We need
        // to make sure its thread is not going to touch any activity
        // state after returning from this function.
        synchronized (mThread) {
            mReady = false;
            mThread.notify();
        }

        super.onDetach();
    }

    /**
     * API for our UI to restart the progress thread.
     */
    public void restart() {
        synchronized (mThread) {
            mThread.notify();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Date getDateMaj() {
        return  this.dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }
}
