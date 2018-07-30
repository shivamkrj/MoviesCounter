package in.skr.shivamkumar.moviescounter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSquareView extends RecyclerView.Adapter<ViewHolderSquare> {

    Context context;
    ViewItemClickListener listener;
    ArrayList<MoviesResult> itemsMovies;
    ArrayList<TvResult> itemsTv;
    int type;
    private boolean isFavouriteFlag;


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
    public void onBindViewHolder(@NonNull final ViewHolderSquare viewHolder, final int i) {
        if(type==1){//Movie
            MoviesResult item = itemsMovies.get(viewHolder.getAdapterPosition());
            viewHolder.movieNameTextView.setText(item.getTitle());
            String imgUrl = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();
            ImageView imageView = viewHolder.imageView;
            Picasso.get().load(imgUrl).fit().into(imageView);

            //for favourite button
            FavouriteDatabase database = Room.databaseBuilder(viewHolder.itemView.getContext(),FavouriteDatabase.class,"favourite_db").allowMainThreadQueries().build();
            FavouriteDAO favouriteDAO = database.getFavouriteDao();
            List<Favourite> savedFavouriteList = favouriteDAO.getFavourites();
            isFavouriteFlag = false;
            for(Favourite f : savedFavouriteList){
                if(f.getFavouriteMovie() == item.getId()){
                    isFavouriteFlag = true;
                    break;
                }
            }
            if(isFavouriteFlag)
                Picasso.get().load(R.drawable.favorite_selected_icon).into(viewHolder.checkBox);
            else
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
                if (isFavouriteFlag){
                    Picasso.get().load(R.drawable.favorite_unselected_icon).into(viewHolder.checkBox);
                    isFavouriteFlag = false;
                    Favourite favourite = new Favourite();
                    if(type == 1)
                        favourite.setFavouriteMovie(itemsMovies.get(i).getId());
                    else
                        favourite.setFavouriteMovie(itemsTv.get(i).getId());

                    FavouriteDatabase database = Room.databaseBuilder(view.getContext(),FavouriteDatabase.class,"favourite_db").allowMainThreadQueries().build();
                    FavouriteDAO favouriteDAO = database.getFavouriteDao();
                    List<Favourite> f = favouriteDAO.getFavourites();
                    for(Favourite ff : f) {
                        if (type == 1) {
                            if (ff.getFavouriteMovie() == favourite.getFavouriteMovie()) {
                                favourite.setId(ff.getId());
                                long ppp = ff.getId();
                                long ttt = favourite.getFavouriteMovie();
                                break;
                            }
                        } else {
                            if (ff.getFavouriteTV() == favourite.getFavouriteTV()) {
                                favourite.setId(ff.getId());
                                break;
                            }
                        }
                    }
                    int r = favouriteDAO.deleteFavourite(favourite);
                    Log.i("Adapter","Rows Affected = "+r);
                    int d = favouriteDAO.getTotal();
                    Log.i("Adapter","Totoal Items in Database = "+d);

                }else{
                    isFavouriteFlag = true;
                    Picasso.get().load(R.drawable.favorite_selected_icon).into(viewHolder.checkBox);
                    Favourite favourite = new Favourite();
                    if(type == 1)
                        favourite.setFavouriteMovie(itemsMovies.get(i).getId());
                    else
                        favourite.setFavouriteMovie(itemsTv.get(i).getId());

                    FavouriteDatabase database = Room.databaseBuilder(view.getContext(),FavouriteDatabase.class,"favourite_db").allowMainThreadQueries().build();
                    FavouriteDAO favouriteDAO = database.getFavouriteDao();
                    long idd = favouriteDAO.addFavourite(favourite);
                }

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
