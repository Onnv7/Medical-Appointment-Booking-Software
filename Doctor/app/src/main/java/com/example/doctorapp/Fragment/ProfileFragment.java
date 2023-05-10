package com.example.doctorapp.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Activity.LoginActivity;
import com.example.doctorapp.Api.DoctorApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Activity.ChangePasswordActivity;
import com.example.doctorapp.Model.Doctor;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.example.doctorapp.Utils.RealPathUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private ImageView btnUploadImage, ivAvatar;
    private LinearLayout layoutBirthdate;
    private EditText edtName, edtEmail, edtPassword, edtPhone, edtBirthDate, edtAddress, edtClinicName, edtSpecialist, edtIntroduction;
    private TextView btnUpadte, btnLogout, tvHelloName;
    private FloatingActionButton fabMore, fabLogout, fabChangePassword;
    private RadioButton rdoFemale, rdoMale;
    private String uid;
    private DoctorApiService doctorApiService;
    private boolean open = false;
    private Uri avatarUri;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if(data == null) {
                    return;
                }
                Uri uri = data.getData();
                avatarUri = uri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    ivAvatar.setImageBitmap(bitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });
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

        setOnFabClick();
        setOnUploadImageButtonClick();
        setOnClickBtnUpdate();
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
    private void setOnClickBtnUpdate() {
        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, RequestBody> body = new HashMap<>();
                MultipartBody.Part filePart = null;
                if(avatarUri != null) {
                    String realPath  = RealPathUtil.getRealPath(getContext(), avatarUri);
                    File file = new File(realPath);
                    String extension = MimeTypeMap.getFileExtensionFromUrl(realPath);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType), file);
                    filePart = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
                    avatarUri = null;
                }
                MediaType mediaType = MediaType.parse("text/plain");

                if(rdoFemale.isChecked()) {
                    body.put("gender", RequestBody.create(mediaType, "female"));
                }
                else if(rdoMale.isChecked()) {
                    body.put("gender", RequestBody.create(mediaType, "male"));
                }
                body.put("name", RequestBody.create(mediaType, edtName.getText().toString()));
                body.put("phone", RequestBody.create(mediaType, edtPhone.getText().toString()));
                body.put("birthDate", RequestBody.create(mediaType, edtBirthDate.getText().toString()));
                body.put("address", RequestBody.create(mediaType, edtAddress.getText().toString()));
                body.put("introduction", RequestBody.create(mediaType, edtIntroduction.getText().toString()));
                doctorApiService.updateProfile(uid, body, filePart).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()) {
                            renderProfile();
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void setOnUploadImageButtonClick() {
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
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

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Confirm");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setMessage("Do you want to sign out ?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("id");
                        editor.remove("email");
                        editor.remove("is_logged");
                        editor.apply();

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();


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
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            String[] storge_permissions_33 = {
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_VIDEO

            };
            requestPermissions(storge_permissions_33, 1);
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
        else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
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
                    tvHelloName.setText("Hello " + doctor.getName());
                    edtPhone.setText(doctor.getPhone());
                    edtBirthDate.setText(doctor.getBirthDate());
                    edtClinicName.setText(doctor.getClinicName());
                    edtAddress.setText(doctor.getClinicAddress());
                    edtSpecialist.setText(doctor.getSpecialist().getName());
                    edtIntroduction.setText(doctor.getIntroduction());

                    Glide.with(getContext())
                            .load(doctor.getAvatarUrl())
                            .into(ivAvatar);
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
    private void init(View view) {
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        sharedPreferences = getActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");


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
        edtIntroduction = view.findViewById(R.id.edt_introduction_pf);

        rdoMale = view.findViewById(R.id.rdo_male_pf);
        rdoFemale = view.findViewById(R.id.rdo_female_pf);

        fabMore = view.findViewById(R.id.fab_more_pf);
        fabLogout = view.findViewById(R.id.fab_logout_pf);
        fabChangePassword = view.findViewById(R.id.fab_change_password_pf);

        fabLogout.setVisibility(View.GONE);
        fabChangePassword.setVisibility(View.GONE);
    }
}