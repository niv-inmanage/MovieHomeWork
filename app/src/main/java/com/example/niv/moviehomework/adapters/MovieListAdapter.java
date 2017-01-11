package com.example.niv.moviehomework.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niv.moviehomework.Movie;
import com.example.niv.moviehomework.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by niv on 1/11/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieName;
        TextView movieYear;
        TextView movieRate;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            movieYear = (TextView) itemView.findViewById(R.id.movie_year);
            movieRate = (TextView) itemView.findViewById(R.id.movie_rate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (callBack!=null)
                callBack.moviePressed(this.getLayoutPosition());
        }
    }

    List<Movie> adapterMovieList = new ArrayList<>();
    List<Movie> arraylist;

    MovieListAdapterCallBack callBack;
    public interface MovieListAdapterCallBack{
        void moviePressed(int position);
    }
    public MovieListAdapter(List<Movie> adapterMovieList,MovieListAdapterCallBack callBack) {
        this.adapterMovieList.addAll(adapterMovieList);
        this.arraylist = new ArrayList<Movie>();
        this.arraylist.addAll(adapterMovieList);
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_custom_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieName.setText(adapterMovieList.get(position).getName());
        holder.movieYear.setText(adapterMovieList.get(position).getYear());
        holder.movieRate.setText("Rate:"+" "+ adapterMovieList.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return adapterMovieList.size();
    }

    // Filter Class
    public void filter(String charText,String category) {
        charText = charText.toLowerCase(Locale.getDefault());
        adapterMovieList.clear();
        if (charText.length() == 0 && category.equals("All")) {
            adapterMovieList.addAll(arraylist);
        }
        else {
            for (Movie m : arraylist) {
                if (m.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        && (m.getCategory().equals(category) || category.equals("All"))) {
                    adapterMovieList.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }
}
