package com.example.loginmedical.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.loginmedical.R;

public class Register extends AppCompatActivity {

    ConstraintLayout signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        signup = findViewById(R.id.signup);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                opendialogsuccess(Gravity.BOTTOM);
//            }
//        });
    }
    private void opendialogsuccess(int gravity){
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.successfull);
//        Window window = dialog.getWindow();
//        if(window == null){
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = gravity;
//        window.setAttributes(windowAttributes);
//
//        dialog.show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.digit_code);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.show();
    }
}