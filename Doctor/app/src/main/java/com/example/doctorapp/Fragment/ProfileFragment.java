package com.example.doctorapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.doctorapp.Activity.LoginActivity;
import com.example.doctorapp.Api.DoctorApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Activity.ChangePasswordActivity;
import com.example.doctorapp.Model.Doctor;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private ImageView btnUploadImage, ivAvatar;
    private LinearLayout layoutBirthdate;
    private EditText edtName, edtEmail, edtPassword, edtPhone, edtBirthDate, edtAddress, edtClinicName, edtSpecialist;
    private TextView btnUpadte, btnLogout, tvHelloName;
    private FloatingActionButton fabMore, fabLogout, fabChangePassword;
    private RadioButton rdoFemale, rdoMale;
    private String uid;
    private DoctorApiService doctorApiService;
    private boolean open = false;
    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        renderProfile();
        setOnLogoutButtonClick();
        setOnFabClick();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Drawable moreIcon = getResources().getDrawable(R.drawable.icon_more);
        open = false;
        fabLogout.setVisibility(View.GONE);
        fabChangePassword.setVisibility(View.GONE);
        fabMore.setImageDrawable(moreIcon);
    }

    private void setOnFabClick() {
        fabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable closeIcon = getResources().getDrawable(R.drawable.icon_close);
                Drawable moreIcon = getResources().getDrawable(R.drawable.icon_more);
                if(open == false) {
                    fabLogout.setVisibility(View.VISIBLE);
                    fabChangePassword.setVisibility(View.VISIBLE);
                    fabMore.setImageDrawable(closeIcon);
                    open = true;
                }
                else {
                    fabLogout.setVisibility(View.GONE);
                    fabChangePassword.setVisibility(View.GONE);
                    fabMore.setImageDrawable(moreIcon);
                    open = false;
                }
            }
        });
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("id");
                editor.remove("email");
                editor.remove("is_logged");
                editor.apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        fabChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private void renderProfile() {
        doctorApiService.getDoctorProfile(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonObject result = res.getAsJsonObject("result");
                    Doctor doctor = gson.fromJson(result, Doctor.class);

                    edtName.setText(doctor.getName());
                    edtEmail.setText(doctor.getEmail());
                    edtPhone.setText(doctor.getPhone());
                    edtBirthDate.setText(doctor.getBirthDate());
                    edtClinicName.setText(doctor.getClinicName());
                    edtAddress.setText(doctor.getClinicAddress());
                    edtSpecialist.setText(doctor.getSpecialist().getName());
                    if(doctor.getGender().equals("male")) {
                        rdoMale.setChecked(true);
                    } else {
                        rdoFemale.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void setOnLogoutButtonClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("id");
                editor.remove("email");
                editor.remove("is_logged");
                editor.apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(View view) {
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        sharedPreferences = getActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        btnLogout = view.findViewById(R.id.btn_log_out_profile);

        btnUploadImage = view.findViewById(R.id.btn_upload_image_pf);
        btnUpadte = view.findViewById(R.id.btn_update_pf);
        ivAvatar = view.findViewById(R.id.iv_avatar_doctor_pf);

        tvHelloName = view.findViewById(R.id.tv_hello_name_profile_fragment);

        edtName = view.findViewById(R.id.edt_name_pf);
        edtEmail = view.findViewById(R.id.edt_email_pf);
//        edtPassword = view.findViewById(R.id.edt_password_pf);
        edtPhone = view.findViewById(R.id.edt_phone_pf);
        edtBirthDate = view.findViewById(R.id.edt_birthDate_pf);
        edtClinicName = view.findViewById(R.id.edt_clinic_name_pf);
        edtAddress = view.findViewById(R.id.edt_address_pf);
        edtSpecialist = view.findViewById(R.id.edt_specialist_pf);

        rdoMale = view.findViewById(R.id.rdo_male_pf);
        rdoFemale = view.findViewById(R.id.rdo_female_pf);

        fabMore = view.findViewById(R.id.fab_more_pf);
        fabLogout = view.findViewById(R.id.fab_logout_pf);
        fabChangePassword = view.findViewById(R.id.fab_change_password_pf);

        fabLogout.setVisibility(View.GONE);
        fabChangePassword.setVisibility(View.GONE);
    }
}