package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRectangularView extends RecyclerView.Adapter<ViewHolderRectangular> {

    Context context;
    ViewItemClickListener listener;
    ArrayList<UpcomingResult> itemsMovies;
    ArrayList<TvResult> itemsTv;
    int type;

    public AdapterRectangularView(Context context, int type, ArrayList<UpcomingResult> itemsMovies,ArrayList<TvResult> itemsTv, ViewItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.itemsMovies = itemsMovies;
        this.itemsTv = itemsTv;
        this.type = type;
    }


    @NonNull
    @Override
    public ViewHolderRectangular onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_layout_rectangular,viewGroup,false);
        return new ViewHolderRectangular(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderRectangular viewHolder, int i) {
        if(type==1){//Movies
            UpcomingResult item = itemsMovies.get(i);
            viewHolder.movieNameTextView.setText(item.getTitle());
            viewHolder.ratingTextView.setText(item.getVoteAverage()+"");
            TextView genre =viewHolder.genreTextView;
            List<Long> genreList = item.getGenreIds();
//
//        for(int j =0 ; j < genreList.size();j++){
//            genre.setText(" "+genreList.get(j)+", ");
//        }
            String imgUrl = "https://image.tmdb.org/t/p/w780"+item.getBackdropPath();
            // Log.d("onresponse",item.getPosterPath()+" title");
            ImageView imageView = viewHolder.imageView;
            Picasso.get().load(imgUrl).resize(320,200).into(imageView);
        }else if(type==2){//Tv
            TvResult item = itemsTv.get(i);
            viewHolder.movieNameTextView.setText(item.getName());
            viewHolder.ratingTextView.setText(item.getVoteAverage()+"");
            String imgUrl = "https://image.tmdb.org/t/p/w780"+item.getBackdropPath();
            ImageView imageView = viewHolder.imageView;
            Picasso.get().load(imgUrl).resize(320,200).into(imageView);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(viewHolder.itemView,viewHolder.getAdapterPosition());
            }
        });
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Favorite
            }
        });
    }

    @Override
    public int getItemCount() {
        if(type==1){
            return itemsMovies.size();
        }else
            return itemsTv.size();
    }
}
