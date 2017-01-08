package com.example.niv.moviehomework.adapters;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.niv.moviehomework.MainActivity;
        import com.example.niv.moviehomework.Movie;
        import com.example.niv.moviehomework.R;
        import com.example.niv.moviehomework.Utils.DownloadJsonWithUrl;
        import com.example.niv.moviehomework.Utils.ParseJson;

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

public class MovieListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Movie> movieList;
    List<Movie> arraylist;

    public MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.arraylist = new ArrayList<Movie>();
        this.arraylist.addAll(movieList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
        TextView movieName;
        TextView movieYear;
        TextView movieRate;
    }
    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        Holder holder=new Holder();
        rowView = inflater.inflate(R.layout.movie_list_custom_row, null);
        holder.movieName=(TextView) rowView.findViewById(R.id.movie_name);
        holder.movieYear=(TextView) rowView.findViewById(R.id.movie_year);
        holder.movieRate=(TextView) rowView.findViewById(R.id.movie_rate);

        holder.movieName.setText(movieList.get(i).getName());
        holder.movieYear.setText(movieList.get(i).getYear());

//        holder.movieRate.setText(movieList.get(i).getRate());
        return rowView;
    }

    // Filter Class
    public void filter(String charText,String category) {
        charText = charText.toLowerCase(Locale.getDefault());
        movieList.clear();
        if (charText.length() == 0 && category.equals("All")) {
            movieList.addAll(arraylist);
        }
        else {
            for (Movie m : arraylist) {
                if (m.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        && (m.getCategory().equals(category) || category.equals("All"))) {
                    movieList.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }
}
