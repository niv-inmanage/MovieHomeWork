package com.example.niv.moviehomework.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.niv.moviehomework.R;
import com.example.niv.moviehomework.Utils.DownloadJsonWithUrl;
import com.example.niv.moviehomework.Utils.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by niv on 1/5/2017.
 */

public class OpenFragment extends Fragment {

    String[] urls = {"http://x-mode.co.il/exam/allMovies/allMovies.txt",
            "http://x-mode.co.il/exam/allMovies/generalDeclaration.txt"};

    private ProgressDialog pDialog;

    OpenFragmentCallBack callBack;
    public interface OpenFragmentCallBack{
        void downloadData(List<JSONObject> jsonObjectList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.open_fragment_layout, container, false);

        new DownloadJsonWithUrl(getActivity(),urls,urlCallBack);
        return layout;
    }
    DownloadJsonWithUrl.DownloadJsonWithUrlCallBack urlCallBack = new DownloadJsonWithUrl.DownloadJsonWithUrlCallBack() {
        @Override
        public void downloadData(List<JSONObject> jsonObjectList) {
            if (callBack!=null)
                callBack.downloadData(jsonObjectList);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBack = (OpenFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
