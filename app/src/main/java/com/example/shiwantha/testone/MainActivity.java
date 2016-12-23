package com.example.shiwantha.testone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiwantha.testone.Authentication.TokenManager;
import com.example.shiwantha.testone.Entity.GymObj;
import com.example.shiwantha.testone.Entity.NutritionistObj;
import com.example.shiwantha.testone.adaptor.NutritionistCardAdaptor;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {

    private GoogleMap mMap;
    private Map<Marker, GymObj> allMarkersMap = new HashMap<Marker, GymObj>();


    ArrayList<GymObj> gymObjArray = new ArrayList<GymObj>();

    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //nipun checking app-wide login
        if(TokenManager.getToken(MainActivity.this).length() == 0){
            Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
            MainActivity.this.startActivity(myIntent);
        }else{
            //if token exists, continue mainactivity
        }
        //*************
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);




    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(MainActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(MainActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(MainActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(MainActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {
            startActivity(new Intent(MainActivity.this, TrainerProfileActivity.class));

        } else if (id == R.id.nav_settings) {
            //startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            TokenManager.setToken(MainActivity.this,"");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        new GetGyms().execute("hello");

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.8, 80.77), 11.0f));

    }

    private void addMapMarkers() {


        for (GymObj gymObj : gymObjArray) {

            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(gymObj.getLatitude(), gymObj.getLongitude())).title("Capital").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            allMarkersMap.put(marker, gymObj);

        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker args) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                // Getting view from the layout file info_window_layout
                View gym_detail_card = getLayoutInflater().inflate(R.layout.gym_detail_card, null);

                final GymObj selectedGymObj = allMarkersMap.get(marker);

                // Getting the position from the marker

                //---     clickMarkerLatLng = args.getPosition();

                TextView gymName = (TextView) gym_detail_card.findViewById(R.id.gymName);
                TextView gymAddress = (TextView) gym_detail_card.findViewById(R.id.gymAddress);
                TextView gymPhone = (TextView) gym_detail_card.findViewById(R.id.gymPhone);
                TextView gymType = (TextView) gym_detail_card.findViewById(R.id.gymType);

                gymName.setText(selectedGymObj.getName());
                gymAddress.setText(selectedGymObj.getNo() + " " + selectedGymObj.getStreet() + " " + selectedGymObj.getCity());
                gymPhone.setText(selectedGymObj.getPhone());
                gymType.setText(selectedGymObj.getType());

                Button gymButton = (Button) gym_detail_card.findViewById(R.id.gymButton);

                gymButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.e("test4", "" + marker.getTitle());
                        Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, GymProfileActivity.class);
                        intent.putExtra("gymID", selectedGymObj.getGymId());
                        startActivity(intent);
                    }
                });


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        GymObj selectedGymObj = allMarkersMap.get(marker);

                        //Toast.makeText(MainActivity.this, "balla", Toast.LENGTH_SHORT).show();// display toast
                        Intent intent = new Intent(MainActivity.this, GymProfileActivity.class);
                        intent.putExtra("gymID", selectedGymObj.getGymId());
                        startActivity(intent);
                        return true;
                    }
                });

                // Defines the contents of the InfoWindow

                   return gym_detail_card;
            }
        });

    }

    @Override//get the position of the seekbar
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Toast.makeText(getApplicationContext(),"seekbar progress: "+i, Toast.LENGTH_SHORT).show();


    }

    @Override //seekbar started moving
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override //seekbar end moving
    public void onStopTrackingTouch(SeekBar seekBar) {


    }


    private class GetGyms extends AsyncTask<String, Void, String> {

        StringBuilder responseOutput;

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://54.244.41.83:9000/api/gyms"); //http://54.244.41.83:9000/api/gyms

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
        protected void onPostExecute(String gymStringArray) {

            try {


                JSONArray gymJsonArray = new JSONArray(gymStringArray);

                for (int i = 0; i < gymJsonArray.length(); i++) {


                    GymObj gymObj = new GymObj();

                    JSONObject gymJSONObj = gymJsonArray.getJSONObject(i);

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
                    gymObj.setWebsite(gymJSONObj.getString("webSite"));
                    gymObj.setWeekDayHours(gymJSONObj.getString("weekDayHours"));
                    gymObj.setWeekDayHours(gymJSONObj.getString("saturdayHours"));
                    gymObj.setSundayHours(gymJSONObj.getString("sundayHours"));

                    gymObjArray.add(gymObj);

                }
               
            } catch (JSONException e) {
                e.printStackTrace();
            }

            addMapMarkers();

        }
    }


}

