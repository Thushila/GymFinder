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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shiwantha.testone.Authentication.TokenManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinTrainerClubActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText trainerName;
    EditText trainerEmail;
    EditText trainerMobile;
    EditText trainerCity;
    CheckBox checkPersonal;
    CheckBox checkGroup;
    CheckBox checkYoga;
    Button createAccount;
    private final String url = "http://54.244.41.83:9000/api/trainers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_trainer_club);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        trainerName = (EditText) findViewById(R.id.trainer_name);
        trainerEmail = (EditText) findViewById(R.id.trainer_email);
        trainerMobile = (EditText) findViewById(R.id.trainer_mobile);
        trainerCity = (EditText) findViewById(R.id.trainer_city);
        checkPersonal = (CheckBox) findViewById(R.id.check_personal);
        checkGroup = (CheckBox) findViewById(R.id.check_group);
        checkYoga = (CheckBox) findViewById(R.id.check_yoga);
        createAccount = (Button) findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = trainerName.getText().toString();
                String email = trainerEmail.getText().toString();
                String mobile = trainerMobile.getText().toString();
                String city = trainerCity.getText().toString();
                boolean personal = checkPersonal.isChecked();
                boolean group = checkGroup.isChecked();
                boolean yoga = checkYoga.isChecked();

                JoinTrainerClub joinTrainerClub = new JoinTrainerClub(name,email, mobile, city, personal, group, yoga);
                joinTrainerClub.execute();


            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(JoinTrainerClubActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(JoinTrainerClubActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(JoinTrainerClubActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(JoinTrainerClubActivity.this, MessagesActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(JoinTrainerClubActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {


        } else if (id == R.id.nav_settings) {
            //startActivity(new Intent(JoinTrainerClubActivity.this, RegisterActivity.class));
            TokenManager.setToken(JoinTrainerClubActivity.this,"");
            startActivity(new Intent(JoinTrainerClubActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class JoinTrainerClub extends AsyncTask<Void, Void, Boolean> {
        private String name;
        private String email;
        private String mobile;
        private String city;
        private boolean personal;
        private boolean group;
        private boolean yoga;
        private final String mUrl;
        private JSONObject jsonParam;
        private String response;
        String serviceListString;

        JoinTrainerClub(String name, String email, String mobile, String city, boolean personal, boolean group, boolean yoga){
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.city = city;
            this.mUrl = url;
            jsonParam = new JSONObject();
            serviceListString  = setServices(personal,group,yoga); //since Service attribute in the model is a single string
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                jsonParam.put("name",name);
                jsonParam.put("email", email);
                jsonParam.put("phone",mobile);
                jsonParam.put("location", city);
                jsonParam.put("services", serviceListString);
                //**hard coded attributes to trainers - added to avoid errors in android
                //**app due to lack of fields when adding the trainers from 'Join trainers club'
                //**removing below makes errors in Trainer list activity at run time
                jsonParam.put("price", "0");
                jsonParam.put("certification","_");
                jsonParam.put("insureStatus", "false");
                jsonParam.put("facilityOrHouseCalls", "_");
                jsonParam.put("gender","_");
                jsonParam.put("rating", "0");
                jsonParam.put("availability", "false");
                //**end of description

                Log.e("reached", "one");

            } catch (Exception e) {
                Log.e("JSON Error", "error while converting to json: " + e);
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            //super.onPostExecute(success);
            Log.e("reached", "two");
            if (success) {
                finish();
                Intent myIntent = new Intent(JoinTrainerClubActivity.this,MainActivity.class);
                JoinTrainerClubActivity.this.startActivity(myIntent);
            } else {
                trainerName.requestFocus();
            }

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                Log.e("reached", "three");
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                OutputStreamWriter outputStream = new   OutputStreamWriter(connection.getOutputStream());
                outputStream.write(jsonParam.toString());
                Log.e("JSON",jsonParam.toString());
                outputStream.flush();
                outputStream.close();

                int status = connection.getResponseCode();
                Log.e("StatusCode:", "" + status);
                connection.disconnect();
                return true;
                /*if(status==200){ // uncommenting makes a runTime exception - doesn't need anyway
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }
                    response = stringBuilder.toString();
                    reader.close();
                    connection.disconnect();
                    Log.e("response : ", response);
                    return true;
                }else{
                    Toast.makeText(JoinTrainerClubActivity.this, "Something went wrong", Toast.LENGTH_LONG);
                    return false;
                }*/
            }catch(Exception e){
                Log.e("Error","Error while calling POST on Join Trainers Club" + e);
                return false;
            }
        }
    }

    private static String setServices(boolean personal, boolean group, boolean yoga) {

        String services = "";
        StringBuilder stringBuilder = new StringBuilder(services);
        if(personal){
            stringBuilder.append(" personal");
        }
        else if(group) {
            stringBuilder.append(" group");
        }
        else if(yoga) {
            stringBuilder.append(" yoga");
        }
        return services.toString();
    }
}
