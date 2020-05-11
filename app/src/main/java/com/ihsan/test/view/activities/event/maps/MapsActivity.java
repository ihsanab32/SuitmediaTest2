package com.ihsan.test.view.activities.event.maps;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ihsan.test.R;
import com.ihsan.test.adapter.EventAdapter;
import com.ihsan.test.model.EventItem;
import com.ihsan.test.model.response.EventResponse;
import com.ihsan.test.view.base.mvp.MvpActivity;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"SetTextI18n","LogNotTimber"})
public class MapsActivity extends MvpActivity<MapsPresenter> implements MapsView {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.event_maps)
    RecyclerView eventMaps;
    @BindView(R.id.mapView)
    MapView mapView;
    List<EventItem> eventItemList = new ArrayList<>();
    EventAdapter eventAdapter;

    @Override
    protected MapsPresenter createPresenter() {
        return new MapsPresenter(this);
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_maps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog(message);
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

                break;
        }
    }

    private void initView(){
        txtTitle.setText("EVENTS");
        ivNext.setImageResource(R.drawable.ic_list_view);
        mapView.getMapAsync(mapboxMap -> {
            mapboxMap.setCameraPosition(new CameraPosition.Builder()
                    .target(new LatLng(-6.889220, 107.624238)).zoom(17)
                    .build());
            mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            });
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-6.889220, 107.624238))
                    .title("Lailatul Koding")
                    .snippet("Jln. Intan IV No 49")
            );
        });
        presenter.getListEvent();
        eventAdapter = new EventAdapter(this, this, eventItemList);
        eventMaps.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        eventMaps.setLayoutManager(layoutManager);
        eventMaps.setAdapter(eventAdapter);
    }

}
