package com.example.shiwantha.testone;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.example.shiwantha.testone.util.CommonData;
import com.example.shiwantha.testone.util.StatusCheck;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

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
import java.util.Calendar;


public class TrainersNearbyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    ArrayList<TrainerObj> trainerObjArray = new ArrayList<TrainerObj>();
    Activity activity;

    LocationManager mLocationManager;
    private Location loc;

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

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!(StatusCheck.isNetworkAvailable(TrainersNearbyActivity.this))) {

            new AlertDialog.Builder(TrainersNearbyActivity.this)
                    .setTitle("No Internet Connection")
                    .setMessage("For use this app, you should enable internet connection")
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(
                                    Settings.ACTION_WIFI_SETTINGS);
                            startActivity(myIntent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else if (!(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {

            new AlertDialog.Builder(TrainersNearbyActivity.this)
                    .setTitle("No GPS Service")
                    .setMessage("For use this app, you should enable GPS")
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {

            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 1 * 60 * 1000) {
                loc = location;
            } else {
                if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    loc = location;
                }

            }

            new GetTrainers().execute("hello");

        }
    }


    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            loc = location;
            if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                mLocationManager.removeUpdates(this);
            }
        }
    }

    // Required functions
    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String arg0) {
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
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
            TokenManager.setToken(TrainersNearbyActivity.this, "");
            startActivity(new Intent(TrainersNearbyActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class GetTrainers extends AsyncTask<String, Void, String> {

        StringBuilder responseOutput;
        SimpleArcDialog mDialog;
        int[] colors = {Color.parseColor("#ffef6968")};

        @Override
        protected void onPreExecute() {
            mDialog = new SimpleArcDialog(TrainersNearbyActivity.this);
            mDialog.setConfiguration(new ArcConfiguration(TrainersNearbyActivity.this));
            mDialog.setCancelable(false);
            ArcConfiguration configuration = new ArcConfiguration(TrainersNearbyActivity.this);
            configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
            configuration.setText("Loading.Please wait..");
            configuration.setColors(colors);
            mDialog.setConfiguration(configuration);
            mDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(CommonData.serverIp+"api/trainers");

                // URL url = new URL("http://54.244.41.83:9000/api/trainers");

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
                    trainerObj.setLatitude(trainerJsonObj.getDouble("latitude"));
                    trainerObj.setLongitude(trainerJsonObj.getDouble("longitude"));


                    Location userLocation = loc;
                    Location trainerLocation = new Location("trainer");
                    trainerLocation.setLatitude(trainerObj.getLatitude());
                    trainerLocation.setLongitude(trainerObj.getLongitude());

                    trainerObj.setDistance(userLocation.distanceTo(trainerLocation) / 1000);

                    trainerObjArray.add(trainerObj);

                }

                TrainerCardAdaptor trainerCardAdaptor = new TrainerCardAdaptor(activity, trainerObjArray);

                ListView trainerListView = (ListView) findViewById(R.id.trainerListView);
                trainerListView.setAdapter(trainerCardAdaptor);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }
    }


}
