package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapp.R;

public class MainActivity extends AppCompatActivity {
    EditText tvEmail, tvPassword;
    TextView btnLogin, tvRegister, btnForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
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
}