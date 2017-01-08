package com.example.niv.moviehomework.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niv.moviehomework.MainActivity;
import com.example.niv.moviehomework.Movie;
import com.example.niv.moviehomework.R;
import com.example.niv.moviehomework.Utils.DownloadJsonWithUrl;
import com.example.niv.moviehomework.Utils.ParseJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by niv on 1/5/2017.
 */

public class MovieDetailsFragment extends Fragment {

    TextView nameView,yearView,categoryView,rateView,trailerView,descView;
    AppCompatImageView imageView;

    Movie selectedMovie;
    String imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.movie_details_layout, container, false);
        nameView = (TextView) layout.findViewById(R.id.movie_name);
        yearView = (TextView) layout.findViewById(R.id.movie_year);
        categoryView = (TextView) layout.findViewById(R.id.movie_category);
        rateView = (TextView) layout.findViewById(R.id.movie_rate);
        trailerView = (TextView) layout.findViewById(R.id.movie_trailer);
        trailerView.setMovementMethod(LinkMovementMethod.getInstance());
        descView = (TextView) layout.findViewById(R.id.movie_desc);
        imageView = (AppCompatImageView) layout.findViewById(R.id.movie_image);

        int position = getArguments().getInt("position");

        try {
            selectedMovie = new ParseJson().getMovieFromJsonById(MainActivity.movieJson,position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] descUrl = {"http://x-mode.co.il/exam/descriptionMovies/"+selectedMovie.getId()+".txt"};
        imageUrl = "http://x-mode.co.il/exam/descriptionMovies/"+selectedMovie.getId()+".jpg";

        nameView.setText(selectedMovie.getName());
        yearView.setText("Year:"+" "+selectedMovie.getYear());
        categoryView.setText(selectedMovie.getCategory());

        downLoadImage.start();

        new DownloadJsonWithUrl(getActivity(),descUrl,urlCallBack);

        return layout;
    }
    Thread downLoadImage = new Thread(new Runnable() {
        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Drawable d = new BitmapDrawable(getResources(), bitmap);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageView.setBackground(d);
                }
            });
        }
    });

    DownloadJsonWithUrl.DownloadJsonWithUrlCallBack urlCallBack = new DownloadJsonWithUrl.DownloadJsonWithUrlCallBack() {
        @Override
        public void downloadData(List<JSONObject> jsonObjectList) {
            getDataFromJson(jsonObjectList.get(0));

        }
    };

    private void getDataFromJson(final JSONObject jsonObject){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    descView.setText(jsonObject.getString("description"));
                    trailerView.setText(jsonObject.getString("promoUrl"));
                    rateView.setText("Rate:"+" "+jsonObject.getString("rate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
