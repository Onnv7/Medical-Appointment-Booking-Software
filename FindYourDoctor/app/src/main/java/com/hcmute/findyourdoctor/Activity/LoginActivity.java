package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.R;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String SHARE = "INFO";
    EditText tvEmail, tvPassword;
    TextView btnLogin, tvRegister;
    AuthApiService authApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(SHARE, Context.MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean("is_logged", false);

        if(isLogged)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);

                if(checkText()) {
                    HashMap<String, String> body = new HashMap<>();
                    body.put("email", tvEmail.getText().toString());
                    body.put("password", tvPassword.getText().toString());
                    authApiService.loginPatient(body).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject res = response.body();
                            Log.d("nva", response.toString());
                            if(response.isSuccessful()){
                                String id = res.getAsJsonObject("result").get("_id").getAsString();
                                String name = res.getAsJsonObject("result").get("name").getAsString();
                                String email = res.getAsJsonObject("result").get("email").getAsString();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", id);
                                editor.putString("name", name);
                                editor.putString("email", email);
                                editor.putBoolean("is_logged", true);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Incorrect password or email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    authApiService = null;
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }
    private boolean checkText() {
        if(TextUtils.isEmpty(tvEmail.getText()))
        {
            tvEmail.requestFocus();
            Toast.makeText(this, "Please enter full account information", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(tvPassword.getText()))
        {
            tvEmail.requestFocus();
            Toast.makeText(this, "Please enter full account information", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void init() {
        tvEmail = (EditText) findViewById(R.id.edt_email_log);
        tvPassword = (EditText) findViewById(R.id.edt_password_log);
        btnLogin = (TextView) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register_log);
    }
}