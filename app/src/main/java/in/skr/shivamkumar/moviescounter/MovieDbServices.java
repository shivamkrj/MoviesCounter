package in.skr.shivamkumar.moviescounter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbServices {

    @GET("movie/upcoming")
    Call<MoviesRoot> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesRoot> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/popular")
    Call<MoviesRoot> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MoviesRoot> getNowShowing(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("tv/on_the_air")
    Call<TvRoot> getOnTheAirTv(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("tv/popular")
    Call<TvRoot> getPopularTv(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("tv/airing_today")
    Call<TvRoot> getAiringTodayTv(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("tv/top_rated")
    Call<TvRoot> getTopRatedTv(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetailsRoot> getMovieDetails(@Path("id") long id, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("tv/{id}")
    Call<TvDetailsRoot> getTvDetails(@Path("id") long id, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/{id}/similar")
    Call<MoviesRoot> getSimilarMovies (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("tv/{id}/similar")
    Call<TvRoot> getSimilarTv (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/{id}/credits")
    Call<CastRoot> getMovieCast (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("tv/{id}/credits")
    Call<CastRoot> getTvCast (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("person/{id}/tv_credits")
    Call<TvCreditsRoot> getTvCredits (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("person/{id}/movie_credits")
    Call<MovieCreditsRoot> getMovieCredits (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("person/{id}/images")
    Call<PersonImagesRoot> getPersonImages (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("person/{id}")
    Call<PersonDetailsRoot> getPersonDetails (@Path("id") long id,@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

}