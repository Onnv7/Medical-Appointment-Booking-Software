package com.hcmute.findyourdoctor.Activity;

import static android.content.ContentValues.TAG;
import static com.hcmute.findyourdoctor.Utils.Constant.SHARE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Domain.ConnectivityReceiver;
import com.hcmute.findyourdoctor.R;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText tvEmail, tvPassword;
    TextView btnLogin, tvRegister, btnForgetPassword;
    AuthApiService authApiService;
    private ConnectivityReceiver connectivityReceiver;
    private boolean isNetworkConnected = false;

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

        connectivityReceiver = new ConnectivityReceiver();


        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isConnected = connectivityReceiver.isConnected(LoginActivity.this);

                if (isConnected == true)
                {
                    authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);

                    if(checkText()) {
                        JsonObject body = new JsonObject();
                        body.addProperty("email", tvEmail.getText().toString());
                        body.addProperty("password", tvPassword.getText().toString());

                        authApiService.loginPatient(body).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject res = response.body();
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
                else
                {
                    Toast.makeText(LoginActivity.this, "Please turn on the network connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (tvPassword.getRight() - tvPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (tvPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            tvPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            tvPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            tvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            tvPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        tvPassword.setSelection(tvPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
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
    private void initView() {
        tvEmail = (EditText) findViewById(R.id.edt_email_log);
        tvPassword = (EditText) findViewById(R.id.edt_password_log);
        btnLogin = (TextView) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register_log);
        btnForgetPassword = findViewById(R.id.btn_forget_password_login);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
            connectivityReceiver = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (connectivityReceiver == null) {
            connectivityReceiver = new ConnectivityReceiver();
        }
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}