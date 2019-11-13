package com.chavau.univ_angers.univemarge.view.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;

/**
 * Cette activité a pour but de gérer les interractions avec les cartes étudiantes.<br>
 * Elle dispose des méthodes nécessaire à la lecture de puces NFC et se charge également
 * d'envoyer les informations lues à la base de données.
 */
public class RFIDActivity extends AppCompatActivity {

    /**
     * L'adaptateur gérant l'intéraction avec le téléphone pour le NFC.
     */
    private NfcAdapter nfcAdapter;
    /**
     * Tag repérant cet Activity dans les messages d'erreur.
     */
    public String TAG = "RFIDActivity";
    /**
     * Int utilisé dans la gestion des messages informatifs concernant les utlisateurs,
     * permettant de pouvoir réafficher le message initial de demande de badgeage.
     */
    public int compteur = 0;

    /**
     * Contient le son à utiliser lorsque quelqu'un ayant badger est accepté par la base de données.
     */
    public MediaPlayer mp_son_approuver;

    /**
     * Contient le son à utiliser lorsque quelqu'un ayant badge n'est pas dans la base de données.
     */
    public MediaPlayer mp_son_refuser;

    /**
     * Méthode appelée au lancement de l'activité. Cette activité contient la majorité des initialisations
     * ayant pour but d'assurer un bon fonctionnement de l'application.
     *
     * @param savedInstanceState Bundle qui peut contenir des informations si l'activité
     *                           est réinitialiser
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("RFIDActivity:OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_rfid);

        mp_son_approuver = MediaPlayer.create(RFIDActivity.this, R.raw.bonbadgeaccepter);

        mp_son_refuser = MediaPlayer.create(RFIDActivity.this, R.raw.mauvaisbadgenonaccepter);

        /*
         * Initialize NFCAdapter
         */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            /*
             * Matériel NFC absent
             */
            Toast.makeText(this, "NFC absent sur le téléphone, fermeture de l'application ", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            /*
             * Si le NFC n'est pas activé alors on ouvre le menu pour
             * que l'utilisateur puisse activer le module NFC
             */
            Toast.makeText(this, "Il faut activer le NFC", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
        System.out.println("onCreate");
        new RFIDActivity.TraitementAsynchrone().execute(getIntent());
    }

    /**
     * Méthode permettant de mettre en place la reception d'une lecture NFC
     * la methode enableForegroundDispatch permet de dire que l'Activité doit être au premier plan
     * pour lire une carte NFC.
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RFIDActivity:OnResume");
        System.out.println("getIntent" + getIntent());

        Intent intent = new Intent(this, RFIDActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }


    /**
     * Quand une carte est lue et détectée cette méthode est lancé
     *
     * @param intent Contient les éléments de la carte NFC
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /*
         * Pour ne pas bloquer l'interface utilisateur, le traitement est lancé en tâche de fond.
         * Une tâche asynchrone est lancé avec un l'intent de la carte en argument
         */
        System.out.println("JE DEMARRE LA LECTURE NFC");
        new RFIDActivity.TraitementAsynchrone().execute(intent);
    }


    /**
     * Désactive l'option enableForegroundDispatch
     * Il est nécessaire de le faire lorsque l'application est mise en pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * Méthode servant à récupérer l'identifiant d'une carte étudiante
     *
     * @param tag Il s'agit ici de l'identifiant de lecture de la carte qui va être
     *            utilisé pour créer l'identifiant unique
     * @return L'identifiant unique de la carte
     */
    private String dumpTagData(Tag tag) {
        byte[] id = tag.getId();
        System.out.println("XXXXXXX dumpTagData:id=" + id);
        return toReversedHex(id);
    }

    /**
     * Méthode calculant l'identifiant d'une carte étudiante
     *
     * @param bytes id de lecture de la carte converti en octet
     * @return l'identifiant de la carte qui correspond au code hexadéciaml inversé.
     */
    private String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b).toUpperCase());
        }
        System.out.println("XXXXXXX toReversedHex: sb=" + sb.toString());
        return sb.toString();
    }

    /**
     * Lorsqu'une carte est lue elle se fait dans le thread d'interface, hors il déconseillé de faire
     * un quelconque traitement dans ce thread. Cette tâche asynchrone à pour but de faire le traitement
     * de lecture dans un nouveau thread.
     */
    public class TraitementAsynchrone extends AsyncTask<Intent, Void, String> {

        /**
         * Traitement qui se fait dans un thread de tâche de fond, afin que le thread de
         * l'interface utilisateur soit toujours utilisable
         *
         * @param intents Contient l'intent de la carte
         * @return l'id de la carte étudiante
         */
        @Override
        protected String doInBackground(Intent... intents) {

            /*
             * Récupérer l'intent
             */
            Intent intent = intents[0];

            Tag tagId = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); //Récupération du Tag permettant d'avoir l'identifiant

            String idEtudiant = dumpTagData(tagId);
            //Log.e("Identifiant carte Etu",idEtudiant);

            /*
             * Permet d'aller dans la méthode onPostExecute
             */
            return idEtudiant;
        }


        /**
         * Méthode appliqué quand le numéro de la carte ayant badgé a finalement été obtenue,
         * Créé la requête pour envoyer le numéro à la base de données puis affiche les informations
         * relatifs à la requête, joue un son selon le réultat de la requête.
         *
         * @param s : contient le numéro de la carte de l'étudiant ayant badgé.
         */
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("XXXXXXX onPostExecute: s=" + s);
        }
    }
}
