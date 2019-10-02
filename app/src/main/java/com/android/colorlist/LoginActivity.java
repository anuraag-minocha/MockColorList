package com.android.colorlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    ProgressBar topProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editText);
        etPassword = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.button);
        topProgressBar = findViewById(R.id.topProgressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etEmail.getText().toString(),etPassword.getText().toString());

            }
        });
    }

    private void login(final String email, final String password) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> myTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                topProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {

                String response;

                try {

                    String url = InternetOperations.SERVER_URL+"login";

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email",email);
                    jsonObject.put("password",password);

                    response = InternetOperations.post(url,jsonObject.toString());

                    return response;

                } catch (Exception e) {
                    e.printStackTrace();

                }

                return null;
            }

            protected void onPostExecute(String s) {

                topProgressBar.setVisibility(View.GONE);

                if (s != null) {


                    try {


                        JSONObject object = new JSONObject(s);

                        if(object.has("token")){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }


            }
        };
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
