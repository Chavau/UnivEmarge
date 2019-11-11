package com.chavau.univ_angers.univemarge.sync;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.io.IOException;
import java.util.EnumSet;

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

    /**
     * This is the thread that will do our work.  It sits in a loop running
     * the progress up until it has reached the top, then stops and waits.
     */
    final Thread mThread = new Thread() {
        @Override
        public void run() {

            // This thread runs almost forever.
            while (true) {
                EnumSet.allOf(ElementToSync.class)
                        .parallelStream()
                        .forEach(APICall::sendRequest);

                // we wait 2h to to another sync
                synchronized (this) {
                    try {
                        wait(WAIT_TIME_SEC);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    };

    private static void sendRequest(final ElementToSync element) {
        System.out.println("element = " + element.toString());

        // TODO : remplacer par l'appel qui permet de récupérer ces 2 valeurs
        final String login = "t.delestang";
        //final String dateMaj = "201910010000";
        final String dateMaj = null;


        // set up parameters & url
        String parameters = LOGIN_INPUT_NAME + login;
        if(dateMaj != null && !"".equals(dateMaj)) {
            parameters += "&" + DATE_INPUT_NAME + dateMaj;
        }
        final String url = API_URL + element.getUrlLink() + "?" + parameters;


        // set up the http request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();


        // make the call to the api
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    // send the response to merge to out data
                    if (response.body() != null) {
                        final String reponse = response.body().string();
                        System.out.println("Response for " + element.getUrlLink());
                        System.out.println(reponse);
                        element.merge(reponse);
                    }
                }
            }
        });
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
}
