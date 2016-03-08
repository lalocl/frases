package com.germangascon.frasescelebres.Services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.germangascon.frasescelebres.MainActivity;
import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.activities.ContenidoActivity;
import com.germangascon.frasescelebres.soap.SoapMethod;

/**
 * Created by usuario on 07/03/2016.
 */
public class AlarmaService extends Service{
    private final static String TAG = "AlarmaService";
    private static final int CUSTOM_NOTIFICATION = 1000;
    private Thread thread;
    private final IBinder mBinder = new MyBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return true;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");

    }
    public int onStartCommand(Intent intent,int flags,int startId){

        Log.i(TAG, "onStartCommand");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {


                /** Obtenemos el sonido de notificación por defecto */
                Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AlarmaService.this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap())
                        .setContentTitle("Frase Célebre del día")
                        .setContentText("Nueva Notificación")
                                //.setContentInfo("4")
                                /** Establecemos el tono de notificación por defecto */
                        .setSound(ringtoneUri)
                                /**
                                 * Actualmente existe un bug que hace que el ticker no se muestre correctamente
                                 * Ver: https://code.google.com/p/android-developer-preview/issues/detail?id=60&colspec=ID%20Type%20Status%20Owner%20Summary
                                 */
                        .setTicker("Nueva Frase!!")
                                /** setAutoCancel permite indicar si la notificación será eliminada de la barra de estado al pulsar sobre ella */
                        .setAutoCancel(true);

                Intent intent = new Intent(AlarmaService.this, ContenidoActivity.class);
                intent.putExtra("texto", SoapMethod.GET_FRASE_DEL_DIA);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(CUSTOM_NOTIFICATION, mBuilder.build());
            }
        });

        thread.start();

        return START_STICKY;



    }


    public class MyBinder extends Binder {
        public AlarmaService getService() {
            return AlarmaService.this;
        }
    }
}
