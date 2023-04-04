package com.hcmute.findyourdoctor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText tvName, tvEmail, tvPassword, tvRePwd;
    TextView btnSignup, tvLogin;
    RadioButton radioPolicy;
    AuthApiService authApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkText())
                {
                    authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
                    HashMap<String, String> body = new HashMap<>();
                    body.put("email", tvEmail.getText().toString().trim());
                    authApiService.sendCodeToEmail(body).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            System.out.println(response.body());
                            if(response.isSuccessful())
                            {
                                String code;
                                JsonObject res = response.body();
                                code = res.get("result").toString();

                                Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
                                intent.putExtra("name", tvName.getText().toString());
                                intent.putExtra("email", tvEmail.getText().toString());
                                intent.putExtra("password", tvPassword.getText().toString());
                                intent.putExtra("code", code);

                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

    }
    private boolean checkText() {
        if(TextUtils.isEmpty(tvName.getText()) || TextUtils.isEmpty(tvEmail.getText()) || 
                TextUtils.isEmpty(tvPassword.getText()) || TextUtils.isEmpty(tvRePwd.getText())) {
            Toast.makeText(this, "Information cannot be left blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.equals(tvPassword.getText(), tvRePwd.getText()))
        {
            Toast.makeText(this, "Invalid confirmation password", Toast.LENGTH_SHORT).show();
            tvRePwd.setText("");
            tvRePwd.requestFocus();
            return false;
        }
        if(!isEmail(tvEmail.getText().toString())) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            tvEmail.requestFocus();
            return false;
        }
        if(!radioPolicy.isChecked()) {
            Toast.makeText(this, "Please agree to our policy to continue to register", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isEmail(String text) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return text.matches(emailPattern);
    }
    private void init() {
        tvName = findViewById(R.id.edt_name_reg);
        tvEmail = findViewById(R.id.et_email_reg);
        tvPassword = findViewById(R.id.edt_password_reg);
        tvRePwd = findViewById(R.id.tv_re_pwd_reg);
        btnSignup = findViewById(R.id.btn_signup);
        radioPolicy = findViewById(R.id.radio_poplicy);
        tvLogin = (TextView) findViewById(R.id.tv_register_log);
    }
}
