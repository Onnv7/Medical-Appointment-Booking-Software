package com.hcmute.findyourdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.LoginActivity;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtPassword, edtRePassword;
    TextView btnChangePassword;
    AuthApiService authApiService;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setOnChangePasswordClick();
    }
    private void setOnChangePasswordClick() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput() == false) {
                    return;
                }
                JsonObject body = new JsonObject();
                body.addProperty("email", email);
                body.addProperty("password", edtPassword.getText().toString());
                authApiService.changePassword(body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(res.get("success").getAsBoolean()) {
                            Toast.makeText(ChangePasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                            finish();
                        }
                        else {
                            Toast.makeText(ChangePasswordActivity.this, "Change password failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });


    }
    private boolean checkInput() {
        boolean flag = true;
        if(TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError("Please don't leave it blank");
            flag = false;
        }
        if(TextUtils.isEmpty(edtRePassword.getText())) {
            edtPassword.setError("Please don't leave it blank");
            flag = false;
        }
        if(!edtPassword.getText().toString().equals(edtRePassword.getText().toString())) {
            edtRePassword.setError("Password incorrect");
            edtRePassword.requestFocus();
            flag = false;
        }
        return flag;
    }
    private void init() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtPassword = findViewById(R.id.edt_new_password_cp);
        edtRePassword = findViewById(R.id.edt_re_new_password_cp);
        btnChangePassword = findViewById(R.id.btn_change_password_cp);
    }
}