package antitheftproject.android.cmpe277.antitheftproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Log";
    private final String registerUrl = Constant.url + "/users/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void register(View v) {
        EditText userNameET = (EditText) findViewById(R.id.registerNameET);
        EditText userPwdET = (EditText) findViewById(R.id.resgiterPwdET);
        EditText userEmailET = (EditText) findViewById(R.id.registerEmailET);
        EditText userPhoneET = (EditText) findViewById(R.id.registerPhoneET);

        String userName = userNameET.getText().toString();
        String userPwd = userPwdET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userPhone = userPhoneET.getText().toString();

        if(userPwd.length()<=6){
            Toast.makeText(getApplicationContext(), "Password should be of at least 7 characters", Toast.LENGTH_LONG).show(); // displaying message
            return;
        }

        if (userEmail.equals("") || userName.equals("")  || userPhone.equals("")) {
            Toast.makeText(this, "Please fill in all the missing fields", Toast.LENGTH_LONG).show();
            return;
        } else {
            JSONObject json = new JSONObject();
            try {
                json.put("phoneNumber", userPhone);
                json.put("password", userPwd);
                json.put("email", userEmail);
                json.put("userName", userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (json.length() > 0) {
                new RegisterTask().execute(String.valueOf(json));
            }
        }
    }

    private class RegisterTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            String JsonResponse = null;
            String JsonData = params[0];
            URL url;
            try {
                url = new URL(registerUrl);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(JsonData);
                System.out.println(JsonData);
                writer.flush();
                writer.close();
                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine +"\n");
                if (buffer.length() == 0) {
                    // Stream was empty.
                    return null;
                }
                JsonResponse = buffer.toString();
                //response data
                Log.i(TAG, JsonResponse);
                //send to post execute
                return JsonResponse;


            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                }
            }


            return JsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            {

                Toast.makeText(getApplicationContext(), "Successfully Registered , please log in "  , Toast.LENGTH_LONG).show();
            }
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();


        }
    }

}
