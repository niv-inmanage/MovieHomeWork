package com.example.niv.moviehomework.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.niv.moviehomework.MainActivity;
import com.example.niv.moviehomework.R;
import com.example.niv.moviehomework.Utils.BaseFragment;
import com.example.niv.moviehomework.Utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by niv on 1/5/2017.
 */

public class AdvertiseFragment extends BaseFragment implements View.OnClickListener {

    String imageUrl,video;
    AppCompatImageView background;

    AdvertiseFragmentCallBack callBack;
    public interface AdvertiseFragmentCallBack{
        void skipButtonPressed();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.advertise_fragment_layout, container, false);
        Button skip = (Button) layout.findViewById(R.id.skipButton);
        skip.setOnClickListener(this);
        background = (AppCompatImageView) layout.findViewById(R.id.background);

        getDataFromJson();

        return layout;
    }

    private void getDataFromJson(){
        try {
            JSONArray banner = MainActivity.advertiseJson.getJSONArray("banner");
            JSONObject b = banner.getJSONObject(0);
            imageUrl = b.getString("imageUrl");
            video = b.getString("videoUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new RequestManager(getActivity(),managerCallBack).imageRequest(imageUrl); // get the image from with url
    }

    RequestManager.RequestManagerCallBack managerCallBack = new RequestManager.RequestManagerCallBack() {
        @Override
        public void JsonArrayRespond(List<JSONArray> jsonObjectList) {

        }

        @Override
        public void JsonObjectRespond(List<JSONObject> jsonObjectList) {

        }

        @Override
        public void imageRespond(Bitmap image) {
            final Drawable d = new BitmapDrawable(getResources(), image);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    background.setBackground(d);
                }
            });
        }
    };

    @Override
    public void onClick(View view) {
        if (callBack!=null)
            callBack.skipButtonPressed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBack = (AdvertiseFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
