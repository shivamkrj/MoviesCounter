package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsScrollingActivity extends Activity {

    long id;
    boolean isMovie;
    RecyclerView castRecyclerView;
    ArrayList<CastRootCast> castItems;
    AdapterCasts adapterCasts;
    RecyclerView similarRecyclerView;
    ArrayList<TvResult> tvItems;
    ArrayList<MoviesResult> movieItems;
    AdapterRectangularView adapterSimilar;
    TextView descriptionTextView;
    TextView descriptionBriefTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
              //  getWindow().setFlags(0,0);
             //   Toast.makeText(DetailsScrollingActivity.this,"offsetListener",Toast.LENGTH_SHORT).show();
            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        final Intent intent = getIntent();
        id = intent.getLongExtra("id",-1);
        String posterPath ="https://image.tmdb.org/t/p/w500/"+ intent.getStringExtra("posterPath");
        String backdropPath ="https://image.tmdb.org/t/p/w500/"+ intent.getStringExtra("backdropPath");
        String title = intent.getStringExtra("title");
        isMovie = intent.getBooleanExtra("isMovie",false);
        toolbar.setTitle(title);
        descriptionTextView = findViewById(R.id.description_detail);
        descriptionBriefTextView=findViewById(R.id.description_brief);
        if(isMovie)
            fetchMovieData();
        else
            fetchTvData();

        ImageView backdropImageView = findViewById(R.id.backdropImageView_Detail);
        ImageView posterImageView = findViewById(R.id.poster_image_detail);
        //set gif placeholder
        Picasso.get().load(posterPath)
                .fit()
                .into(posterImageView);
        Picasso.get().load(backdropPath)
                .fit()
                .into(backdropImageView);

        String overview = intent.getStringExtra("overview");
        Double rating = intent.getDoubleExtra("rating",0);
        descriptionTextView.setText(overview);
        TextView ratingsTextView = findViewById(R.id.ratingTextView_Detail);
        ratingsTextView.setText(rating+"*");

        castRecyclerView = findViewById(R.id.recyclerViewCasts);
        castItems = new ArrayList<>();
        adapterCasts = new AdapterCasts(DetailsScrollingActivity.this, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open cast detatils activity
                Intent intent1 = new Intent(DetailsScrollingActivity.this,CastDetailsActivity.class);
                CastRootCast item = castItems.get(position);
                intent1.putExtra("id",item.getId());
                intent1.putExtra("name",item.getName());
                intent1.putExtra("profilePath",item.getProfilePath());
                startActivity(intent1);
                Log.d("play","onClick ");
            }
        }, castItems);
        castRecyclerView.setAdapter(adapterCasts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsScrollingActivity.this, LinearLayoutManager.HORIZONTAL,false);
        castRecyclerView.setLayoutManager(layoutManager);
        similarRecyclerView = findViewById(R.id.recyclerViewSimilar);
        if(isMovie)
            loadSimilarMovie();
        else
            loadSimilarTv();

    }


    private void loadSimilarMovie(){
        movieItems = new ArrayList<>();
        adapterSimilar = new AdapterRectangularView(this, 1, movieItems, null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {//open movie details
                MoviesResult result = movieItems.get(position);

                Intent intent = new Intent(DetailsScrollingActivity.this, DetailsScrollingActivity.class);
                intent.putExtra("posterPath", result.getPosterPath());
                intent.putExtra("backdropPath", result.getBackdropPath());
                intent.putExtra("title", result.getTitle());
                intent.putExtra("id", result.getId());
                intent.putExtra("isMovie", true);
                intent.putExtra("overview", result.getOverview());
                intent.putExtra("rating", result.getVoteAverage());
                startActivity(intent);
            }
        });
        similarRecyclerView = findViewById(R.id.recyclerViewSimilar);
        similarRecyclerView.setAdapter(adapterSimilar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        similarRecyclerView.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getSimilarMovies(id,apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                if(resultList==null){
                    return;
                }
                movieItems.addAll(resultList);
                adapterSimilar.notifyDataSetChanged();
                TextView t1 = findViewById(R.id.similarMoviesTitleTextView);
                t1.setText("Similar Movies");
                t1.setVisibility(View.VISIBLE);
                t1 = findViewById(R.id.similarSeeAll);
                t1.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadSimilarTv(){
        tvItems = new ArrayList<>();
        adapterSimilar = new AdapterRectangularView(this,2,null,tvItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
                TvResult result = tvItems.get(position);

                Intent intent = new Intent(DetailsScrollingActivity.this,DetailsScrollingActivity.class);
                intent.putExtra("posterPath",result.getPosterPath());
                intent.putExtra("backdropPath",result.getBackdropPath());
                intent.putExtra("title",result.getName());
                intent.putExtra("id",result.getId());
                intent.putExtra("isMovie",false);
                intent.putExtra("overview",result.getOverview());
                startActivity(intent);
            }
        });
        similarRecyclerView = findViewById(R.id.recyclerViewSimilar);
        similarRecyclerView.setAdapter(adapterSimilar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        similarRecyclerView.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<TvRoot> resultCall = ApiClient.getMovieDbServices().getSimilarTv(id,apiKey,"en-US",1);
        resultCall.enqueue(new Callback<TvRoot>() {
            @Override
            public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                List<TvResult> resultList = response.body().getTvResults();
                if(resultList==null){
                    return;
                }
                tvItems.addAll(resultList);
                adapterSimilar.notifyDataSetChanged();
                TextView t1 = findViewById(R.id.similarMoviesTitleTextView);
                t1.setText("Similar TV Shows");
                t1.setVisibility(View.VISIBLE);
                t1 = findViewById(R.id.similarSeeAll);
                t1.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<TvRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTvData() {
        Call<TvDetailsRoot> rootCall = ApiClient.getMovieDbServices().getTvDetails(id,zzApiKey.getApiKey(),"en-US",1);
        rootCall.enqueue(new Callback<TvDetailsRoot>() {
            @Override
            public void onResponse(Call<TvDetailsRoot> call, Response<TvDetailsRoot> response) {
                TvDetailsRoot root = response.body();
                fetchCast();
            }
            @Override
            public void onFailure(Call<TvDetailsRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"No Network",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchCast() {
        Call<CastRoot> rootCall;
        if(isMovie)
            rootCall = ApiClient.getMovieDbServices().getMovieCast(id,zzApiKey.getApiKey(),"en-US",1);
        else
            rootCall = ApiClient.getMovieDbServices().getTvCast(id,zzApiKey.getApiKey(),"en-US",1);

        rootCall.enqueue(new Callback<CastRoot>() {
            @Override
            public void onResponse(Call<CastRoot> call, Response<CastRoot> response) {
                CastRoot root = response.body();
                castItems.addAll(root.getCast());
                adapterCasts.notifyDataSetChanged();
                TextView textView = findViewById(R.id.similarCastTextView);
                textView.setVisibility(View.VISIBLE);
                textView = findViewById(R.id.castTitleTextView);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<CastRoot> call, Throwable t) {

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
                fetchCast();
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
                if(root==null)
                    return;
                //Release Date
                String briefText;
                String s = root.getReleased();
                briefText = "<b>Release Date</b> : "+s+"<br>";
                briefText = "<b>Genre</b> : "+root.getGenre();

//                descriptionBriefTextView.setText(Html.fromHtml("<b>Release Date</b> : "+s));


            }

            @Override
            public void onFailure(Call<OmdbRoot> call, Throwable t) {
                Toast.makeText(DetailsScrollingActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void seeAll(View view){
        Intent intent = new Intent(this,ViewAllActivity.class);
        if(isMovie)
            intent.putExtra("url","similarMovie");
        else
            intent.putExtra("url","similarTv");
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void seeAllCast(View view){
        Intent intent = new Intent(this,ViewAllActivity.class);
        if(isMovie)
            intent.putExtra("url","castMovie");
        else
            intent.putExtra("url","castTv");
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void readMoreDescription(View view) {
        TextView textView = (TextView)view;
        if(((TextView) view).getText().toString().equals("Collapse")){
            descriptionTextView.setMaxLines(4);
            descriptionTextView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setText("Read More");
            return;
        }
        descriptionTextView.setMaxLines(Integer.MAX_VALUE);
        textView.setText("Collapse");
        if(isMovie)
            descriptionBriefTextView.setVisibility(View.VISIBLE);
    }
}
