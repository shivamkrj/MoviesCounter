package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSquareView extends RecyclerView.Adapter<ViewHolderSquare> {

    Context context;
    ViewItemClickListener listener;
    ArrayList<MoviesResult> itemsMovies;
    ArrayList<TvResult> itemsTv;
    int type;

    public AdapterSquareView(Context context, int type, ArrayList<MoviesResult> itemsMovies, ArrayList<TvResult> itemsTv , ViewItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.itemsMovies = itemsMovies;
        this.itemsTv = itemsTv;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolderSquare onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_layout_square,viewGroup,false);
        return new ViewHolderSquare(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSquare viewHolder, int i) {
        if(type==1){//Movie
            MoviesResult item = itemsMovies.get(i);
            viewHolder.movieNameTextView.setText(item.getTitle());
            String imgUrl = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();
            ImageView imageView = viewHolder.imageView;
            Picasso.get().load(imgUrl).fit().into(imageView);
            Picasso.get().load(R.drawable.favorite_unselected_icon).into(viewHolder.checkBox);

        }else if(type==2){//Tv
            TvResult item = itemsTv.get(i);
            viewHolder.movieNameTextView.setText(item.getName());
            String imgUrl = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();
            ImageView imageView = viewHolder.imageView;
            Picasso.get().load(imgUrl).fit().into(imageView);
            Picasso.get().load(R.drawable.favorite_unselected_icon).into(viewHolder.checkBox);

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
                Picasso.get().load(R.drawable.favorite_selected_icon).into(viewHolder.checkBox);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (type==1)
            return itemsMovies.size();
        else
            return itemsTv.size();
    }
}
