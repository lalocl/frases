package com.germangascon.frasescelebres.soap;

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class SoapRequest {
    private static final String TAG = "SoapRequest";
    private String url;
    private String namespace;

    /**
     * Crea un objeto SoapRequest para llamar a funciones de un webservice
     * @param url Dirección web donde se encuentra ubicado el wsdl
     * @param namespace Namespace que figura en el archivo xml (wdsl)
     */
    public SoapRequest(String url, String namespace) {
        this.url = url;
        this.namespace = namespace;
    }

    /**
     * Llamada a procedimiento remoto del Web Service sin parámetros
     * @param method Método a ejecutar por el servicio web
     * @return String que contiene la respuesta en formato JSON
     * @throws IOException
     */
    public String call(String method) throws IOException {
        return this.call(method, null);
    }

    /**
     * Llamada a procedimiento remoto del Web Service
     * @param method Método a ejecutar por el servicio web
     * @param params Parámetros que se le pasan al método
     * @return String que contiene la respuesta en formato JSON
     * @throws IOException
     */
    public String call(String method, Map<String,String> params) throws IOException {
        String resp = null;
        try {

            SoapObject request = new SoapObject(namespace, method);
            //Añadimos los parámetros del método
            if (params != null) {
                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry ent = (Map.Entry) it.next();
                    request.addProperty((String) ent.getKey(), ent.getValue());
                }
            }

            // SoapEnvelop.VER11 is SOAP Version 1.1 constant
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE ht = new HttpTransportSE(url);

            try {
                /** Existe un bug en la librería. Si después de una conexión se realiza otra
                 * intenta reaprovechar la conexión y da una excepción java.io.EOFException
                 * at libcore.io.Streams.readAsciiLine(Streams.java:203).
                 */
                /* @todo En próximas versiones comprobar si sigue ocurriendo para eliminar el Connection close */
                ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

                //Llamamos al webservice
                ht.call(getSoapAction(method), envelope);
            } catch (SoapFault sf) {
                SoapFault error = (SoapFault)envelope.bodyIn;
                Log.e(TAG, "SoapFault Error message : " + error.toString() + " MESSAGE: " + sf.getMessage());
                sf.printStackTrace();
                throw new IOException("Error SOAP FAULT"+ error.toString() +  " MESSAGE: " + sf.getMessage());
            } catch (IOException e) {
                try {
                    /** Lo intentamos de nuevo */
                    ht.call(getSoapAction(method), envelope);
                } catch (IOException e1) {
                    try {
                        /** Lo intentamos por tercera y última vez */
                        ht.call(getSoapAction(method), envelope);
                    } catch (IOException e2) {
                        Log.e(TAG, "Error producido por el bug Issue 173 de la librería Ksoap2");
                        e.printStackTrace();
                        throw new IOException("Error al conectar con el servidor");
                    }
                }
            } catch (XmlPullParserException e) {
                resp = null;
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                throw new IOException("Error al conectar con el servidor");
            } catch (Exception e) {
                resp = null;
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            /** Si el cuerpo del Envelope no es nulo, ¡Tenemos respuesta! */
            if (envelope.bodyIn != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;
                try {
                    resp = result.getProperty(0).toString();
                } catch(Exception e) {
                    resp = null;
                    Log.i(TAG, "Respuesta vacía (null)");
                }

            }
        } catch (Exception e) {
            resp = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            throw new IOException("Error al conectar con el servidor");
        }
        return resp;
    }

    public String getSoapAction(String method) {
        return "\"" + namespace + method + "\"";
    }
}
