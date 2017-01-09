package com.example.niv.moviehomework.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niv on 1/8/2017.
 */

public class RequestManager {

    Activity activity;
    RequestQueue queue;

    RequestManagerCallBack callBack;
    public interface RequestManagerCallBack{
        void JsonArrayRespond(List<JSONArray> jsonObjectList);
        void JsonObjectRespond(List<JSONObject> jsonObjectList);
        void imageRespond(Bitmap image);
    }

    public RequestManager(Activity activity,RequestManagerCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
        queue = Volley.newRequestQueue(activity);
    }

   public void jsonArrayRequest(final String[] url){
        final List<JSONArray> objectList = new ArrayList<>();

        final ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");
        pDialog.show();

       final int[] respondCount = new int[1];
        for (int i=0;i<url.length;i++) {
            JsonArrayRequest jsonRequest = new JsonArrayRequest
                    (Request.Method.GET, url[i], null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            objectList.add(response);
                            respondCount[0]++;
                            pDialog.hide();
                            if (respondCount[0] ==url.length) //check if download all the url data
                                if (callBack != null)
                                    callBack.JsonArrayRespond(objectList);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity,error.toString(), Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    });
            queue.add(jsonRequest);
        }
    }
    public void jsonObjectRequest(final String[] url){
        final List<JSONObject> objectList = new ArrayList<>();

        final ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");
        pDialog.show();

        final int[] respondCount = new int[1];
        for (int i=0;i<url.length;i++) { // send request for each Url
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url[i], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            objectList.add(response);
                            respondCount[0]++;
                            pDialog.hide();
                            if (respondCount[0] ==url.length) //check if download all the url data
                                if (callBack != null)
                                    callBack.JsonObjectRespond(objectList);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity,error.toString(), Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    });
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonRequest);
        }
    }
    public void imageRequest(String imageUrl){
        ImageRequest imgRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if (callBack != null)
                            callBack.imageRespond(response);
                    }
                }, 0, 0,null, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(imgRequest);
    }
}
