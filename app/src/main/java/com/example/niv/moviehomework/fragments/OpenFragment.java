package com.example.niv.moviehomework.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.niv.moviehomework.R;
import com.example.niv.moviehomework.Utils.BaseFragment;
import com.example.niv.moviehomework.Utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by niv on 1/5/2017.
 */

public class OpenFragment extends BaseFragment {

    String[] urls = {"http://x-mode.co.il/exam/allMovies/allMovies.txt",
            "http://x-mode.co.il/exam/allMovies/generalDeclaration.txt"};


    OpenFragmentCallBack callBack;
    public interface OpenFragmentCallBack{
        void downloadData(List<JSONObject> jsonObjectList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.open_fragment_layout, container, false);

        new RequestManager(getActivity(),managerCallBack).jsonObjectRequest(urls);
        return layout;
    }

    RequestManager.RequestManagerCallBack managerCallBack = new RequestManager.RequestManagerCallBack() {
        @Override
        public void JsonArrayRespond(List<JSONArray> jsonObjectList) {

        }

        @Override
        public void JsonObjectRespond(List<JSONObject> jsonObjectList) {
            if (jsonObjectList!=null) {
                if (callBack != null)
                    callBack.downloadData(jsonObjectList);
            } else Toast.makeText(getContext(),"Can't connect to server",Toast.LENGTH_LONG).show();
        }

        @Override
        public void imageRespond(Bitmap image) {

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
