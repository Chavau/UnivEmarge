package com.chavau.univ_angers.univemarge.view.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.dao.AutreDAO;
import com.chavau.univ_angers.univemarge.database.dao.EtudiantDAO;
import com.chavau.univ_angers.univemarge.database.dao.PersonnelDAO;
import com.chavau.univ_angers.univemarge.database.entities.Autre;
import com.chavau.univ_angers.univemarge.database.entities.Etudiant;
import com.chavau.univ_angers.univemarge.database.entities.Personnel;
import com.chavau.univ_angers.univemarge.utils.Utils;
import com.chavau.univ_angers.univemarge.view.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.view.adapters.AdapterPersonneInscrite;

import java.util.ArrayList;

/**
 * Cette activité a pour but de gérer les interractions avec les cartes étudiantes.<br>
 * Elle dispose des méthodes nécessaire à la lecture de puces NFC et se charge également
 * d'envoyer les informations lues à la base de données.
 */
public class BadgeageEtudiant extends AppCompatActivity {

    AdapterPersonneInscrite madapterPersonneInscrite;
    private RecyclerView _recyclerview;
    private TextView tv_nombre_presents;
    private TextView tv_nombre_hors_creneau;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;
    private ArrayList<Personnel> _personnels;
    private ArrayList<Autre> _autres;
    SharedPreferences preferences;

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

    public class Listener {
        public void majPresence() {majAffichagePresent();}
    }

    public void alertDialogCodePin(final View view, boolean isBackPress) {
        String codePin = preferences.getString(getResources().getString(R.string.Code_Pin), "");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Code Pin");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.create();
        AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                final EditText _text_code_pin = view.findViewById(R.id.code_pin);
                String code_pin_saisi = _text_code_pin.getText().toString();
                String msg = "Erreur";
                if (code_pin_saisi.matches(codePin)) {
                    if (isBackPress)
                        pinSuccessBack();
                    else
                        switchmodeEnseignant(true);
                    dialog.dismiss();
                } else {
                    TextView tv = view.findViewById(R.id.errorCodePin);
                    msg = " Le code pin est incorrect, veuillez réessayer !";
                    tv.setText(msg);
                    tv.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void pinSuccessBack(){
        switchmodeEnseignant(true); // pour éviter une boucle dans onBackPressed
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_seance);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // lien avec affichage
        tv_nombre_presents = (TextView) findViewById(R.id.tv_nb_presents);
        tv_nombre_hors_creneau = (TextView) findViewById(R.id.tv_nb_hors_creneau);
        _recyclerview = findViewById(R.id.recyclerview_modification_seance);

        // Création adapter
        madapterPersonneInscrite = new AdapterPersonneInscrite(this);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(madapterPersonneInscrite);

        // Récuperation des données envoyées par l'activité précedente
        _intent = getIntent();
        _titreActivite = _intent.getStringExtra(AdapterEvenements.getNomAct());
        setTitle(_titreActivite);

        int id_evenement = _intent.getIntExtra(AdapterEvenements.getIdEvent(), 0);
        madapterPersonneInscrite.setIdEvenement(id_evenement);

        // INITIALISATION ETUDIANTS
        EtudiantDAO edao = new EtudiantDAO(new DatabaseHelper(this));
        _etudiants = edao.listeEtudiantInscrit(id_evenement);

        for(Etudiant e : _etudiants){
            madapterPersonneInscrite.addPersonne(e);
        }

        // INITIALISATION PERSONNELS
        PersonnelDAO pdao = new PersonnelDAO(new DatabaseHelper(this));
        _personnels = pdao.listePersonnelInscrit(id_evenement);

        for(Personnel p : _personnels){
            madapterPersonneInscrite.addPersonne(p);
        }

        // INITIALISATION AUTRES
        AutreDAO adao = new AutreDAO(new DatabaseHelper(this));
        _autres = adao.listeAutresInscrit(id_evenement);

        for(Autre a : _autres){
            madapterPersonneInscrite.addPersonne(a);
        }

        // INITIALISATION LISTENER POUR AFFICHAGE
        Listener listener = new Listener();
        madapterPersonneInscrite.set_listener(listener);

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

        // Si les autorisations du NFC ont été refusées
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

        majAffichagePresent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_pin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_mode_enseignant:
                Boolean etatSwitch = preferences.getBoolean(getResources().getString(R.string.Is_pin_activated), false);
                if (etatSwitch) {
                    View v = LayoutInflater.from(this).inflate(R.layout.dialog_pin, null);
                    alertDialogCodePin(v,false);
                } else {
                    switchmodeEnseignant(true);
                }
                break;

            case R.id.menu_mode_etudiant:
                switchmodeEnseignant(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchmodeEnseignant(boolean isModeEnseignant) {

        ActionMenuItemView menu_lock = (ActionMenuItemView) findViewById(R.id.menu_mode_enseignant);
        ActionMenuItemView menu_unlock = (ActionMenuItemView) findViewById(R.id.menu_mode_etudiant);

        if (isModeEnseignant) {
            madapterPersonneInscrite.set_isModeEnseignant(true);
            menu_lock.setVisibility(View.GONE);
            if (menu_unlock != null) // TODO : voir pourquoi il est null au début (affichage actuel non correct)
                menu_unlock.setVisibility(View.VISIBLE);

        } else {
            madapterPersonneInscrite.set_isModeEnseignant(false);
            menu_lock.setVisibility(View.VISIBLE);
            menu_unlock.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        // si mode enseignant acitvé alors juste sortir
        // si mode etudiant activé alors demander le pin si pin activé
        if (!madapterPersonneInscrite.get_isModeEnseignant()) { // si il n'y a pas le mode enseignant
            Boolean isPinActif = preferences.getBoolean(getResources().getString(R.string.Is_pin_activated), false);
            if (isPinActif) { // si le code pin est activé
                View v = LayoutInflater.from(this).inflate(R.layout.dialog_pin, null);
                alertDialogCodePin(v,true); // TODO : dev : attendre la validation du code pour back
            }else
                super.onBackPressed();
        }else
            super.onBackPressed();
    }

    /**
     * Met à jour le nombre de présent et hors créneau
     */
    public void majAffichagePresent(){
        tv_nombre_presents.setText(madapterPersonneInscrite.getNbPresent()+"/"+madapterPersonneInscrite.getItemCount());
        tv_nombre_hors_creneau.setText(""+madapterPersonneInscrite.getNbHorsCreneau());
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //_etudiants = data.getParcelableArrayListExtra(AdapterEvenements.getListeEtud()); // TODO
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }*/

    /**
     * Méthode permettant de mettre en place la reception d'une lecture NFC
     * la methode enableForegroundDispatch permet de dire que l'Activité doit être au premier plan
     * pour lire une carte NFC.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // TODO : dev : pourquoi onResume ? (et pas onCreate)


        //TODO : utilité de ces lignes ? met en vert lors d'un click lors du badgeage
       /* madapterPersonneInscrite.set_listener(position -> {

            //madapterPersonneInscrite.setPresence(position, Etudiant.STATUE_ETUDIANT.PRESENT);
            //madapterPersonneInscrite.notifyDataSetChanged();
        });
*/
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
         * @param no_mifare : contient le numéro de la carte de l'étudiant ayant badgé.
         */
        protected void onPostExecute(String no_mifare) {
            super.onPostExecute(no_mifare);

            System.out.println("MIFARE=" + no_mifare);
            // TODO : dev :if (hors_creneau) à ajouter
            no_mifare = Utils.testCarte(no_mifare);
            boolean isCarteOK = false; // pour le son

            for (AdapterPersonneInscrite.PersonneInscrite personne : madapterPersonneInscrite.getlistePersonneInscrites()){
                String mifare_personne = personne.getMifare();
                if (mifare_personne == null)
                    mifare_personne = "";

                if(mifare_personne.equals(no_mifare)){
                    isCarteOK = true;
                    // TODO : dev : afficher la photo et nom
                    personne.setPresence(madapterPersonneInscrite.PRESENT);
                    majAffichagePresent();
                }
                else {
                    // TODO : dev : afficher message mauvaise carte
                }
            }
            if (isCarteOK)
                mp_son_approuver.start();
            else
                mp_son_refuser.start();

            madapterPersonneInscrite.notifyDataSetChanged();
        }
    }

}