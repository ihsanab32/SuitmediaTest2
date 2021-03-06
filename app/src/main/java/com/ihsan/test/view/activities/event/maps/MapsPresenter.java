package com.ihsan.test.view.activities.event.maps;


import com.ihsan.test.BuildConfig;
import com.ihsan.test.model.response.EventResponse;
import com.ihsan.test.utils.api.NetworkCallback;
import com.ihsan.test.view.base.ui.BasePresenter;

/**
 * Created by
 * Name     : Ihsan Abdurahman
 * Email    : ihsanab31@gmail.com
 * WA       : 085749729115
 * Date     : 11/05/2020.
 * ------------------------------
 * This class for
 */
public class MapsPresenter extends BasePresenter<MapsView> {

    MapsPresenter(MapsView view) {
        super.attachView(view);
    }

    void getListEvent() {
        view.showLoading("Please Wait....");
        addSubscribe(apiStores.getEvent(BuildConfig.API_KEY), new NetworkCallback<EventResponse>() {
            @Override
            public void onSuccess(EventResponse model) {
                view.getDataSuccess(model);
            }

            @Override
            public void onFailure(String message) {
                view.getDataFailed(message);
            }

            @Override
            public void onFinish() {
                view.dismissLoading();
            }
        });

    }
}
