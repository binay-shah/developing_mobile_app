package com.example.moviemanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviemanager.R;
import com.example.moviemanager.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    List<Movie> mMovies;
    Context mContext;
    LayoutInflater mInflater;

    public MovieRecyclerViewAdapter(List<Movie> movies, Context context) {
        this.mMovies = movies;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie movie = mMovies.get(position);

        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieOverView.setText(movie.getOverview());

        Log.d("image", movie.getPosterPath());

        Picasso.get()
                .load(movie.getPosterPath())
                .error(R.mipmap.ic_launcher)
       // .resize(50, 50)
            //    .centerCrop()
                .into(viewHolder.movieImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso",""+e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView movieImage;
        public TextView movieTitle;
        public TextView movieOverView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.ivMovieImage);
            movieTitle = itemView.findViewById(R.id.tvTitle);
            movieOverView = itemView.findViewById(R.id.tvOverview);
        }
    }
}
