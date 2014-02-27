package com.stoicavlad.carnet.ui.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.ui.main.MainActivity;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public class SetupActivity extends FragmentActivity implements SetupFragment.OnFragmentInteractionListener {

    @Inject
    MateriiDatabase materiiDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getApplicationContext()).inject(this);
        SharedPreferences settings = getSharedPreferences("appPref", Context.MODE_PRIVATE);
        if(settings.contains("SETUP_DONE")){
            gotoMainActivity();
        }
        setContentView(R.layout.activity_setup);
        if (savedInstanceState == null) {
            SetupFragment setupFragment = new SetupFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, setupFragment)
                    .commit();
        }
        SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment(getString(R.string.materii_setup));
        simpleDialogFragment.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public void onNextSelected(ArrayList<String> materiiDeAdaugat) {
        for(String materie:materiiDeAdaugat){
            materiiDatabase.addMaterie(materie);
        }
        SharedPreferences settings = getSharedPreferences("appPref", Context.MODE_PRIVATE);
        settings.edit().putString("SETUP_DONE","true").commit();

        gotoMainActivity();

    }

    public void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

