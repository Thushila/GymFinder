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
import android.widget.EditText;
import android.widget.Toast;

import com.example.shiwantha.testone.Authentication.TokenManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText email;
    private EditText name;
    private EditText mobile;
    private EditText password;
    private EditText repeatPassword;
    private final String url = "http://54.244.41.83:9000/api/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        email = (EditText) findViewById(R.id.register_email);
        name = (EditText) findViewById(R.id.register_name);
        mobile = (EditText) findViewById(R.id.register_mobile);
        password = (EditText) findViewById(R.id.register_password);
        repeatPassword = (EditText) findViewById(R.id.register_repeat_passowrd);



        final Button registerInButton = (Button) findViewById(R.id.create_account);
        registerInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String nameValue = name.getText().toString();
                final String emailValue = email.getText().toString();
                final String mobileValue = mobile.getText().toString();
                final String passwordValue = password.getText().toString();
                String repeatPasswordValue = repeatPassword.getText().toString();

                UserSignUpTask userSignUpTask = new UserSignUpTask(nameValue, emailValue, mobileValue, passwordValue);
                userSignUpTask.execute();
                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {
        private String mName;
        private String mEmail;
        private String mMobile;
        private String mPassword;
        private final String mUrl;
        private JSONObject jsonParam;
        private String response;

        UserSignUpTask(String name, String email, String mobile, String password){
            this.mName = name;
            this.mEmail = email;
            this.mMobile = mobile;
            this.mPassword = password;
            this.mUrl = url;
            jsonParam = new JSONObject();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                jsonParam.put("name",mName);
                jsonParam.put("email", mEmail);
                jsonParam.put("mobile",mMobile);
                jsonParam.put("password", mPassword);
            } catch (Exception e) {
                Log.e("JSON Error", "error while converting to json: " + e);
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            //super.onPostExecute(success);

            if (success) {
                finish();
                Intent myIntent = new Intent(RegisterActivity.this,MainActivity.class);
                String token = response;
                //storing shared preference
                TokenManager.setToken(RegisterActivity.this, token);//saved as the key value pair
                RegisterActivity.this.startActivity(myIntent);
            } else {
                email.requestFocus();
            }

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
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

                if(status==200){
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
                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG);
                    return false;
                }
            }catch(Exception e){
                Log.e("Error","Error while calling POST on sign up" + e);
                return false;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gym) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));

        } else if (id == R.id.nav_trainer) {
            startActivity(new Intent(RegisterActivity.this, TrainersNearbyActivity.class));

        } else if (id == R.id.nav_nutri) {
            startActivity(new Intent(RegisterActivity.this, NutritionistNearbyActivity.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(RegisterActivity.this, JoinTrainerClubActivity.class));


        } else if (id == R.id.nav_events) {
            startActivity(new Intent(RegisterActivity.this, UserProfileActivity.class));


        } else if (id == R.id.nav_payment) {
            startActivity(new Intent(RegisterActivity.this, TrainerProfileActivity.class));


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
