package com.chavau.univ_angers.univemarge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.sync.APICall;
import com.chavau.univ_angers.univemarge.view.activities.Authentification;
import com.chavau.univ_angers.univemarge.view.activities.CodePinDialogue;
import com.chavau.univ_angers.univemarge.view.activities.ListeEvenementsCours;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        APICall apiCall = new APICall();
        apiCall.setContext(this);
        apiCall.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE),0);

        if(preferences.getString(getResources().getString(R.string.PREF_LOGIN),"").equals("")) {
            Intent intent = new Intent(MainActivity.this, Authentification.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, ListeEvenementsCours.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_liste_evenement,menu);
        return(super.onCreateOptionsMenu(menu));

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.calendar:
                Toast msg = Toast.makeText(MainActivity.this,"Calendrier" ,Toast.LENGTH_SHORT);
                msg.show();
                return true;
            case R.id.synchron:
                Toast msg2 = Toast.makeText(MainActivity.this,"Synchronization" ,Toast.LENGTH_SHORT);
                msg2.show();
                return true;
            case R.id.setting:
                Toast msg3 = Toast.makeText(MainActivity.this,"Paramètre" ,Toast.LENGTH_SHORT);
                msg3.show();
                return true;
            case R.id.deconnect:
                Toast msg4 = Toast.makeText(MainActivity.this,"Déconnexion" ,Toast.LENGTH_SHORT);
                msg4.show();
                return true;



        }
        return super.onOptionsItemSelected(item);

    }


    public void Codepin(View view) {
        new CodePinDialogue().show(getSupportFragmentManager(),"PinClasse");
    }

}
