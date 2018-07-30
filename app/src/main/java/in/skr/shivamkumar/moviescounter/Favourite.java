package in.skr.shivamkumar.moviescounter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourite")
public class Favourite {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long FavouriteTV;
    private long FavouriteMovie;
    private long FavouriteCast;

    public long getFavouriteTV() {
        return FavouriteTV;
    }

    public void setFavouriteTV(long favouriteTV) {
        FavouriteTV = favouriteTV;
    }

    public long getFavouriteMovie() {
        return FavouriteMovie;
    }

    public void setFavouriteMovie(long favouriteMovie) {
        FavouriteMovie = favouriteMovie;
    }

    public long getFavouriteCast() {
        return FavouriteCast;
    }

    public void setFavouriteCast(long favouriteCast) {
        FavouriteCast = favouriteCast;
    }
}
