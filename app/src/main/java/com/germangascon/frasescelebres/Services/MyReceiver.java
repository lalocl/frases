package com.germangascon.frasescelebres.Services;

import android.widget.Toast;

/**
 * Created by usuario on 07/03/2016.
 */
public class MyReceiver extends android.content.BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "Momento Alarma", Toast.LENGTH_LONG).show();
    }
}
