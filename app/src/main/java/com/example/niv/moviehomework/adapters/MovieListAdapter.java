package com.example.niv.moviehomework.adapters;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.niv.moviehomework.Movie;
        import com.example.niv.moviehomework.R;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by niv on 1/5/2017.
 */

public class MovieListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Movie> adapterMovieList = new ArrayList<>();
    List<Movie> arraylist;

    public MovieListAdapter(Context context, List<Movie> adapterMovieList) {
        this.context = context;
        this.adapterMovieList.addAll(adapterMovieList);
        this.arraylist = new ArrayList<Movie>();
        this.arraylist.addAll(adapterMovieList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adapterMovieList.size();
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

        holder.movieName.setText(adapterMovieList.get(i).getName());
        holder.movieYear.setText(adapterMovieList.get(i).getYear());
        holder.movieRate.setText("Rate:"+" "+ adapterMovieList.get(i).getRate());

        return rowView;
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
