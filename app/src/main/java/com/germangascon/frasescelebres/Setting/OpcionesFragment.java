package com.germangascon.frasescelebres.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.germangascon.frasescelebres.R;


public class OpcionesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG ="OPCIONES FRAGMENT:" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_opciones);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "Escucha de cambios");

        if(key.equals("hora")|| key.equals("min")){
            Toast.makeText(null, "Momento Alarma", Toast.LENGTH_LONG).show();
        }









       /* if(key.equals("log")){



            Preference pref = findPreference(key);
            String acceso= pref.getSharedPreferences().getString("log", "con");
            String usu,pass;
            Log.d(TAG, "Escucha de cambios" + acceso);
            pref.setSummary("prueba");
            if(acceso.equalsIgnoreCase("adm")){
                usu=pref.getSharedPreferences().getString("Usuario","");
                pass=pref.getSharedPreferences().getString("Pass","");

                if(!(usu.equals("frase")&& pass.equals("frase"))){

                    SharedPreferences.Editor editor=pref.getEditor();
                    editor.putString("acceso","con");
                    editor.putString("Usuario","");
                    editor.putString("Pass","");
                    editor.commit();


                }

            }
        }*/


    }

}
