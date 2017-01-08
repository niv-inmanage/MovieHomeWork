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

    public List<Movie> getListOfJsonObjects(JSONObject jsonObject) throws JSONException {
        // Getting JSON Array node
        JSONArray movies = jsonObject.getJSONArray("movies");

        List<Movie> movieList = new ArrayList<>();
        // looping through All Contacts
        for (int i = 0; i < movies.length(); i++) {
            JSONObject m = movies.getJSONObject(i);

            int id = m.getInt("id");
            String name = m.getString("name");
            String year = m.getString("year");
            String category = m.getString("category");
//            int rate = m.getInt("rate");

            Movie movie = new Movie(0, null, null, null,0);

            movie.setId(id);
            movie.setName(name);
            movie.setYear(year);
            movie.setCategory(category);
//            movie.setRate(rate);

            // adding contact to contact list
            movieList.add(movie);
        }
        return movieList;
    }
    public Movie getMovieFromJsonById(JSONObject jsonObject, int id) throws JSONException {
        // Getting JSON Array node
        JSONArray movies = jsonObject.getJSONArray("movies");

        for (int i = 0; i < movies.length(); i++) {
            JSONObject m = movies.getJSONObject(i);
            if (m.getInt("id")== id){
                int movieId = m.getInt("id");
                String name = m.getString("name");
                String year = m.getString("year");
                String category = m.getString("category");
//                int rate = m.getInt("rate");

                Movie movie = new Movie(0, null, null, null,0);

                movie.setId(movieId);
                movie.setName(name);
                movie.setYear(year);
                movie.setCategory(category);
//                movie.setRate(rate);

                return movie;
            }
        }
        return null;
    }
}
