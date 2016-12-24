package com.example.shiwantha.testone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiwantha.testone.Entity.GymObj;
import com.example.shiwantha.testone.Entity.NutritionistObj;
import com.example.shiwantha.testone.Entity.TrainerObj;
import com.example.shiwantha.testone.R;

public class NutritionistProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_profile);
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
        final NutritionistObj nutritionistObj = trainerProfIntent.getExtras().getParcelable("nutritionistObj");

        TextView nutritionistProfName = (TextView) findViewById(R.id.nutritionistProfName);
        TextView nutritionistProfPhone = (TextView) findViewById(R.id.nutritionistProfPhone);
        TextView nutritionistProfAddress = (TextView) findViewById(R.id.nutritionistProfAddress);
        Log.e("nutri","nutri::"+nutritionistObj.getName());

//        TextView trainerProfHouseCallFac = (TextView) findViewById(R.id.trainerProfHouseCallFac);
//        TextView trainerProfLocation = (TextView) findViewById(R.id.trainerProfLocation);

        nutritionistProfName.setText(nutritionistObj.getName());
        nutritionistProfPhone.setText(nutritionistObj.getPhone());
        nutritionistProfAddress.setText(nutritionistObj.getNo()+" "+nutritionistObj.getStreet()+" "+nutritionistObj.getCity());
//        trainerProfHouseCallFac.setText(trainerObj.getFacilityOrHouseCalls());
//        trainerProfLocation.setText(trainerObj.getLocation());

      Button nutritionistProfContactBtn =(Button) findViewById(R.id.nutritionistProfContactBtn);
        nutritionistProfContactBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(NutritionistProfileActivity.this, MessagesActivity.class);
                intent.putExtra("nutritionistObj", nutritionistObj);
                startActivity(intent);
            }
        });

//        Button nutritionistProfScheduleBtn =(Button) findViewById(R.id.nutritionistProfScheduleBtn);
//        nutritionistProfContactBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Intent intent = new Intent(NutritionistProfileActivity.this, MessagesActivity.class);
//                intent.putExtra("nutritionistObj", nutritionistObj);
//                startActivity(intent);
//            }
//        });



    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(NutritionistProfileActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(NutritionistProfileActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(NutritionistProfileActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(NutritionistProfileActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(NutritionistProfileActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(NutritionistProfileActivity.this, RegisterActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
