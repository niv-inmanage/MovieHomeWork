package com.example.niv.moviehomework.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.niv.moviehomework.fragments.OpenFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by niv on 1/5/2017.
 */

public class DownloadJsonWithUrl extends AsyncTask<String[], String, List<JSONObject>> {


    private ProgressDialog pDialog;
    Activity activity;

    DownloadJsonWithUrlCallBack callBack;
    public interface DownloadJsonWithUrlCallBack{
        void downloadData(List<JSONObject> jsonObjectList);
    }

    public DownloadJsonWithUrl(Activity activity,String[] url, DownloadJsonWithUrlCallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
        this.execute(url);
    }

    List<JSONObject> listOfJsons = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected List<JSONObject> doInBackground(String[]... url) {
        int i=0;
        for (;i<url[0].length;i++) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url[0][i]);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObj!=null)
                    listOfJsons.add(jsonObj);
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }

        return listOfJsons;
    }

    @Override
    protected void onPostExecute(List<JSONObject> aVoid) {
        super.onPostExecute(aVoid);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        pDialog = null;
        if (callBack!=null)
            callBack.downloadData(listOfJsons);
    }
}