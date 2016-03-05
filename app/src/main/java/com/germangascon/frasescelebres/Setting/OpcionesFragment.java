package com.germangascon.frasescelebres.Setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.germangascon.frasescelebres.R;


public class OpcionesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_opciones);
    }
}
