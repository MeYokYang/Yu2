package com.zhly.yu2.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhly.yu2.LoginActivity;
import com.zhly.yu2.MainActivity;
import com.zhly.yu2.R;


public class AccountFragment extends Fragment implements View.OnClickListener{

    TextView tvUsername;
    EditText etPS;
    Button btnLogOut;
    LinearLayout llAccount;

    public AccountFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initView(view);
        initEvent();

        return view;
    }

    private void initView(View view) {
        tvUsername = view.findViewById(R.id.username);
        etPS = view.findViewById(R.id.PS);
        btnLogOut = view.findViewById(R.id.log_out);
        llAccount = view.findViewById(R.id.account);

        SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        String username = sp.getString("username", "");
        if (username != null && username.length() > 20) {
            username = username.substring(0, 14)+"...";
        }
        String ps = sp.getString("PS", "");
        tvUsername.setText(username);
        etPS.setText(ps);
    }

    private void initEvent() {
        llAccount.setOnClickListener(this);
        btnLogOut.setOnClickListener(view -> {
            savePS();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.PS:
                break;
            default:
                savePS();
                break;
        }
    }

    private void savePS() {
        SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PS", etPS.getText().toString());
        editor.commit();
    }
}