package com.localtrip.jejulocaltrip.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by hong on 2016. 7. 11..
 */

/**
 * Created by Administrator on 2015-09-23.
 */
public class HttpConnector {

    private static JSONObject jObj = null;
    private static String json = "";

    public static JSONObject callGETMethod(String url) {

        try {
            URL jsonURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) jsonURL.openConnection();

            if (urlConnection != null) {
                urlConnection.setConnectTimeout(10000);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("GET");

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    json = sb.toString();
                }
                urlConnection.disconnect();
            }

        } catch (IOException e) {
            Log.e("JSON Parser", "Error parsing data" +e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }

    public static JSONObject callPOSTMethod(String url, HashMap<String, String> entry) {

        try {
            URL jsonURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) jsonURL.openConnection();

            if(urlConnection != null) {
                urlConnection.setConnectTimeout(10000);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                OutputStreamWriter outStream = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(getPostDataString(entry));
                writer.flush();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    json = sb.toString();
                }
                urlConnection.disconnect();
            }

        } catch (IOException e) {
            Log.e("JSON Parser", "Error parsing data" +e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



}