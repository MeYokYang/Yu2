package com.zhly.yu2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhly.yu2.utils.NetWorkInfoUtils;


public class LoginActivity extends AppCompatActivity {

    EditText eTPhoneNumber, eTPassword;
    Button btnLoginIn, btnFP, btnSU;
    static int Request_FP_CODE = 1;
    static int Request_SU_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp == null){
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegistrationActivity.class);
            startActivityForResult(intent, Request_SU_CODE);
        }else {
            String phoneNumber = sp.getString("phoneNumber", "");
            eTPhoneNumber.setText(phoneNumber);
        }

    }


    private void initView() {
        eTPhoneNumber = findViewById(R.id.phone_number);
        eTPassword = findViewById(R.id.password);
        btnLoginIn = findViewById(R.id.login_in);
        btnFP = findViewById(R.id.forget_password);
        btnSU = findViewById(R.id.sign_up);
    }

    private void initEvent() {

        btnLoginIn.setOnClickListener(view -> {

            String phoneNumber = eTPhoneNumber.getText().toString().trim();
            String password = eTPassword.getText().toString().trim();

            if (phoneNumber != null && !phoneNumber.equals("") && password != null && !password.equals("")){

                if (NetWorkInfoUtils.isConnected(LoginActivity.this)){
                    login(phoneNumber, password);
                }else {
                    Toast.makeText(LoginActivity.this, "Please check the network connection!", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(LoginActivity.this, "The information cannot be blank!", Toast.LENGTH_LONG).show();
            }
        });

        btnFP.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RetrieveActivity.class);
            startActivityForResult(intent, Request_FP_CODE);
        });

        btnSU.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegistrationActivity.class);
            startActivityForResult(intent, Request_SU_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Request_FP_CODE || resultCode == Request_SU_CODE) {
            if(data != null){
                String phoneNumber = data.getStringExtra("phoneNumber");
                eTPhoneNumber.setText(phoneNumber);
            }
        }

    }

    private void login(String phoneNumber, String password) {

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String phoneNumberTrue = sp.getString("phoneNumber", "");
        String passwordTrue = sp.getString("password", "");
        if (phoneNumber.equals(phoneNumberTrue) && password.equals(passwordTrue)) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            eTPassword.setText("");
            Toast.makeText(LoginActivity.this, "Worry phone number or password!", Toast.LENGTH_LONG)
                    .show();
        }

    }

}