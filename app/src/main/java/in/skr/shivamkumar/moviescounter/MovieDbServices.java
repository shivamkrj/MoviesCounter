package in.skr.shivamkumar.moviescounter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MovieDbServices {

    @GET("movie/upcoming")
    Call<UpcomingRoot> getUpcomingMovies(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("movie/top_rated")
    Call<UpcomingRoot> getTopRatedMovies(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("movie/popular")
    Call<UpcomingRoot> getPopularMovies(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("movie/now_playing")
    Call<UpcomingRoot> getNowShowing(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

}