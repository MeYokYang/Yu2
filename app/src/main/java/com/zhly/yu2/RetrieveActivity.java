package com.zhly.yu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.zhly.yu2.utils.NetWorkInfoUtils;
import com.zhly.yu2.utils.registration.RegistrationCheck;
import com.zhly.yu2.utils.registration.RegistrationStore;
import com.zhly.yu2.utils.retrieve.RetrieveCheck;

import java.util.Random;

public class RetrieveActivity extends AppCompatActivity {

    ImageButton btnBack, ibCheckCodePicture;
    Button btnCheckMsg, btnRetrieve;
    EditText etPhoneNumber, etCheckCode, etCheckMsg;

    public static int SUCCESS = 1;
    public static int FAILED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        if (sp == null){
            Toast.makeText(RetrieveActivity.this, "No account!Please login up first!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(RetrieveActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        initView();
        initEvent();

    }

    private void initView() {
        btnBack = findViewById(R.id.back);
        etPhoneNumber = findViewById(R.id.phoneNumber);
        etCheckCode = findViewById(R.id.check_code);
        ibCheckCodePicture = findViewById(R.id.check_code_picture);
        etCheckMsg = findViewById(R.id.check_msg);
        btnCheckMsg = findViewById(R.id.check_msg_button);
        btnRetrieve = findViewById(R.id.retrieve);
    }

    private void initEvent() {

        btnBack.setOnClickListener(View -> {
            setResult(FAILED);
            finish();
        });

        btnRetrieve.setOnClickListener(View -> {

            String phoneNumber = etPhoneNumber.getText().toString().trim();
            String checkCode = etCheckCode.getText().toString().trim();
            String checkMsg = etCheckMsg.getText().toString().trim();

            if (phoneNumber != null && !phoneNumber.equals("")
                    && checkCode != null && !checkCode.equals("")
                    && checkMsg != null && !checkMsg.equals("")){

                if (NetWorkInfoUtils.isConnected(RetrieveActivity.this)){
                    //校验手机号
                    if (RetrieveCheck.checkCode(checkCode)) {
                        if (RetrieveCheck.checkMsg(checkMsg)) {

                            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);

                            if(phoneNumber.equals(sp.getString("phoneNumber", ""))){

                                Toast.makeText(RetrieveActivity.this, "The password is "+sp.getString("password", ""), Toast.LENGTH_LONG)
                                        .show();

                                Intent intent = new Intent();
                                intent.putExtra("phoneNumber", phoneNumber);
                                setResult(SUCCESS, intent);
                                finish();

                            }else {
                                Toast.makeText(RetrieveActivity.this, "No such account!", Toast.LENGTH_LONG)
                                        .show();
                            }

                        }else {
                            Toast.makeText(RetrieveActivity.this, "Worry check-message!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        Toast.makeText(RetrieveActivity.this, "Worry check-code!", Toast.LENGTH_LONG)
                                .show();
                    }
                }else {
                    Toast.makeText(RetrieveActivity.this, "Please check the network connection!", Toast.LENGTH_LONG)
                            .show();
                }
            }else{
                Toast.makeText(RetrieveActivity.this, "The information cannot be blank!", Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}