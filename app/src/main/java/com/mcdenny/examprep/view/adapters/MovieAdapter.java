package com.mcdenny.examprep.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.utils.Constants;
import com.mcdenny.examprep.view.activity.MovieDetailActivity;
import com.mcdenny.examprep.view.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import static com.mcdenny.examprep.utils.Constants.IMAGE_URL_BASE_PATH;

/** this adapter displays coupon items in recycler view
 *  it extends PagedListAdapter which gets data from PagedList
 *  and displays in recycler view as data is available in PagedList
 */

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder>{
    private static final String TAG = "MovieAdapter";


    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem == newItem;
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }




    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView movie_title;
        private Context context;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_title = itemView.findViewById(R.id.tv_movie_title);
            context = itemView.getContext();
        }

        public void bind(Movie movie) {
            movie_title.setText(movie.getTitle());
            itemView.setOnClickListener(v -> {
//                Intent detailIntent = new Intent(context, MovieDetailActivity.class);
//                String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
//                Log.d(TAG, "Image poster Url: "+image_url);
//
//                //Extract bitmap
//                Picasso.get()
//                        .load(image_url)
//                        .resize(100, 200)
//                        .into(target);
//
//                Constants.selected_movie_poster_url = image_url;
//                Constants.selected_movie_id = movie.getId();
//                detailIntent.putExtra("movie_id", movie.getId());
//                Log.d(TAG, "onBindViewHolder - Movie id: "+movie.getId());
//                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(detailIntent);
            });
        }
    }

    //Getting the drawable from the picasso
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.d(TAG, "onBitmapLoaded: Bitmap from Picasso: "+bitmap);
            Constants.selected_movie_bimap = bitmap;
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            Log.e(TAG, "onBitmapFailed: ",e );
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

}
