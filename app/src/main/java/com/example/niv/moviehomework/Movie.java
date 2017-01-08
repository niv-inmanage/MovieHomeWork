package com.example.niv.moviehomework;

/**
 * Created by niv on 1/5/2017.
 */

public class Movie {
    int id;
    String name;
    String year;
    String category;
    int rate;

    public Movie(int id, String name, String year, String category,int rate) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.category = category;
        this.rate = rate;
    }

    public enum sortType{
        NAME,
        YEAR
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public int getRate() {
        return rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
