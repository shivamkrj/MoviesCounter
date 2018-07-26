package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsScrollingActivity extends Activity {

    long id;
    boolean isMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        Intent intent = getIntent();
        id = intent.getLongExtra("id",-1);
        String posterPath ="https://image.tmdb.org/t/p/w500/"+ intent.getStringExtra("posterPath");
        String backdropPath ="https://image.tmdb.org/t/p/w500/"+ intent.getStringExtra("backdropPath");
        String title = intent.getStringExtra("title");
        isMovie = intent.getBooleanExtra("isMovie",false);
        toolbar.setTitle(title);
        if(isMovie)
            fetchMovieData();
        else
            fetchTvData();

        ImageView backdropImageView = findViewById(R.id.backdropImageView_Detail);
        ImageView posterImageView = findViewById(R.id.poster_image_detail);
        Picasso.get().load(posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(posterImageView);
        Picasso.get().load(backdropPath)
                .fit()
                .into(backdropImageView);
    }

    private void fetchTvData() {
        Call<TvDetailsRoot> rootCall = ApiClient.getMovieDbServices().getTvDetails(id,zzApiKey.getApiKey(),"en-US",1);
        rootCall.enqueue(new Callback<TvDetailsRoot>() {
            @Override
            public void onResponse(Call<TvDetailsRoot> call, Response<TvDetailsRoot> response) {
                TvDetailsRoot root = response.body();
            }
            @Override
            public void onFailure(Call<TvDetailsRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"No Network",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchMovieData() {
        Call<MovieDetailsRoot> rootCall = ApiClient.getMovieDbServices().getMovieDetails(id,zzApiKey.getApiKey(),"en-US",1);
        rootCall.enqueue(new Callback<MovieDetailsRoot>() {
            @Override
            public void onResponse(Call<MovieDetailsRoot> call, Response<MovieDetailsRoot> response) {
                MovieDetailsRoot root = response.body();
                fetchMovieBriefs(root.getImdbId());
            }
            @Override
            public void onFailure(Call<MovieDetailsRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchMovieBriefs(String imdbId) {
        Call<OmdbRoot> rootCall = ApiClient.getOMDbSrvices().getMovieBriefs("",imdbId,"full",zzApiKey.getApiKeyOMDB());
        rootCall.enqueue(new Callback<OmdbRoot>() {
            @Override
            public void onResponse(Call<OmdbRoot> call, Response<OmdbRoot> response) {
                OmdbRoot root = response.body();
                String directorName = root.getDirector();
                Log.d("director",directorName);
            }

            @Override
            public void onFailure(Call<OmdbRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
