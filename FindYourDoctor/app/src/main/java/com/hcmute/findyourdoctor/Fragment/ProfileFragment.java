package com.hcmute.findyourdoctor.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.LoginActivity;
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.PatientApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;


public class ProfileFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    ImageView btnUploadImage, ivAvatar;
    LinearLayout layoutBirthdate;
    EditText edtName, edtEmail, edtPassword, edtPhone, edtBirthDate, edtAddress;
    TextView btnUpadte;
    RadioButton rdoFemale, rdoMale;
    PatientApiService patientApiService;
    DatePickerDialog.OnDateSetListener setListener;
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        sharedPreferences = requireActivity().getSharedPreferences(LoginActivity.SHARE, Context.MODE_PRIVATE);
        String patientId = sharedPreferences.getString("id", null);
        patientApiService = RetrofitClient.getRetrofit().create(PatientApiService.class);



        if(patientId == null){
            Toast.makeText(getActivity(), "Patient ID is null", Toast.LENGTH_SHORT).show();
            return;
        }


        getProfilePatient(patientId);


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

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


                patientApiService.updateProfile(patientId, body, filePart).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("nva", response.toString());
                        if(response.isSuccessful()) {
                            renderProfile(response);
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("nva", t.getMessage());
                    }
                });
            }
        });
    }

    private void onClickRequestPermission() {
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
    private void renderProfile(Response<JsonObject> response) {

        JsonObject res = response.body();
        JsonObject result = res.getAsJsonObject("result");

        String name = result.get("name") != null ? result.get("name").getAsString() : "";
        String email = result.get("email") != null ? result.get("email").getAsString() : "";
        String phone = result.get("phone") != null ? result.get("phone").getAsString() : "";
        String birthDate = result.get("birthDate") != null ? result.get("birthDate").getAsString() : "";
        String gender = result.get("gender") != null ? result.get("gender").getAsString() : "";
        String address = result.get("address") != null ? result.get("address").getAsString() : "";
        String avatarUrl = result.get("avatarUrl") != null ? result.get("avatarUrl").getAsString() : "";

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = null;
        try {
            inputDate = inputDateFormat.parse(birthDate);
            birthDate = outputDateFormat.format(inputDate);
            edtBirthDate.setText(birthDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(birthDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; // do tháng trong Java bắt đầu từ 0
            int day = cal.get(Calendar.DAY_OF_MONTH);

            setBirthDateEditText(day, month, year);
        } catch (Exception e) {
            edtBirthDate.setText("");
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            Toast.makeText(getContext(), year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
            setBirthDateEditText(dayOfMonth, month, year);
        }

        if(gender.equalsIgnoreCase("female"))
        {
            rdoMale.setChecked(true);
        }
        else if(gender.equalsIgnoreCase("male"))
        {
            rdoMale.setChecked(true);
        }
        edtName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(phone);

        edtAddress.setText(address);
        if(avatarUrl.equals(""))
        {
            int imageResource = getResources().getIdentifier("avatar", "drawable", getActivity().getPackageName());
            ivAvatar.setImageResource(imageResource);
        }
        else
        {
            Glide.with(getContext())
                    .load(avatarUrl)
                    .into(ivAvatar);
        }
    }
    private void getProfilePatient(String patientId) {
        patientApiService.getProfileById(patientId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    renderProfile(response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void setBirthDateEditText(int day, int month, int year) {
        edtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        edtBirthDate.setText(i + "-" + i1 + "-" + i2);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
    }
    private void init(View view) {
        btnUploadImage = view.findViewById(R.id.btn_upload_image_pf);
        btnUpadte = view.findViewById(R.id.btn_update_pf);
        ivAvatar = view.findViewById(R.id.iv_avatar);

        edtName = view.findViewById(R.id.edt_name_pf);
        edtEmail = view.findViewById(R.id.edt_email_pf);
//        edtPassword = view.findViewById(R.id.edt_password_pf);
        edtPhone = view.findViewById(R.id.edt_phone_pf);
        edtBirthDate = view.findViewById(R.id.edt_birthDate_pf);

        edtAddress = view.findViewById(R.id.edt_address_pf);

        rdoMale = view.findViewById(R.id.rdo_male_pf);
        rdoFemale = view.findViewById(R.id.rdo_female_pf);



    }
}
