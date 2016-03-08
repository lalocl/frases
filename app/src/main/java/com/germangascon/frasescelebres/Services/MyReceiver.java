package com.germangascon.frasescelebres.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.germangascon.frasescelebres.MainActivity;

/**
 * Created by usuario on 07/03/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("miAlarma")) {
            Intent service = new Intent(context, AlarmaService.class);
            context.startService(service);
        }
      //  Toast.makeText(context, "Momento Alarma", Toast.LENGTH_LONG).show();
       /* if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.i(TAG, "Iniciando servicio de alarma ...");
            Intent service = new Intent(context, AlarmaService.class);
            context.startService(service);
        }*/

        /*
        if(intent.getAction().equals("WhatEverYouWant")) {
            Toast.makeText(context, "WhatEverYouWant", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Iniciando servicio de WhatEverYouWant ...");
            Intent serviceIntent = new Intent();
            serviceIntent.setAction("com.germangascon.frasescelebres.MyService");
            context.startService(serviceIntent);

            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }*/

        /*
        if(intent.getAction().equals("Alarma")) {
            Toast.makeText(context, "Momento Alarma", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Iniciando servicio de alarma ...");
            Intent serviceIntent = new Intent();
            serviceIntent.setAction("com.germangascon.frasescelebres.MyService");
            context.startService(serviceIntent);

            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
        */



    }
}
