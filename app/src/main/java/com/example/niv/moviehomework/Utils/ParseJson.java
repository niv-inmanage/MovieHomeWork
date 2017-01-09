package com.example.niv.moviehomework.Utils;

import com.example.niv.moviehomework.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by niv on 1/5/2017.
 */

public class ParseJson {

    public List<Movie> getListOfMovies(JSONObject jsonObject) throws JSONException {
        String name = null;
        String year=null;
        String category=null;

        // Getting JSON Array node
        JSONArray movies = null;

        movies = jsonObject.getJSONArray("movies");

        List<Movie> movieList = new ArrayList<>();
        // looping through All Contacts
        for (int i = 0; i < movies.length(); i++) {
            JSONObject m = null;
            try {
                m = movies.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int id = 0;
            try {
                id = m.getInt("id");
                name = m.getString("name");
                year = m.getString("year");
                category = m.getString("category");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Movie movie = new Movie(0, null, null, null,0);

            movie.setId(id);
            movie.setName(name);
            movie.setYear(year);
            movie.setCategory(category);

            // adding contact to contact list
            movieList.add(movie);
        }
        return movieList;
    }
}
