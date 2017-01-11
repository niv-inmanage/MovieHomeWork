package com.example.niv.moviehomework.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.niv.moviehomework.Movie;
import com.example.niv.moviehomework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niv on 1/5/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button categoryButton;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryButton = (Button) itemView.findViewById(R.id.category_button);
            categoryButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String categoryName = b.getText().toString();
            if (callBack!=null)
                callBack.categoryChose(categoryName);
        }
    }

    List<String> categoryList;

    CategoryListAdapterCallBack callBack;
    public interface CategoryListAdapterCallBack{
        void categoryChose(String categoryName);
    }
    public CategoryListAdapter(List<Movie> movieList,CategoryListAdapterCallBack callBack) {
        this.categoryList = getCategoryList(movieList);
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_custom_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==0)
            holder.categoryButton.setText("All");

        int positionForList = (position-1); // custom position to place the "All" button first
        if (positionForList>-1 && positionForList<categoryList.size())
            holder.categoryButton.setText(categoryList.get(positionForList));
    }

    @Override
    public int getItemCount() {
        return categoryList.size()+1;
    }

    private List<String> getCategoryList(List<Movie> movieList){
        List<String> categoryList = new ArrayList<>();
        int i=0;
        for (; i< movieList.size(); i++){ // go through all the category's
            boolean addCategory = true;
            int p=0;
            for (;p<categoryList.size();p++){ // check if category already exist
                String category = categoryList.get(p);
                if (category.equalsIgnoreCase(movieList.get(i).getCategory())){
                    p=categoryList.size();
                    addCategory = false; // category is exist
                }
            }
            if (addCategory)
                categoryList.add(movieList.get(i).getCategory());
        }
        return categoryList;
    }
}
