package in.skr.shivamkumar.moviescounter;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Favourite.class,version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {
    abstract FavouriteDAO getFavouriteDao();
}
