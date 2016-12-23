package com.example.shiwantha.testone;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shiwantha.testone.Authentication.TokenManager;
import com.example.shiwantha.testone.Entity.TrainerObj;
import com.example.shiwantha.testone.adaptor.TrainerCardAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TrainersNearbyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<TrainerObj> trainerObjArray = new ArrayList<TrainerObj>();
    Activity activity;
    Button mJoinTrainersClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_nearby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mJoinTrainersClub = (Button) findViewById(R.id.join_trainer_club);
        activity = TrainersNearbyActivity.this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //adding join trainers club button listener
        mJoinTrainersClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainersNearbyActivity.this, JoinTrainerClubActivity.class));
            }
        });

        //add getTrainer method and execute
        new GetTrainers().execute("hello");

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(TrainersNearbyActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(TrainersNearbyActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(TrainersNearbyActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(TrainersNearbyActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(TrainersNearbyActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {



        } else if (id == R.id.nav_settings) {
            //startActivity(new Intent(TrainersNearbyActivity.this, RegisterActivity.class));
            TokenManager.setToken(TrainersNearbyActivity.this,"");
            startActivity(new Intent(TrainersNearbyActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendAdapterData(ArrayList<TrainerObj> trinerObjArraya) {

        TrainerCardAdaptor trainerCardAdaptor = new TrainerCardAdaptor(this, trinerObjArraya);

        ListView trainerListView = (ListView) findViewById(R.id.trainerListView);
        trainerListView.setAdapter(trainerCardAdaptor);

        trainerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "hollo "+i, Toast.LENGTH_SHORT).show();
            }
        });

//        trainerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//
//            @Override
//            public void onItemClick(AdapterView<?> trainerCardAdaptor, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "hollo "+i, Toast.LENGTH_SHORT).show();
//                if (true) {
//                    Intent myIntent = new Intent(view.getContext(), TrainerProfileActivity.class);
//                    startActivityForResult(myIntent, 0);
//                }
//            }
//        });
    }

    private class GetTrainers extends AsyncTask<String, Void, String> {
        StringBuilder responseOutput;

        @Override
        protected String doInBackground(String... strings) {
            //54.244.41.83
            try {

               // URL url = new URL("http://192.168.8.103:9000/api/trainers");

                URL url = new URL("http://54.244.41.83:9000/api/trainers");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                connection.setRequestMethod("GET");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                int responseCode = connection.getResponseCode();


                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }

                br.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return responseOutput.toString();
        }

        @Override
        protected void onPostExecute(String trainerStringArray) {
            Log.e("test4","qq"+trainerStringArray);

            try {

                JSONArray trainerJsonArray = new JSONArray(trainerStringArray);

                for (int i = 0; i < trainerJsonArray.length(); i++) {

                    TrainerObj trainerObj = new TrainerObj();
                    JSONObject trainerJsonObj = trainerJsonArray.getJSONObject(i);


                    trainerObj.setName(trainerJsonObj.getString("name"));
                    trainerObj.setPhone(trainerJsonObj.getString("phone"));
                    trainerObj.setCertification(trainerJsonObj.getString("certification"));
                    trainerObj.setFacilityOrHouseCalls(trainerJsonObj.getString("facilityOrHouseCalls"));
                    trainerObj.setInsured(trainerJsonObj.getBoolean("insureStatus"));
                    trainerObj.setLocation(trainerJsonObj.getString("location"));
                    trainerObj.setPrice(trainerJsonObj.getInt("price"));
                    trainerObj.setServices(trainerJsonObj.getString("services"));
                    trainerObj.setRating(trainerJsonObj.getInt("rating"));
                    trainerObj.setGender(trainerJsonObj.getString("gender"));
                    trainerObj.setAvailability(trainerJsonObj.getBoolean("availability"));

                    trainerObjArray.add(trainerObj);


                }

//                sendAdapterData(trainerObjArray);

                TrainerCardAdaptor trainerCardAdaptor = new TrainerCardAdaptor(activity, trainerObjArray);

                ListView trainerListView = (ListView) findViewById(R.id.trainerListView);
                trainerListView.setAdapter(trainerCardAdaptor);

//                trainerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getApplicationContext(), "hollo "+i, Toast.LENGTH_SHORT).show();
//                    }
//                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
