package com.chavau.univ_angers.univemarge.sync;

import java.io.IOException;
import java.util.EnumSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncAPI {

    private static final String API_URL = "https://api-emaua.univ-angers.fr/api-emaua/";
    private static final String LOGIN_INPUT_NAME = "login=";
    private static final String DATE_INPUT_NAME = "datemaj=";

    private SyncAPI() {}

    public static void syncData() {
        EnumSet.allOf(ElementToSync.class)
                .forEach(SyncAPI::sendRequest);
    }

    private static final void sendRequest(final ElementToSync element) {

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
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
}
