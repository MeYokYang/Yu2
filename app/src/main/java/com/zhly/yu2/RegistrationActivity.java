package com.zhly.yu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhly.yu2.utils.NetWorkInfoUtils;
import com.zhly.yu2.utils.registration.RegistrationCheck;
import com.zhly.yu2.utils.registration.RegistrationStore;

public class RegistrationActivity extends AppCompatActivity {

    ImageButton btnBack;
    EditText eTPhoneNumber, eTUsername, eTPassword, eTCPassword;
    Button btnSU;

    public static int SUCCESS = 2;
    public static int FAILED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
        initEvent();

    }

    private void initView() {
        btnBack = findViewById(R.id.back);
        eTPhoneNumber = findViewById(R.id.phoneNumber);
        eTUsername = findViewById(R.id.username);
        eTPassword = findViewById(R.id.password);
        eTCPassword = findViewById(R.id.confirmPassword);
        btnSU = findViewById(R.id.sign_up);
    }

    private void initEvent() {
        btnBack.setOnClickListener(view -> {
            setResult(FAILED);
            finish();
        });

        btnSU.setOnClickListener(view -> {

            String phoneNumber = eTPhoneNumber.getText().toString().trim();
            String username = eTUsername.getText().toString().trim();
            String password = eTPassword.getText().toString().trim();
            String cPassword = eTCPassword.getText().toString().trim();

            if (phoneNumber != null && !phoneNumber.equals("")
                    && username != null && !username.equals("")
                    && password != null && !password.equals("")
                    && cPassword != null && !cPassword.equals("")){

                if (NetWorkInfoUtils.isConnected(RegistrationActivity.this)){
                    //校验手机号
                    if (password.equals(cPassword)) {
                        if (RegistrationCheck.checkPhone(phoneNumber)) {
                            RegistrationStore.UserStore(phoneNumber, username, password);

                            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("phoneNumber", phoneNumber);
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.commit();

                            Intent intent = new Intent();
                            intent.putExtra("phoneNumber", phoneNumber);
                            setResult(SUCCESS, intent);
                            finish();
                        }else {
                            Toast.makeText(RegistrationActivity.this, "The phone number has been registered!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        eTPassword.setText("");
                        eTCPassword.setText("");
                        Toast.makeText(RegistrationActivity.this, "Worry password repeatedly!", Toast.LENGTH_LONG)
                                .show();
                    }
                }else {
                    Toast.makeText(RegistrationActivity.this, "Please check the network connection!", Toast.LENGTH_LONG)
                            .show();
                }
            }else{
                Toast.makeText(RegistrationActivity.this, "The information cannot be blank!", Toast.LENGTH_LONG)
                        .show();
            }


        });
    }
}