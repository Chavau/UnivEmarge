package com.chavau.univ_angers.univemarge.view.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.view.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.view.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

/**
 * Cette activité a pour but de gérer les interractions avec les cartes étudiantes.<br>
 * Elle dispose des méthodes nécessaire à la lecture de puces NFC et se charge également
 * d'envoyer les informations lues à la base de données.
 */
public class BadgeageEtudiant extends AppCompatActivity {

    private RecyclerView _recyclerview;
    AdapterPersonneInscrite _api;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;

    private final int MY_PERMISSIONS_REQUEST_NFC = 1;
    /**
     * L'adaptateur gérant l'intéraction avec le téléphone pour le NFC.
     */
    private NfcAdapter nfcAdapter;
    /**
     * Contient le son à utiliser lorsque quelqu'un ayant badger est accepté par la base de données.
     */
    public MediaPlayer mp_son_approuver;
    /**
     * Contient le son à utiliser lorsque quelqu'un ayant badge n'est pas dans la base de données.
     */
    public MediaPlayer mp_son_refuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_seance);

        // Récuperation des données envoyées par l'activité précedente
        _intent = getIntent();
        _titreActivite = _intent.getStringExtra(AdapterEvenements.getNomAct());
        setTitle(_titreActivite);
        _recyclerview = findViewById(R.id.recyclerview_creation_seance);
        _etudiants = _intent.getParcelableArrayListExtra(AdapterEvenements.getListeEtud());

        // RFID
        mp_son_approuver = MediaPlayer.create(BadgeageEtudiant.this, R.raw.bonbadgeaccepter);
        mp_son_refuser = MediaPlayer.create(BadgeageEtudiant.this, R.raw.mauvaisbadgenonaccepter);

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

        // Si les autorisations du NFC ont été refusés
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.NFC)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.NFC}, MY_PERMISSIONS_REQUEST_NFC);
        }

        // TODO: Suppression du son par default
//        nfcAdapter.enableReaderMode(this,
//                tag -> System.out.println(tag),
//            NfcAdapter.FLAG_READER_NFC_A |
//            NfcAdapter.FLAG_READER_NFC_B |
//            NfcAdapter.FLAG_READER_NFC_F |
//            NfcAdapter.FLAG_READER_NFC_V |
//            NfcAdapter.FLAG_READER_NFC_BARCODE |
//            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
//            null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_pin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_code_pin:
                intent = new Intent(this, BadgeageEnseignant.class);
                intent.putExtra(AdapterEvenements.getNomAct(), _titreActivite);
                intent.putParcelableArrayListExtra(AdapterEvenements.getListeEtud(), _etudiants);
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                _etudiants = data.getParcelableArrayListExtra(AdapterEvenements.getListeEtud());
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    /**
     * Méthode permettant de mettre en place la reception d'une lecture NFC
     * la methode enableForegroundDispatch permet de dire que l'Activité doit être au premier plan
     * pour lire une carte NFC.
     */
    @Override
    protected void onResume() {
        super.onResume();
        _api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.NS);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(_api);

        _api.set_listener(position -> {
            //TODO : utilité de ces lignes ? met en vert lors d'un click lors du badgeage
            //_api.setPresence(position, Etudiant.STATUE_ETUDIANT.PRESENT);
            //_api.notifyDataSetChanged();
        });

        // RFID
        Intent intent = new Intent(this, BadgeageEtudiant.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{
                new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
        };
        String[][] techLists = new String[][]{
                new String[]{
                        NfcA.class.getName()
                },
        };

        try {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists);
        } catch (Exception e) {
            // TODO
            System.out.println(e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_NFC:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    System.out.println("Granted");
                } else {
                    // permission denied
                    System.out.println("denied");
                    Toast.makeText(BadgeageEtudiant.this, "[NFC] Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
        new BadgeageEtudiant.TraitementAsynchrone().execute(intent);
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
        for (byte aByte : bytes) {
            int b = aByte & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b).toUpperCase());
        }
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
            System.out.println("doInBackground");
            /*
             * Récupérer l'intent
             */
            Intent intent = intents[0];

            Tag tagId = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); //Récupération du Tag permettant d'avoir l'identifiant

            String idEtudiant = dumpTagData(tagId);
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
            System.out.println("MIFARE=" + s);
//            Toast.makeText(BadgeageEtudiant.this, "MIFARE:\n" + s, Toast.LENGTH_LONG).show();
//            if () mp_son_approuver.start(); else mp_son_refuser.start();

            //TODO: Demo
            Toast.makeText(BadgeageEtudiant.this, "Vincent Le Quec", Toast.LENGTH_LONG).show();
            _api.setPresence(1, Etudiant.STATUE_ETUDIANT.PRESENT);
            _api.notifyDataSetChanged();
            mp_son_approuver.start();
        }
    }

}