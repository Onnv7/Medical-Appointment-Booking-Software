package com.hcmute.findyourdoctor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class VerifyCodeActivity extends AppCompatActivity {
    public static final int VERIFY = 1;
    EditText edtNum1, edtNum2, edtNum3, edtNum4;
    TextView btnSubmit, tvWrong;
    AuthApiService authApiService;
    String email;
    String code;
    ImageView imv_back_from_vefify_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        init();
        sendCode();
        setOnSubmitClick();

    }
    private void setOnSubmitClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkText())
                {
                    HashMap<String, String> body = new HashMap<>();
                    String patientCode = edtNum1.getText().toString().trim() +  edtNum2.getText().toString().trim() +edtNum3.getText().toString().trim() +edtNum4.getText().toString().trim();
                    System.out.println(code + " --- " +  patientCode);
                    if(!code.equals(patientCode))
                    {
                        tvWrong.setVisibility(View.VISIBLE);
                        return;
                    }
                    else {
                        Intent intentRegister = new Intent();
                        intentRegister.putExtra("success", true);
                        setResult(VERIFY, intentRegister);
                        finish();
                    }
                }
            }
        });
    }
    private void sendCode() {
        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        authApiService.sendCodeToEmail(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println(response.body());
                if(response.isSuccessful())
                {
                    JsonObject res = response.body();
                    code = res.get("result").getAsString();
                    Toast.makeText(VerifyCodeActivity.this, "Code sent to email " + email, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private boolean checkText() {
        if(TextUtils.isEmpty(edtNum1.getText()))
        {
            edtNum1.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(edtNum2.getText()))
        {
            edtNum2.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(edtNum3.getText()))
        {
            edtNum3.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(edtNum4.getText()))
        {
            edtNum4.requestFocus();
            return false;
        }
        return true;
    }

    private void init() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtNum1 = (EditText) findViewById(R.id.edt_num1);
        edtNum2 = (EditText) findViewById(R.id.edt_num2);
        edtNum3 = (EditText) findViewById(R.id.edt_num3);
        edtNum4 = (EditText) findViewById(R.id.edt_search_appointment_list);
        tvWrong = (TextView) findViewById(R.id.tv_wrong);
        btnSubmit = (TextView) findViewById(R.id.btn_submit);
        imv_back_from_vefify_code = (ImageView) findViewById(R.id.imv_back_from_vefify_code);
        edtNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edtNum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edtNum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    edtNum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    edtNum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        imv_back_from_vefify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
