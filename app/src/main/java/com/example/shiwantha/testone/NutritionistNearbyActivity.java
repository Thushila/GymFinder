package com.example.shiwantha.testone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shiwantha.testone.Entity.NutritionistObj;
import com.example.shiwantha.testone.adaptor.NutritionistCardAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class NutritionistNearbyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // private String nutritionists;
    ArrayList<NutritionistObj> nutritionistObjArray = new ArrayList<NutritionistObj>();
    private  double latitude;
    private  double longitude;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_nearby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("location", "" + location);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    loc=location;
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

        new GetNutritionists().execute("hello");

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(NutritionistNearbyActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(NutritionistNearbyActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(NutritionistNearbyActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(NutritionistNearbyActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(NutritionistNearbyActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(NutritionistNearbyActivity.this, RegisterActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendAdapterData(ArrayList<NutritionistObj> nutritionistObjArray) {

        NutritionistCardAdaptor nutritionistCardAdaptor = new NutritionistCardAdaptor(this, nutritionistObjArray);

        ListView nutritionistListView = (ListView) findViewById(R.id.nutritionistListView);

        nutritionistListView.setAdapter(nutritionistCardAdaptor);

//        nutritionistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//                String Slecteditem= itemname[+position];
//                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

//
//            }
//        });
    }


    private class GetNutritionists extends AsyncTask<String, Void, String> {

        StringBuilder responseOutput;

        @Override
        protected String doInBackground(String... params) {
            try {


                URL url = new URL("http://10.0.3.2:9000/api/nutritionist");





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
        protected void onPostExecute(String nutrtionistsStringArray) {

            try {

                JSONArray nutrtionistsJsonArray = new JSONArray(nutrtionistsStringArray);


                for (int i = 0; i < nutrtionistsJsonArray.length(); i++) {

                    NutritionistObj nutritionistObj = new NutritionistObj();

                    JSONObject nutrtionistJSONObj = nutrtionistsJsonArray.getJSONObject(i);

                    nutritionistObj.setName(nutrtionistJSONObj.getString("name"));
                    nutritionistObj.setPhone(nutrtionistJSONObj.getString("phone"));
                    nutritionistObj.setNo(nutrtionistJSONObj.getJSONObject("address").getString("no"));
                    nutritionistObj.setStreet(nutrtionistJSONObj.getJSONObject("address").getString("street"));
                    nutritionistObj.setCity(nutrtionistJSONObj.getJSONObject("address").getString("city"));
                    nutritionistObj.setAvailability(nutrtionistJSONObj.getBoolean("availability"));
                    nutritionistObj.setRating(nutrtionistJSONObj.getDouble("rating"));
                    nutritionistObj.setLatitude(nutrtionistJSONObj.getDouble("latitude"));
                    nutritionistObj.setLongitude(nutrtionistJSONObj.getDouble("longitude"));


                    Location userLocation=loc;
                    Location nutritionistLocation= new Location("nutritionist");
                    nutritionistLocation.setLatitude(nutritionistObj.getLatitude());
                    nutritionistLocation.setLongitude(nutritionistObj.getLongitude());

                    nutritionistObj.setDistance(userLocation.distanceTo(nutritionistLocation)/1000);

                    nutritionistObjArray.add(nutritionistObj);


                }

//                sendAdapterData(nutritionistObjArray);
                NutritionistCardAdaptor nutritionistCardAdaptor = new NutritionistCardAdaptor(NutritionistNearbyActivity.this, nutritionistObjArray);

                ListView nutritionistListView = (ListView) findViewById(R.id.nutritionistListView);

                nutritionistListView.setAdapter(nutritionistCardAdaptor);

//                nutritionistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        // TODO Auto-generated method stub
//                        // String Slecteditem= itemname[+position];
//                        Toast.makeText(getApplicationContext(), "hollo " + position, Toast.LENGTH_SHORT).show();
//
//
//                    }
//                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //  super.onPostExecute(nutrtionistsStringArray);
        }


    }

}
