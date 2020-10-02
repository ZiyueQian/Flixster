package com.example.flixster.adapters;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        public RelativeLayout container;


        public ViewHolder(View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            String imageURL;
            //decide which image based on the orientation of the phone
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageURL)
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
            //1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener(){
                //2. Navigate to a new activity on tap
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(context,movie.getTitle(), Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(context, DetailActivity.class);
//                    i.putExtra("movie", Parcels.wrap(movie)); //use parcelable so android studio can break down our model
//                    context.startActivity(i);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    Pair<View, String> p1 = Pair.create((View)tvOverview, "overview");
                    Pair<View, String> p2 = Pair.create((View)tvTitle, "title");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)context, p1, p2);
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }
}
