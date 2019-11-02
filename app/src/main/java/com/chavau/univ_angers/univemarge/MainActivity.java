package com.chavau.univ_angers.univemarge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.view.activities.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preference:
                launch_preference();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void launch_preference() {
        Intent start_settings_activity = new Intent(this, SettingsActivity.class);
        startActivity(start_settings_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
