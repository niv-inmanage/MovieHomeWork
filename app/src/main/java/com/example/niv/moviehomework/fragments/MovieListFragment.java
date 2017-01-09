package com.example.niv.moviehomework.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.niv.moviehomework.MainActivity;
import com.example.niv.moviehomework.Movie;
import com.example.niv.moviehomework.R;
import com.example.niv.moviehomework.Utils.BaseFragment;
import com.example.niv.moviehomework.Utils.ParseJson;
import com.example.niv.moviehomework.Utils.RequestManager;
import com.example.niv.moviehomework.adapters.CategoryListAdapter;
import com.example.niv.moviehomework.adapters.MovieListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by niv on 1/5/2017.
 */

public class MovieListFragment extends BaseFragment implements View.OnClickListener, TextWatcher, View.OnTouchListener, AdapterView.OnItemClickListener {

    ListView movieListView;
    RecyclerView categoryList;
    Button sortButton;
    EditText searchMovieView;
    DrawerLayout drawerLayout;
    ListView drawerList;
    android.support.v7.app.ActionBarDrawerToggle drawerToggle;

    List<Movie> movieList = new ArrayList<>();
    private String[] mNavigationDrawerItemTitles;
    MovieListAdapter movieListAdapter;
    String searchInput = "",chosenCategory = "All";

    MovieListFragmentCallBack callBack;
    public interface MovieListFragmentCallBack{
        void moviePressed(int position);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.movie_list_fragment_layout, container, false);

        this.movieList.addAll(MainActivity.movieList);
        drawerLayout = (DrawerLayout) layout.findViewById(R.id.drawer_layout);
        drawerList = (ListView) layout.findViewById(R.id.left_drawer);
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.left_drawer_layout,mNavigationDrawerItemTitles);
        drawerList.setAdapter(adapter);
        drawerLayout.setDrawerListener(drawerToggle);
        setupDrawerToggle();
        drawerList.setOnItemClickListener(this);

        sortButton = (Button) layout.findViewById(R.id.sort_button);
        sortButton.setOnClickListener(this);

        searchMovieView = (EditText) layout.findViewById(R.id.search_edit_text);
        searchMovieView.addTextChangedListener(this);
        searchMovieView.setOnTouchListener(this);

        categoryList = (RecyclerView) layout.findViewById(R.id.category_list);
        setCategoryListAdapter();

        movieListView = (ListView) layout.findViewById(R.id.movie_list);
        sortListBy(Movie.sortType.YEAR);
        movieListAdapter = new MovieListAdapter(getContext(),movieList);
        movieListView.setAdapter(movieListAdapter);
        movieListView.setOnItemClickListener(this);
        return layout;
    }

    void setupDrawerToggle(){
        drawerToggle = new android.support.v7.app.ActionBarDrawerToggle(getActivity(),drawerLayout,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.movie_list:
                if (callBack!=null)
                    callBack.moviePressed(movieList.get(position).getId());
                break;
            case R.id.left_drawer:
                switch (position){
                    case 0:
                        sortListBy(Movie.sortType.NAME);
                        break;
                    case 1:
                        sortListBy(Movie.sortType.YEAR);
                        break;
                }
//                List<Movie> tempMovieList = new ArrayList<>();
                movieListAdapter = new MovieListAdapter(getContext(),movieList);
                movieListAdapter.filter(searchInput,chosenCategory);
                movieListView.setAdapter(movieListAdapter);

                drawerLayout.closeDrawer(drawerList);
                break;
        }
    }

    private void sortListBy(final Movie.sortType sortType){
        Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie t1) {
                if (sortType.equals(Movie.sortType.NAME))
                    return movie.getName().compareToIgnoreCase(t1.getName());
                if (sortType.equals(Movie.sortType.YEAR))
                    return movie.getYear().compareToIgnoreCase(t1.getYear());
                return 0;
            }
        });
    }

    private void setCategoryListAdapter(){
        categoryList.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (movieList.size() > 0 & categoryList != null)
            categoryList.setAdapter(new CategoryListAdapter(movieList,adapterCallBack));
        categoryList.setLayoutManager(MyLayoutManager);
    }
    CategoryListAdapter.CategoryListAdapterCallBack adapterCallBack = new CategoryListAdapter.CategoryListAdapterCallBack() {
        @Override
        public void categoryChose(String categoryName) {
            chosenCategory = categoryName;
            movieListAdapter.filter(searchInput,chosenCategory);
        }
    };

    @Override
    public void onClick(View view) {
        drawerLayout.openDrawer(drawerList);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        searchInput = searchMovieView.getText().toString().toLowerCase(Locale.getDefault());
        movieListAdapter.filter(searchInput,chosenCategory);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (searchMovieView.getRight() - searchMovieView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                searchMovieView.getText().clear();
                return true;
            }
        }
        return false;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBack = (MovieListFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
