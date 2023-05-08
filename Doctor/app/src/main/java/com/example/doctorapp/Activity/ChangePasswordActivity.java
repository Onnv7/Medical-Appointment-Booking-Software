package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
    private String uid;
    private String uemail;
    private EditText edtOldPwd, edtNewPwd, edtRePwd;
    private TextView btnChangePwd;
    private SharedPreferences sharedPreferences;
    private AuthApiService authApiService;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setOnChangePwdButtonClick();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setOnChangePwdButtonClick() {
        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEditText() == false) {
                    return;
                }
                JsonObject body = new JsonObject();
                body.addProperty("email", uemail);
                body.addProperty("oldPassword", edtOldPwd.getText().toString());
                body.addProperty("password", edtRePwd.getText().toString());
                authApiService.changePassword(body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                            Toast.makeText(ChangePasswordActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            edtOldPwd.requestFocus();
                            edtOldPwd.setError(res.get("message").getAsString());
                            edtNewPwd.setText("");
                            edtOldPwd.setText("");
                            edtRePwd.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean checkEditText() {
        if(TextUtils.isEmpty(edtOldPwd.getText())) {
            edtOldPwd.setError("Please complete all information");
            edtOldPwd.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(edtNewPwd.getText())) {
            edtNewPwd.setError("Please complete all information");
            edtNewPwd.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(edtRePwd.getText())) {
            edtRePwd.setError("Please complete all information");
            edtRePwd.requestFocus();
            return false;
        }
        if(!edtNewPwd.getText().toString().equals(edtRePwd.getText().toString())) {
            edtRePwd.setError("Re-password is invalid");
            return false;
        }
        return true;
    }
    private void init() {
        sharedPreferences = ChangePasswordActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        uemail = sharedPreferences.getString("email", "");

        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);

        edtOldPwd = findViewById(R.id.edt_old_pwd_change_password);
        edtNewPwd = findViewById(R.id.edt_new_pwd_change_password);
        edtRePwd = findViewById(R.id.edt_re_pwd_change_password);

        btnChangePwd = findViewById(R.id.btn_change_pwd_change_password);
        ivBack = findViewById(R.id.iv_back_change_password);
    }
}