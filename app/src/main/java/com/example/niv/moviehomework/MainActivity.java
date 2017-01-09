package com.example.niv.moviehomework;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.niv.moviehomework.Utils.BaseFragment;
import com.example.niv.moviehomework.Utils.ParseJson;
import com.example.niv.moviehomework.Utils.RequestManager;
import com.example.niv.moviehomework.fragments.AdvertiseFragment;
import com.example.niv.moviehomework.fragments.MovieDetailsFragment;
import com.example.niv.moviehomework.fragments.MovieListFragment;
import com.example.niv.moviehomework.fragments.OpenFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OpenFragment.OpenFragmentCallBack,AdvertiseFragment.AdvertiseFragmentCallBack
        ,MovieListFragment.MovieListFragmentCallBack{

    public static JSONObject advertiseJson;
    public static List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // open the loading fragment to get all the data
            OpenFragment openFragment = new OpenFragment();
            pushFragment(openFragment, BaseFragment.PushType.ADD);
        }
    }

    private void pushFragment(BaseFragment fragment,BaseFragment.PushType pushType){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (pushType.equals(BaseFragment.PushType.REPLACE))
            transaction.replace(R.id.fragment_container, fragment);
        if (pushType.equals(BaseFragment.PushType.ADD))
            transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // OpenFragment call back
    @Override
    public void downloadData(List<JSONObject> jsonObjectList) {
        JSONObject json = null;
        JSONObject movieJson = null;
        try {
            json = new JSONObject(jsonObjectList.get(0).toString()); // get the jsonObject name to match the right global jsonObjct
            Object jsonObjectName = json.names().get(0);
            if (jsonObjectName.equals("movies")){
                advertiseJson = jsonObjectList.get(1);
                movieJson = jsonObjectList.get(0);
            }
            else {
                advertiseJson = jsonObjectList.get(0);
                movieJson = jsonObjectList.get(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            movieList = new ParseJson().getListOfMovies(movieJson);
            setMovieRateList(); // after movie list ready push advertiseFragment from the method
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
        }
    }

//    AdvertiseFragment call back
    @Override
    public void skipButtonPressed() {
        MovieListFragment movieListFragment = new MovieListFragment();
        pushFragment(movieListFragment, BaseFragment.PushType.REPLACE);
    }

    // MovieListFragment call back
    @Override
    public void moviePressed(int position) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        movieDetailsFragment.setArguments(args);
        pushFragment(movieDetailsFragment, BaseFragment.PushType.REPLACE);
    }

    private void setMovieRateList(){
        final String[] idListUrl = new String[movieList.size()];

        for (int i=0;i<movieList.size();i++) {
            int id = movieList.get(i).getId();
            String descUrl = "http://x-mode.co.il/exam/descriptionMovies/" + id + ".txt";
            idListUrl[i] = descUrl;
        }
        new RequestManager(this,managerCallBack).jsonObjectRequest(idListUrl);
    }

    RequestManager.RequestManagerCallBack managerCallBack = new RequestManager.RequestManagerCallBack() {
        @Override
        public void JsonArrayRespond(List<JSONArray> jsonArrayList) {

        }

        @Override
        public void JsonObjectRespond(List<JSONObject> jsonObjectList) {
            for (int i=0;i<jsonObjectList.size();i++){
                try {
                    int rate = Integer.parseInt(jsonObjectList.get(i).getString("rate"));
                    int position = (Integer.parseInt(jsonObjectList.get(i).getString("id"))-1000);
                    movieList.get(position).setRate(rate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            AdvertiseFragment advertiseFragment = new AdvertiseFragment();
            pushFragment(advertiseFragment, BaseFragment.PushType.REPLACE);
        }

        @Override
        public void imageRespond(Bitmap image) {

        }
    };
}
