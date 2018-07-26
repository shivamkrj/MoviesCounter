package in.skr.shivamkumar.moviescounter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface OMDbServices {

    @GET
    Call<OmdbRoot> getMovieBriefs(@Url String url, @Query("i") String imdbID, @Query("plot") String plot , @Query("apikey") String apiKey);
}
