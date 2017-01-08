package com.example.niv.moviehomework.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.niv.moviehomework.R;

/**
 * Created by niv on 1/5/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Button categoryButton;

    CategoryViewHolderCallBack callBack;
    public interface CategoryViewHolderCallBack{
        void categoryChose(String category);
    }

    public CategoryViewHolder(View itemView, CategoryViewHolderCallBack callBack) {
        super(itemView);
        this.callBack = callBack;
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
