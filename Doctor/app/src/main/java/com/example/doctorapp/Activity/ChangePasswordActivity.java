package com.example.doctorapp.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapp.Api.AuthApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    EditText edtNewPassword, edtRePassword;
    TextView btnChangePassword;
    AuthApiService authApiService;
    CardView ivBack;
    String uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setOnChangePasswordClick();
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }
    private void setOnChangePasswordClick() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput() == false) {
                    return;
                }
                JsonObject body = new JsonObject();

                body.addProperty("email", uemail);
                body.addProperty("password", edtRePassword.getText().toString());
                authApiService.changePassword(body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();

                        if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                            Toast.makeText(ChangePasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private boolean checkInput() {
        boolean flag = true;
        if(TextUtils.isEmpty(edtNewPassword.getText())) {
            edtNewPassword.setError("Please don't leave it blank");
            flag = false;
        }
        if(TextUtils.isEmpty(edtRePassword.getText())) {
            edtNewPassword.setError("Please don't leave it blank");
            flag = false;
        }
        if(!edtNewPassword.getText().toString().equals(edtRePassword.getText().toString())) {
            edtRePassword.setError("Password incorrect");
            edtRePassword.requestFocus();
            flag = false;
        }
        if(edtNewPassword.length() < 6) {
            edtNewPassword.setError("Password minimum 6 characters");
            edtNewPassword.requestFocus();
            flag = false;
        }
        return flag;
    }
    private void init() {
        Intent intent = getIntent();
        uemail = intent.getStringExtra("email");
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtNewPassword = findViewById(R.id.edt_new_pwd_change_password);
        edtRePassword = findViewById(R.id.edt_re_pwd_change_password);
        btnChangePassword = findViewById(R.id.btn_change_pwd_change_password);
        ivBack = findViewById(R.id.iv_back_change_password);
    }
}