package com.example.shiwantha.testone;

import android.content.Intent;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GymProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GymObj gymObj = new GymObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent gymProfIntent = getIntent();
        Bundle gymProfBundle = gymProfIntent.getExtras();

        String gymID = (String) gymProfBundle.get("gymID");

        new GymProfData().execute(gymID);


        Log.e("Test2", "gymActivity " + gymProfBundle.get("gymID"));

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(GymProfileActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(GymProfileActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(GymProfileActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(GymProfileActivity.this, JoinTrainerClubActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(GymProfileActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {
            startActivity(new Intent(GymProfileActivity.this, TrainerProfileActivity.class));


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(GymProfileActivity.this, RegisterActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setGymProfproperties() {

        TextView gymProfAddress = (TextView) findViewById(R.id.gymProfAddress);
        TextView gymProfPhone = (TextView) findViewById(R.id.gymProfPhone);
        TextView gymProfPrice = (TextView) findViewById(R.id.gymProfPrice);
        TextView gymProfHours = (TextView) findViewById(R.id.gymProfHours);
        TextView gymProfType = (TextView) findViewById(R.id.gymProfType);
        TextView gymProfWebsite = (TextView) findViewById(R.id.gymProfWebsite);

        gymProfAddress.setText(gymObj.getNo()+" "+gymObj.getStreet()+" "+gymObj.getCity());
        gymProfPhone.setText(gymObj.getPhone());
        gymProfPrice.setText( String.valueOf(gymObj.getPrice()));
        gymProfHours.setText(gymObj.getHours());
        gymProfType.setText(gymObj.getType());
        gymProfWebsite.setText(gymObj.getWebsite());


    }

    private class GymProfData extends AsyncTask<String, Void, String> {

        StringBuilder responseOutput;

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("Test2", "Log before :: ");

                URL url = new URL("http://192.168.8.101:9000/api/gyms/" + params[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                int responseCode = connection.getResponseCode();
                Log.e("Test2", "Log after :: " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }

                br.close();

                Log.e("Test2", "Log 11111 :: " + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }

            return responseOutput.toString();
        }

        @Override
        protected void onPostExecute(String gymStringObj) {

            Log.e("Test2", "OnPost Data getGymsOnPostExecute :: " + gymStringObj);

            try {

                JSONObject gymJSONObj = new JSONObject(gymStringObj);


                gymObj.setGymId(gymJSONObj.getString("_id"));
                gymObj.setName(gymJSONObj.getString("name"));
                gymObj.setLatitude(gymJSONObj.getDouble("latitude"));
                gymObj.setLongitude(gymJSONObj.getDouble("longitude"));
                gymObj.setType(gymJSONObj.getString("type"));
                gymObj.setPhone(gymJSONObj.getString("phone"));
                gymObj.setNo(gymJSONObj.getJSONObject("address").getString("no"));
                gymObj.setStreet(gymJSONObj.getJSONObject("address").getString("street"));
                gymObj.setCity(gymJSONObj.getJSONObject("address").getString("city"));
                gymObj.setPrice(gymJSONObj.getDouble("price"));
                gymObj.setHours(gymJSONObj.getString("hours"));
                gymObj.setWebsite(gymJSONObj.getString("webSite"));


            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("Test2", "OnPost Data getGymsOnPostExecute :: " + gymObj.getLongitude());

            setGymProfproperties();
        }


    }
}



