package com.hcmute.findyourdoctor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

    EditText edtNum1, edtNum2, edtNum3, edtNum4;
    TextView btnSubmit, tvWrong;
    AuthApiService authApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        init();
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkText())
                {
                    HashMap<String, String> body = new HashMap<>();
                    Intent intent = getIntent();
                    String code = intent.getStringExtra("code").toString();
                    String patientCode = edtNum1.getText().toString().trim() +  edtNum2.getText().toString().trim() +edtNum3.getText().toString().trim() +edtNum4.getText().toString().trim();
                    System.out.println(code + " --- " +  patientCode);
                    if(!code.equals(patientCode))
                    {
                        tvWrong.setVisibility(View.VISIBLE);
                        return;
                    }

                    body.put("name", intent.getStringExtra("name"));
                    body.put("email", intent.getStringExtra("email"));
                    body.put("phone", intent.getStringExtra("phone"));
                    body.put("password", intent.getStringExtra("password"));
                    body.put("birthDate", intent.getStringExtra("birthDate"));

                    authApiService.registerPatient(body).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.isSuccessful())
                            {
                                JsonObject res = response.body();
                                Toast.makeText(VerifyCodeActivity.this, res.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                Intent intentLogin = new Intent(VerifyCodeActivity.this, LoginActivity.class);
                                startActivity(intentLogin);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(VerifyCodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
        edtNum1 = (EditText) findViewById(R.id.edt_num1);
        edtNum2 = (EditText) findViewById(R.id.edt_num2);
        edtNum3 = (EditText) findViewById(R.id.edt_num3);
        edtNum4 = (EditText) findViewById(R.id.edt_num4);
        tvWrong = (TextView) findViewById(R.id.tv_wrong);
        btnSubmit = (TextView) findViewById(R.id.btn_submit);
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

    }
}
