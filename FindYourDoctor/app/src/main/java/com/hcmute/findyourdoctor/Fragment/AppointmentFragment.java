package com.hcmute.findyourdoctor.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Booking;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment implements OnHistoryAppointmentClickListener {
    private Fragment haveAppointmentFragment;
    private Fragment emptyAppointmentFragment;
    private FragmentManager fragmentManager;
    private FrameLayout flContent;
    private SharedPreferences sharedPreferences;
    private Fragment currentFragment;
    List<Booking> mAppointment;
    String uid;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderAppointmentList();

    }
    private void renderAppointmentList() {
        mAppointment = new ArrayList<>();
        BookingApiService bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingApiService.getBookingListId(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean() && res.getAsJsonArray("result").size() > 0) {
                    JsonArray result = res.getAsJsonArray("result");
                    haveAppointmentFragment = new HaveAppointmentFragment(result);
                    setCurrentFragment(haveAppointmentFragment);
                }
                else {
                    setCurrentFragment(emptyAppointmentFragment);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickHistoryAppointment(String bookingId) {
    }
    private void setCurrentFragment(Fragment fragment) {
        if (currentFragment != null && currentFragment != fragment) {
            fragmentManager.beginTransaction().remove(currentFragment).commit();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fl_content_appointment_list, fragment)
                .commit();

        currentFragment = fragment;
    }

    private void init(View view) {
        sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", null);

        flContent = view.findViewById(R.id.fl_content_appointment_list);
        fragmentManager = getActivity().getSupportFragmentManager();
        currentFragment = null;
        emptyAppointmentFragment = new EmptyAppointmentFragment();
    }

}