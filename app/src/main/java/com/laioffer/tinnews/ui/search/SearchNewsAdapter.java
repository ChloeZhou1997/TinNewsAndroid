package com.laioffer.tinnews.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    // 1. Supporting data:
    // TODO
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        SearchNewsViewHolder searchNewsViewHolder = new SearchNewsViewHolder(view);
        Log.d("Tim", searchNewsViewHolder.toString() + " createViewHolder");
        return searchNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
       Article article = articles.get(position);
       Log.d("Tim", holder.toString() + " onBindViewHolder");
       holder.itemTitleTextView.setText(article.title);
       if (article.urlToImage != null) {
           Picasso.get().load(article.urlToImage).resize(200, 200).into(holder.itemImageView);
       }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 2. SearchNewsViewHolder:
    // TODO
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.search_item_image);
            itemTitleTextView =  itemView.findViewById(R.id.search_item_title);
        }

    }

    // 3. Adapter overrides:
    // TODO
}
