package in.skr.shivamkumar.moviescounter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Insert
    long addFavourite(Favourite favourite);

    @Delete
    int deleteFavourite(Favourite favourite);

//    @Delete("DELETE FROM favourite WHERE id = :idd")
//    int deleteFavouriteMovie(long idd);

    @Update
    int update(Favourite favourite);

    @Query("select * from favourite")
    List<Favourite> getFavourites();

    @Query("select* from favourite where id = :iD ")
    Favourite getFavourieFromID(long iD);

    @Query("SELECT COUNT(id) FROM favourite")
    int getTotal();
}
