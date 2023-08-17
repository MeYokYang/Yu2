package com.zhly.yu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.zhly.yu2.fragments.AccountFragment;
import com.zhly.yu2.fragments.IndexFragment;
import com.zhly.yu2.fragments.RecommendFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton mBtnRecommend, mBtnIndex, mBtnAccount;
    Fragment[] fragments = new Fragment[3];
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(); //获取三个组件
        initEvent(); //注册监听事件

        showFragment(R.id.btn_index); //默认的fragment
    }

    private void initView() {
        mBtnRecommend = findViewById(R.id.btn_recommend);
        mBtnIndex = findViewById(R.id.btn_index);
        mBtnAccount = findViewById(R.id.btn_account);
    }

    private void initEvent() {
        mBtnRecommend.setOnClickListener(this);
        mBtnIndex.setOnClickListener(this);
        mBtnAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showFragment(view.getId());
    }

    private void showFragment(int id) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null; //应该被显示的fragment，fragments里存放已经添加了的，防止多次重复创建fragment而影响性能。
        switch (id){
            case R.id.btn_index:
                if (fragments[0] != null){
                    fragment = fragments[0];
                }else{
                    fragment = new IndexFragment();
                    fragments[0] = fragment;
                }
                break;
            case R.id.btn_recommend:
                if (fragments[1] != null){
                    fragment = fragments[1];
                }else{
                    fragment = new RecommendFragment();
                    fragments[1] = fragment;
                }
                break;
            case R.id.btn_account:
                if (fragments[2] != null){
                    fragment = fragments[2];
                }else{
                    fragment = new AccountFragment();
                    fragments[2] = fragment;
                }
                break;
        }

        if (mCurrentFragment != null){
            fragmentTransaction.hide(mCurrentFragment); //存在原fragment则隐藏
        }

        if(!fragment.isAdded()){
            fragmentTransaction.add(R.id.content, fragment);
        }else{
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
        mCurrentFragment = fragment;
    }
}