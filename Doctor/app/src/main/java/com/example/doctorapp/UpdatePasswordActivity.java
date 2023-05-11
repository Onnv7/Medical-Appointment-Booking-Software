package com.example.doctorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.doctorapp.Api.AuthApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    EditText edtNewPassword, edtRePassword, edtOldPassword;
    TextView btnChangePassword;
    AuthApiService authApiService;
    ImageView ivBack;
    String uemail;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        init();
        setOnButtonClick();


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


        edtOldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtOldPassword.getRight() - edtOldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtOldPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtOldPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtOldPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtOldPassword.setSelection(edtOldPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });


    }
    private void setOnButtonClick() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(UpdatePasswordActivity.this);
                alert.setTitle("Profile Update");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setMessage("Do you want to change your password?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkInput() == false) {
                            return;
                        }
                        JsonObject body = new JsonObject();

                        body.addProperty("email", uemail);
                        body.addProperty("oldPassword", edtOldPassword.getText().toString());
                        body.addProperty("password", edtRePassword.getText().toString());
                        authApiService.updatePassword(body).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject res = response.body();
                                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                                    Toast.makeText(UpdatePasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(response.isSuccessful() && !res.get("success").getAsBoolean()){
                                    Toast.makeText(UpdatePasswordActivity.this, res.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(UpdatePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.show();

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
        if(TextUtils.isEmpty(edtOldPassword.getText())) {
            edtOldPassword.setError("Please don't leave it blank");
            flag = false;
        }
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
        sharedPreferences = UpdatePasswordActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uemail = sharedPreferences.getString("email", "");
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtNewPassword = findViewById(R.id.edt_new_pwd_update_password);
        edtRePassword = findViewById(R.id.edt_re_pwd_update_password);
        edtOldPassword = findViewById(R.id.edt_old_pwd_update_password);
        btnChangePassword = findViewById(R.id.btn_change_pwd_update_password);
        ivBack = findViewById(R.id.iv_back_update_password);
    }

}