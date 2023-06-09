package com.example.doctorapp.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapp.Api.DoctorApiService;
import com.example.doctorapp.Api.PatientApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.CheckTextInput;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    DoctorApiService doctorApiService;
    EditText edtEmail;
    TextView btnContinue, tvError;
    ImageView imv_back_forget_password;
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == VerifyCodeActivity.VERIFY) {
                Intent intentChangePassword = new Intent(ForgetPasswordActivity.this, ChangePasswordActivity.class);
                intentChangePassword.putExtra("email", edtEmail.getText().toString().trim());
                startActivity(intentChangePassword);
                finish();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
        setOnClickContinue();

        imv_back_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickContinue() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!CheckTextInput.isValidEmail(edtEmail.getText().toString())) {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Invalid email");
                    return;
                }
                doctorApiService.isExistedDoctor(edtEmail.getText().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(res.get("result").getAsBoolean()) {
                            Intent intent = new Intent(ForgetPasswordActivity.this, VerifyCodeActivity.class);
                            intent.putExtra("email", edtEmail.getText().toString().trim());
                            resultLauncher.launch(intent);
                        }
                        else if(res.get("result").getAsBoolean() == false) {
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText("Email not registered account");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ForgetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void init() {
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        edtEmail = findViewById(R.id.edt_email_forget_password);
        btnContinue = findViewById(R.id.btn_continue_forget_password);
        tvError = findViewById(R.id.tv_error_forget_password);
        imv_back_forget_password = findViewById(R.id.imv_back_forget_password);
    }
}