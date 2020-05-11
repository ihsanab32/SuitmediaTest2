package com.ihsan.test.view.activities.guest;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ihsan.test.R;
import com.ihsan.test.adapter.GuestAdapter;
import com.ihsan.test.model.GuestItem;
import com.ihsan.test.model.response.GuestResponse;
import com.ihsan.test.view.base.mvp.MvpActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("SetTextI18n")
public class GuestActivity extends MvpActivity<GuestPresenter> implements GuestView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rv_guest)
    RecyclerView rvGuest;
    List<GuestItem> guestItemList = new ArrayList<>();
    GuestAdapter guestAdapter;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    int offset = 0;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @Override
    protected GuestPresenter createPresenter() {
        return new GuestPresenter(this);
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_guest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        scroll();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog(message);
        swRefresh.setRefreshing(false);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void getDataSuccess(GuestResponse model) {
        guestItemList = model.getData();
        guestAdapter.refresh(guestItemList);
        Log.d("Event Activity", String.valueOf(model.getData()));
    }

    @Override
    public void getPagingSuccess(GuestResponse model) {
        guestItemList = model.getData();
        guestAdapter.addMoreGuest(guestItemList);
        isLoading = false;
        Log.d("Event Activity", String.valueOf(model.getData()));
    }

    @Override
    public void getDataFailed(String message) {
        Log.d("Event Activity", message);
    }



    @OnClick({R.id.iv_back, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_next:
                break;
        }
    }

    private void initView() {
        swRefresh.setOnRefreshListener(this);
        txtTitle.setText("GUESTS");
        ivNext.setImageResource(0);
        presenter.getListGuest();
        guestAdapter = new GuestAdapter(this, this, guestItemList);
        rvGuest.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rvGuest.setLayoutManager(gridLayoutManager);
        rvGuest.setAdapter(guestAdapter);
    }

    @Override
    public void onRefresh() {
        clear();
        presenter.getListGuest();
        scroll();
    }

    private void scroll() {
        rvGuest.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    offset = offset + guestItemList.size();
                    presenter.getPaging(offset);
                }
            }
        });
    }

    private void clear(){
        int item = guestItemList.size();
        guestItemList.clear();
        offset = 0;
        guestAdapter.notifyItemRangeRemoved(0, item);
    }
}
