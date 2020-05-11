package com.ihsan.test.view.activities.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ihsan.test.R;
import com.ihsan.test.view.activities.choose.ChooseActivity;
import com.ihsan.test.view.base.mvp.MvpActivity;
import com.libizo.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.et_name)
    CustomEditText etName;
    @BindView(R.id.et_palindrome)
    CustomEditText etPalindrome;


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(this);
    }


    @OnClick({R.id.btn_next, R.id.btn_palindrome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (etName.getText().toString().equals("")) {
                    etName.setError(getResources().getString(R.string.error_null));
                } else if (etPalindrome.getText().toString().equals("")) {
                    etPalindrome.setError(getResources().getString(R.string.error_null));
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChooseActivity.class);
                    intent.putExtra("nama", etName.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.btn_palindrome:
                if (etPalindrome.getText().toString().equals("")) {
                    etPalindrome.setError(getResources().getString(R.string.error_null));
                } else {
                    if (presenter.isPalindrome(etPalindrome.getText().toString())) {
                        presenter.showDialogBox("is Palindrome", etPalindrome.getText().toString(), this);
                    } else {
                        presenter.showDialogBox("not a Palindrome", etPalindrome.getText().toString(), this);
                    }
                }
                break;
        }
    }
}

