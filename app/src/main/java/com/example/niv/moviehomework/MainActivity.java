package com.example.niv.moviehomework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.niv.moviehomework.fragments.AdvertiseFragment;
import com.example.niv.moviehomework.fragments.MovieDetailsFragment;
import com.example.niv.moviehomework.fragments.MovieListFragment;
import com.example.niv.moviehomework.fragments.OpenFragment;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OpenFragment.OpenFragmentCallBack,AdvertiseFragment.AdvertiseFragmentCallBack
        ,MovieListFragment.MovieListFragmentCallBack{

    public static JSONObject movieJson,advertiseJson;

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
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, openFragment).commit();
        }
    }

    private void pushFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    // OpenFragment call back
    @Override
    public void downloadData(List<JSONObject> jsonObjectList) {
        if (jsonObjectList.size()>0) {
            movieJson = jsonObjectList.get(1);
            advertiseJson = jsonObjectList.get(0);
        }

        AdvertiseFragment advertiseFragment = new AdvertiseFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, advertiseFragment);
        transaction.commitAllowingStateLoss();
    }

//    AdvertiseFragment call back
    @Override
    public void skipButtonPressed() {
        MovieListFragment movieListFragment = new MovieListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, movieListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // MovieListFragment call back
    @Override
    public void moviePressed(int position) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        movieDetailsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, movieDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
