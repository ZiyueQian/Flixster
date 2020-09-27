package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //two pieces of data that will be passed in
    Context context;
    List<Movie> movies;

    //constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //inflate the layout from XML, return the view holder
    @NonNull
    @Override
    //this is more expensive (not showing up once scrolling)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //populate data into the item through the holder
    //relatively cheaper
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //1. get movie passed in position
        Movie movie = movies.get(position);
        //2. bind movie data into the viewholder 
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPoster;
        public TextView tvTitle;
        public TextView tvOverview;


        public ViewHolder(View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            //decide which image based on the orientation of the phone
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageURL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.imagenotfound)
                    .into(ivPoster);
        }
    }
}
