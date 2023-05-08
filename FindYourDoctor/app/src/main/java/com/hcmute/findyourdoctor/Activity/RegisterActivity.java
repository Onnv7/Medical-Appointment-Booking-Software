package com.hcmute.findyourdoctor.Activity;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.AuthApiService;
import com.hcmute.findyourdoctor.Api.PatientApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launchResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
//            System.out.println(result.getData().toString() + "");
            if(result != null && result.getResultCode() == VerifyCodeActivity.VERIFY) {
//                Toast.makeText(RegisterActivity.this, "ok ne 44" + result.getData().getBooleanExtra("success", false), Toast.LENGTH_SHORT).show();
                JsonObject body = new JsonObject();
                body.addProperty("name", edtName.getText().toString().trim());
                body.addProperty("email", edtEmail.getText().toString().trim());
                body.addProperty("phone", edtPhone.getText().toString().trim());
                body.addProperty("birthDate", edtBirthdate.getText().toString().trim());
                body.addProperty("password", edtPassword.getText().toString().trim());
                authApiService.registerPatient(body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(res.get("success").getAsBoolean()) {
                            Toast.makeText(RegisterActivity.this, "Successful account registration", Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        }
    });
    EditText edtName, edtEmail, edtPassword, edtRePwd, edtPhone, edtBirthdate;
    TextView btnSignup, tvLogin;
    RadioButton radioPolicy;
    AuthApiService authApiService;
    PatientApiService patientApiService;
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
                    patientApiService.isExistedPatient(edtEmail.getText().toString()).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject res = response.body();
                            if(res.get("result").getAsBoolean()) {
                                edtEmail.setText("Email was registered account");
                            }
                            else if(res.get("result").getAsBoolean() == false) {
                                Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
                                intent.putExtra("email", edtEmail.getText().toString().trim());
                                launchResult.launch(intent);
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
                finish();
            }
        });


        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtPassword.setSelection(edtPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        edtRePwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtRePwd.getRight() - edtRePwd.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (edtRePwd.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            edtRePwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.eye);
                            edtRePwd.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);

                        } else {
                            edtRePwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable newDrawable = getResources().getDrawable(R.drawable.noteye);
                            edtRePwd.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null);
                        }
                        edtRePwd.setSelection(edtRePwd.getText().length());
                        return true;
                    }
                }
                return false;
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
            edtRePwd.setError("Invalid confirmation password");
            edtRePwd.setText("");
            edtRePwd.requestFocus();
            return false;
        }
        if(edtPassword.getText().length() < 6) {
            edtPassword.setError("Password must be at least 6 characters");
            return false;
        }
        if(!isEmail(edtEmail.getText().toString())) {
//            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            edtEmail.setText("Invalid email");
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
        patientApiService = RetrofitClient.getRetrofit().create(PatientApiService.class);
        authApiService = RetrofitClient.getRetrofit().create(AuthApiService.class);
        edtName = findViewById(R.id.edt_name_reg);
        edtEmail = findViewById(R.id.edt_email_reg);
        edtPassword = findViewById(R.id.edt_password_reg);
        edtBirthdate = findViewById(R.id.edt_birthdate_reg);
        edtRePwd = findViewById(R.id.tv_re_pwd_reg);
        edtPhone = findViewById(R.id.edt_phone_reg);
        btnSignup = findViewById(R.id.btn_forget_password_login);
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
