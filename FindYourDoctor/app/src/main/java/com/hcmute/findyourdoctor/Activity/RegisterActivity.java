package com.hcmute.findyourdoctor.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Fragment.ProfileFragment;
import com.hcmute.findyourdoctor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword, edtRePwd, edtPhone, edtBirthdate;
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
                    body.put("email", edtEmail.getText().toString().trim());
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
                                intent.putExtra("name", edtName.getText().toString());
                                intent.putExtra("email", edtEmail.getText().toString());
                                intent.putExtra("phone", edtPhone.getText().toString());
                                Log.d("nva", edtBirthdate.getText().toString());
                                intent.putExtra("birthDate", edtBirthdate.getText().toString());
                                intent.putExtra("password", edtPassword.getText().toString());
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
        if(TextUtils.isEmpty(edtName.getText()) || TextUtils.isEmpty(edtEmail.getText()) || 
                TextUtils.isEmpty(edtPassword.getText()) || TextUtils.isEmpty(edtRePwd.getText()) ||
                TextUtils.isEmpty(edtPhone.getText()) || TextUtils.isEmpty(edtBirthdate.getText())) {
            Toast.makeText(this, "Information cannot be left blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.equals(edtPassword.getText(), edtRePwd.getText()))
        {
            Toast.makeText(this, "Invalid confirmation password", Toast.LENGTH_SHORT).show();
            edtRePwd.setText("");
            edtRePwd.requestFocus();
            return false;
        }
        if(!isEmail(edtEmail.getText().toString())) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return false;
        }
        if(!radioPolicy.isChecked()) {
            Toast.makeText(this, "Please agree to our policy to continue to register", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    // TODO: code here
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "((\\+84)|(84)|0)9[01]\\d{7}";
        return phoneNumber.matches(regex);
    }
    public boolean isEmail(String text) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return text.matches(emailPattern);
    }
    private void init() {
        edtName = findViewById(R.id.edt_name_reg);
        edtEmail = findViewById(R.id.edt_email_reg);
        edtPassword = findViewById(R.id.edt_password_reg);
        edtBirthdate = findViewById(R.id.edt_birthdate_reg);
        edtRePwd = findViewById(R.id.tv_re_pwd_reg);
        edtPhone = findViewById(R.id.edt_phone_reg);
        btnSignup = findViewById(R.id.btn_signup);
        radioPolicy = findViewById(R.id.radio_poplicy);
        tvLogin = (TextView) findViewById(R.id.tv_register_log);

        edtBirthdate.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(i, i1-1, i2);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = sdf.format(calendar.getTime());
                        edtBirthdate.setText(formattedDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
    }
}
