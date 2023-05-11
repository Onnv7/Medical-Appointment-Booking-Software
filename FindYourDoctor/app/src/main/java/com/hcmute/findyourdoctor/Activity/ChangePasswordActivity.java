package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtNewPassword, edtRePassword;
    TextView btnChangePassword;
    AuthApiService authApiService;
    ImageView ivBack;
    String uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setOnButtonClick();
    }
    private void setOnButtonClick() {
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
                        else {
                            Toast.makeText(ChangePasswordActivity.this, "Change password failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edtNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtNewPassword.getRight() - edtNewPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtNewPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtNewPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtNewPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtNewPassword.setSelection(edtNewPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });


        edtRePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtRePassword.getRight() - edtRePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtRePassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtRePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtRePassword.setSelection(edtRePassword.getText().length());
                        return true;
                    }
                }
                return false;
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