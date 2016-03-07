package com.germangascon.frasescelebres.soap;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.germangascon.frasescelebres.interfaces.JsonResult;
import com.germangascon.frasescelebres.interfaces.Resultado;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2016 See AUTHORS file.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * FrasesCelebres
 *
 * @author Germán Gascón
 * @version 0.1, 2016-02-18
 * @since 0.1
 **/

public class FrasesCelebresTask extends AsyncTask<SoapParam, String, String> {
    private final static String TAG = "FrasesCelebresTask";
    private TextView textView;
    private String method;
    private Resultado resultado;
    private SharedPreferences prefs;


    public FrasesCelebresTask(Resultado resultado,SharedPreferences prefs) {
        this.resultado = resultado;
        this.prefs=prefs;

    }

    @Override
    protected String doInBackground(SoapParam... params) {



        String ip= prefs.getString("ipServidor","192.168.1.36");
        String puerto= prefs.getString("puertoServidor","9090");
        String servicioWeb= prefs.getString("nombreSW","frasesCelebresWS?wsdl");
        String namespace=prefs.getString("nameSpaceSW","http://frasescelebresws.germangascon.com/");



        String url = "http://" +ip + ":"+puerto+"/"+servicioWeb;

       // String url = "http://192.168.1.36:9090/frasesCelebresWS?wsdl";
       // String namespace = "http://frasescelebresws.germangascon.com/";
        String result = null;
       // method = "getFraseDelDia";
        Map parametros = new HashMap<String, String>();
        SoapRequest sr = new SoapRequest(url, namespace);

        if(params != null && params.length > 0) {
            method = params[0].getMethod();
            parametros = params[0].getParams();
        }

        try {
            if(parametros.size()>0) {
                result = sr.call(method, parametros);
            } else {
                result = sr.call(method);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al conectar con el Web Service");
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        resultado.onResult(s);
    }
}
