package com.ihsan.test.view.activities.event;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ihsan.test.R;
import com.ihsan.test.adapter.EventAdapter;
import com.ihsan.test.model.EventItem;
import com.ihsan.test.model.response.EventResponse;
import com.ihsan.test.view.activities.event.maps.MapsActivity;
import com.ihsan.test.view.base.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EventActivity extends MvpActivity<EventPresenter> implements EventView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rv_event)
    RecyclerView rvEvent;
    List<EventItem> eventItemList = new ArrayList<>();
    EventAdapter eventAdapter;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;

    @Override
    protected EventPresenter createPresenter() {
        return new EventPresenter(this);
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
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
    public void getDataSuccess(EventResponse model) {
        eventItemList = model.getData();
        eventAdapter.refresh(eventItemList);
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
                startActivity(MapsActivity.class);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        swRefresh.setOnRefreshListener(this);
        presenter.getListEvent();
        txtTitle.setText("EVENTS");
        eventAdapter = new EventAdapter(this, this, eventItemList);
        rvEvent.setHasFixedSize(true);
        rvEvent.setLayoutManager(new LinearLayoutManager(this));
        rvEvent.setAdapter(eventAdapter);
    }

    @Override
    public void onRefresh() {
        presenter.getListEvent();
    }
}
