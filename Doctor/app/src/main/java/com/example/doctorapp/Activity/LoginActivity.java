package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapp.Api.AuthApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Model.Doctor;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    TextView btnLogin, tvRegister, btnForgetPassword;
    AuthApiService authApiService;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = LoginActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean("is_logged", false);

        if(isLogged)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        initView();
        setOnLoginButtonClick();

        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtPassword.setSelection(edtPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setOnLoginButtonClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkText()){
                    JsonObject body = new JsonObject();
                    body.addProperty("email", edtEmail.getText().toString());
                    body.addProperty("password", edtPassword.getText().toString());
                    authApiService.doctorLogin(body).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.isSuccessful()){
                                JsonObject res = response.body();
                                if(res.get("success").getAsBoolean()) {
                                    Gson gson = new Gson();
                                    JsonObject result = res.get("result").getAsJsonObject();
                                    Doctor doctor = gson.fromJson(result, Doctor.class);
                                    if(doctor.getId() != null) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("id", doctor.getId());
                                        editor.putString("name", doctor.getName());
                                        editor.putString("email", doctor.getEmail());
                                        editor.putBoolean("is_logged", true);
                                        editor.apply();
                                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
//                                        intentMain.putExtra("name", doctor.getName());
//                                        intentMain.putExtra("avatarUrl", doctor.getAvatarUrl());
                                        startActivity(intentMain);
                                        finish();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                                    edtEmail.requestFocus();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    private boolean checkText() {
        if(TextUtils.isEmpty(edtEmail.getText()))
        {
            edtEmail.requestFocus();
            Toast.makeText(this, "Please enter full account information", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtPassword.getText()))
        {
            edtEmail.requestFocus();
            Toast.makeText(this, "Please enter full account information", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initView() {
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtEmail = (EditText) findViewById(R.id.edt_email_login);
        edtPassword = (EditText) findViewById(R.id.edt_password_login);
        btnLogin = (TextView) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register_log);
        btnForgetPassword = findViewById(R.id.btn_forget_password_login);
    }
}