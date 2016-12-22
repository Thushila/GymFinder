package com.example.shiwantha.testone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shiwantha.testone.Entity.GymObj;
import com.example.shiwantha.testone.Entity.TrainerObj;

public class TrainerProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent trainerProfIntent = getIntent();
        TrainerObj trainerObj = trainerProfIntent.getExtras().getParcelable("trainerObj");

        TextView trainerProfService = (TextView) findViewById(R.id.trainerProfService);
        TextView trainerProfCertification = (TextView) findViewById(R.id.trainerProfCertification);
        TextView trainerProfInsured = (TextView) findViewById(R.id.trainerProfInsured);
        TextView trainerProfHouseCallFac = (TextView) findViewById(R.id.trainerProfHouseCallFac);
        TextView trainerProfLocation = (TextView) findViewById(R.id.trainerProfLocation);

        trainerProfService.setText(trainerObj.getServices());
        trainerProfCertification.setText(trainerObj.getCertification());
        trainerProfInsured.setText(String.valueOf(trainerObj.getInsured()));
        trainerProfHouseCallFac.setText(trainerObj.getFacilityOrHouseCalls());
        trainerProfLocation.setText(trainerObj.getLocation());



        Log.e("Test3","j "+trainerObj.getName());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(TrainerProfileActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(TrainerProfileActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(TrainerProfileActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(TrainerProfileActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(TrainerProfileActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(TrainerProfileActivity.this, RegisterActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
