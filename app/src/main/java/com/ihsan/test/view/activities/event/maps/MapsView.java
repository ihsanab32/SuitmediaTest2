package com.ihsan.test.view.activities.event.maps;

import com.ihsan.test.model.response.EventResponse;

/**
 * Created by
 * Name     : Ihsan Abdurahman
 * Email    : ihsanab31@gmail.com
 * WA       : 085749729115
 * Date     : 11/05/2020.
 * ------------------------------
 * This class for
 */
public interface MapsView {
    void showLoading(String message);

    void dismissLoading();

    void getDataSuccess(EventResponse model);

    void getDataFailed(String message);
}
