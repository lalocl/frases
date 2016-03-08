package com.germangascon.frasescelebres.Services;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by usuario on 07/03/2016.
 */
public class NotificacionService extends AsyncTask<Void,String,String>{
    private final static String TAG = "NotificacionService";



    @Override
    protected void onPreExecute(){

        Log.i(TAG, "onPreExecute");
    }


    @Override
    protected void onCancelled() {
        Log.i(TAG, "Tarea cancelada");
    }

    @Override
    protected String doInBackground(Void... params) {
      //  AlarmManager manager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);


        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, s);
    }


}
