package com.ihsan.test.view.activities.main;

import android.app.Activity;
import android.app.AlertDialog;

import com.ihsan.test.view.base.ui.BasePresenter;

/**
 * Created by
 * Name     : Ihsan Abdurahman
 * Email    : ihsanab31@gmail.com
 * WA       : 085749729115
 * Date     : 10/05/2020.
 * ------------------------------
 * This class for
 */
public class MainPresenter extends BasePresenter<MainView> {

    MainPresenter (MainView view){
        super.attachView(view);
    }

     boolean isPalindrome(String str)
    {
        int i = 0, j = str.length() - 1;
        while (i < j) {
            if (str.charAt(i) != str.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    void showDialogBox(String message, String text, Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Result Palindrome");
        dialog.setMessage("\""+text+"\""+" "+message+"");
        dialog.setCancelable(true);
        dialog.setPositiveButton(
                "Yes",
                (dialog12, id) -> {
                });
        dialog.setNegativeButton(
                "No",
                (dialog1, id) -> {
                    dialog1.cancel();
                });

        AlertDialog alert = dialog.create();
        alert.show();
    }
}
