package com.hcmute.findyourdoctor.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmute.findyourdoctor.R;

public class RegisterActivity extends AppCompatActivity {
    TextView tvName, tvEmail, tvPassword, tvRePwd;
    TextView btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();


    }
    private void init() {
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPassword = findViewById(R.id.tv_password);
        tvRePwd = findViewById(R.id.tv_re_pwd);
        btnSignup = findViewById(R.id.btn_signup);
    }
}
