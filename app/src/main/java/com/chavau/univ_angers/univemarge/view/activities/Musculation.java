package com.chavau.univ_angers.univemarge.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterMusculation;
import com.chavau.univ_angers.univemarge.adapters.AdapterViewPager;
import com.chavau.univ_angers.univemarge.intermediaire.MusculationData;
import com.chavau.univ_angers.univemarge.intermediaire.Personnel;

import java.util.ArrayList;
import java.util.Random;

public class Musculation extends AppCompatActivity {

    private ViewPager viewpager;
    private AdapterViewPager adapterviewpager;
    private AdapterMusculation adaptermusculation;
    private RecyclerView recyclerview;
    private MusculationData mdata;
    private ArrayList<Personnel> presences = new ArrayList<>();
    private TabLayout tabLayout;

    private static String PERSONNELS_PRESENTS = "presences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musculation);

        setTitle("SUAPS : Musculation");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.viewpager);
        recyclerview = findViewById(R.id.recyclerview_musculation);

        // Recuperation de pesences si y'a une sauvegarde

        if (savedInstanceState != null) {
            presences = savedInstanceState.getParcelableArrayList(PERSONNELS_PRESENTS);
        }

        // Affectation du nombre de presents dans la salle
        presences = creerPers();
        mdata = creerMuscuData(presences.size());

        adaptermusculation = new AdapterMusculation(this, presences, mdata);

        ItemTouchHelper.SimpleCallback ihscb = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                int position = (int)viewHolder.itemView.getTag();
                adaptermusculation.enlever(position);
            }
        };

        new ItemTouchHelper(ihscb).attachToRecyclerView(recyclerview);
    }

    public MusculationData creerMuscuData(int presences) {
        MusculationData m_data = new MusculationData(33);
        m_data.setTempsMinimum(1,30);
        m_data.setOccupation(presences,m_data.getCapacite());
        return m_data;
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterviewpager = new AdapterViewPager(mdata,this);
        viewpager.setAdapter(adapterviewpager);

        tabLayout.setupWithViewPager(viewpager, true);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adaptermusculation);

        adaptermusculation.set_listener(new AdapterMusculation.Listener() {
            @Override
            public void onClick(int position) {
                // Suppression d'un personnel et mise à jour du recyclerview
                adaptermusculation.enlever(position);
                // Mise à jour du viewpager
                mdata.setOccupation(adaptermusculation.getItemCount(),mdata.getCapacite());
                View v = viewpager.findViewWithTag("OcuppationValue");
                TextView tv = v.findViewById(R.id.id_details_value);
                tv.setText(mdata.getOccupation());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PERSONNELS_PRESENTS,presences);
    }

    public ArrayList<Personnel> creerPers() {
        ArrayList<Personnel> personnels = new ArrayList<>();
        Random rand = new Random();
        personnels.add(new Personnel("ALLON","LEVY"));
        personnels.add(new Personnel("BACARD","HUGO"));
        personnels.add(new Personnel("BAKER","MATTHEW"));
        personnels.add(new Personnel("BALWE","CHETAN"));
        personnels.add(new Personnel("BELAIR","LUC"));
        personnels.add(new Personnel("CEBALLOS","CESAR"));
        personnels.add(new Personnel("FAVRE","CHARLES"));
        personnels.add(new Personnel("BYSZEWSKI","DYLAN"));
        personnels.add(new Personnel("CHEN","CHRISTIAN"));
        personnels.add(new Personnel("FEHM","ARNO"));
        personnels.add(new Personnel("GARCIA","LUIS"));
        personnels.add(new Personnel("FARGUES","LAURENT"));
        personnels.add(new Personnel("HERBLOT","MATHILDE"));
        personnels.add(new Personnel("LI","WEN-WEI"));

        for(Personnel p : personnels) {
            int heure = rand.nextInt(3);
            int minute = rand.nextInt(60);
            p.setHeurePassee(heure,minute);
        }
        return personnels;
    }
}
