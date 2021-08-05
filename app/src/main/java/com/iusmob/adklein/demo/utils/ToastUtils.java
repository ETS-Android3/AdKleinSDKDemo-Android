package com.iusmob.adklein.demo.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class ToastUtils {

    public static void toast(final Activity activity, final String content, final int duration) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, content, duration).show();
                Log.i(activity.getClass().getSimpleName(), content);
            }
        });
    }

}
