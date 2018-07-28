package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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

public class CastDetailsActivity extends Activity {

    long id;
    RecyclerView recyclerViewTvCast;
    RecyclerView recyclerViewMovieCast;
    ArrayList<TvResult> tvItems;
    ArrayList<MoviesResult> movieItems;
    AdapterRectangularView tvCastAdapter;
    AdapterRectangularView movieCastAdapter;
    TextView biographyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cast);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        Intent intent = getIntent();
        id = intent.getLongExtra("id",500);
        String name = intent.getStringExtra("name");
        String profilePath = "https://image.tmdb.org/t/p/w500/" + intent.getStringExtra("profilePath");
        ImageView imageView = findViewById(R.id.backdropImageView_Cast);
        Picasso.get().load(profilePath).into(imageView);

        if(name==null||name.length()==0)
            toolbar.setTitle("Cast");
        else
            toolbar.setTitle(name);

        biographyTextView = findViewById(R.id.biographyTextView);
        fetchPersonDetails();
        fetchMovieCast();
        fetchTvCast();
    }

    private void fetchPersonDetails() {
        Call<PersonDetailsRoot> call = ApiClient.getMovieDbServices().getPersonDetails(id,zzApiKey.getApiKey(),"en-US",1);
        call.enqueue(new Callback<PersonDetailsRoot>() {
            @Override
            public void onResponse(Call<PersonDetailsRoot> call, Response<PersonDetailsRoot> response) {
                PersonDetailsRoot root = response.body();
                String biography = root.getBiography();
                biographyTextView.setText(biography);
                biographyTextView.setMaxLines(4);
                biographyTextView.setEllipsize(TextUtils.TruncateAt.END);
            }

            @Override
            public void onFailure(Call<PersonDetailsRoot> call, Throwable t) {

            }
        });
    }
    private void fetchTvCast() {
        tvItems = new ArrayList<>();
        tvCastAdapter = new AdapterRectangularView(this, 2,null, tvItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
                TvResult result = tvItems.get(position);

                Intent intent = new Intent(CastDetailsActivity.this,DetailsScrollingActivity.class);
                intent.putExtra("posterPath",result.getPosterPath());
                intent.putExtra("backdropPath",result.getBackdropPath());
                intent.putExtra("title",result.getName());
                intent.putExtra("id",result.getId());
                intent.putExtra("isMovie",false);
                intent.putExtra("overview",result.getOverview());
                startActivity(intent);
            }
        });
        Call<TvCreditsRoot> creditsRootCall = ApiClient.getMovieDbServices().getTvCredits(id,zzApiKey.getApiKey(),"en-US",1);
        creditsRootCall.enqueue(new Callback<TvCreditsRoot>() {
            @Override
            public void onResponse(Call<TvCreditsRoot> call, Response<TvCreditsRoot> response) {
                List<TvCreditsRootCast> list = response.body().getCast();
                for(int i=0;i<list.size();i++){
                    TvCreditsRootCast resultItem = list.get(i);
                    TvResult item = new TvResult();
                    item.setId(resultItem.getId());
                    item.setBackdropPath(resultItem.getBackdropPath());
                    item.setPosterPath(resultItem.getPosterPath());
                    item.setOverview(resultItem.getOverview());
                    item.setName(resultItem.getName());
                    item.setVoteAverage(resultItem.getVoteAverage());
                    item.setGenreIds(resultItem.getGenreIds());
                    tvItems.add(item);
                    tvCastAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TvCreditsRoot> call, Throwable t) {
                Toast.makeText(CastDetailsActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewTvCast = findViewById(R.id.recyclerViewTvCast);
        recyclerViewTvCast.setAdapter(tvCastAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTvCast.setLayoutManager(layoutManager);
    }
    private void fetchMovieCast() {
        movieItems = new ArrayList<>();
        movieCastAdapter = new AdapterRectangularView(this, 1, movieItems, null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = movieItems.get(position);
                Intent intent = new Intent(CastDetailsActivity.this,DetailsScrollingActivity.class);
                intent.putExtra("posterPath",result.getPosterPath());
                intent.putExtra("backdropPath",result.getBackdropPath());
                intent.putExtra("title",result.getTitle());
                intent.putExtra("id",result.getId());
                intent.putExtra("isMovie",true);
                intent.putExtra("overview",result.getOverview());
                intent.putExtra("rating",result.getVoteAverage());
                startActivity(intent);
            }
        });
        Call<MovieCreditsRoot> creditsRootCall = ApiClient.getMovieDbServices().getMovieCredits(id,zzApiKey.getApiKey(),"en-US",1);
        creditsRootCall.enqueue(new Callback<MovieCreditsRoot>() {
            @Override
            public void onResponse(Call<MovieCreditsRoot> call, Response<MovieCreditsRoot> response) {
                List<MovieCreditsRootCast> list = response.body().getCast();
                for(int i=0;i<list.size();i++){
                    MovieCreditsRootCast resultItem = list.get(i);
                    MoviesResult item = new MoviesResult();
                    item.setId(resultItem.getId());
                    item.setBackdropPath(resultItem.getBackdropPath());
                    item.setPosterPath(resultItem.getPosterPath());
                    item.setOverview(resultItem.getOverview());
                    item.setTitle(resultItem.getTitle());
                    item.setVoteAverage(resultItem.getVoteAverage());
                    item.setGenreIds(resultItem.getGenreIds());
                    movieItems.add(item);
                    movieCastAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieCreditsRoot> call, Throwable t) {

            }
        });
        recyclerViewMovieCast = findViewById(R.id.recyclerViewMovieCast);
        recyclerViewMovieCast.setAdapter(movieCastAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMovieCast.setLayoutManager(layoutManager);
    }
    public void readMoreBiography(View view) {
        TextView textView = (TextView)view;
        if(((TextView) view).getText().toString().equals("Collapse")){
            biographyTextView.setMaxLines(4);
            biographyTextView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setText("Read More");
            return;
        }

        biographyTextView.setMaxLines(Integer.MAX_VALUE);
        textView.setText("Collapse");
    }

    public void seeAllMovieCast(View view) {
        Intent intent = new Intent(this,ViewAllActivity.class);
        intent.putExtra("url","castMovieList");
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void seeAllTvCast(View view) {
        Intent intent = new Intent(this,ViewAllActivity.class);
        intent.putExtra("url","castTvList");
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
