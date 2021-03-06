package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity {

    ArrayList<MoviesResult> items;
    ArrayList<TvResult> tvItems;
    ArrayList<CastRootCast> castItems;
    AdapterSquareView squrareAdapter;
    AdapterRectangularView rectangularAdapter;
    AdapterCasts castsAdapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    int page =1;
    boolean isScrolled = false;
    boolean isSmallView;
    int currentItem,totalItem,scrolledOutItem;
    long similarId;
    GridLayoutManager layoutManager;
    private final int MOVIES_POPULAR = 1;
    private final int MOVIES_UPCOMING = 2;
    private final int MOVIES_NOWSHOWING = 3;
    private final int MOVIES_TOPRATED = 4;
    private final int MOVIES_SIMILAR = 5;
    private final int TV_AIRINGTODAY = 6;
    private final int TV_ONTHEAIR = 7;
    private final int TV_POPULAR = 8;
    private final int TV_TOPRATED = 9;
    private final int TV_SIMILAR = 10;
    private final int CASTTV = 888;
    private final int CASTMOVIE = 999;
    private final int CASTMOVIELIST = 101;
    private final int CASTTVLIST = 201;
    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        sharedPreferences=getSharedPreferences("my_shared_pref",MODE_PRIVATE);
        isSmallView = sharedPreferences.getBoolean("ISSMALLVIEW",true);
        recyclerView = findViewById(R.id.recyclerView);
        Intent i = getIntent();
        String s = i.getStringExtra("url");

        if(s.equals("popular")){
            category = MOVIES_POPULAR;
            setTitle("Popular Movies");
        }else if(s.equals("upcoming")){
            category = MOVIES_UPCOMING;
            setTitle("Upcoming Movies");
        }else if(s.equals("topRated")){
            category = MOVIES_TOPRATED;
            setTitle("Top Rated Movies");
        }else if(s.equals("nowShowing")){
            category = MOVIES_NOWSHOWING;
            setTitle("Now Showing Movies");
        }else if(s.equals("tvTopRated")){
            category = TV_TOPRATED;
            setTitle("Top Rated TV Series");
        }else if(s.equals("tvPopular")){
            category = TV_POPULAR;
            setTitle("Popular TV Series");
        }else if(s.equals("airingToday")){
            category = TV_AIRINGTODAY;
            setTitle("Airing Today");
        }else if(s.equals("onTheAir")){
            category = TV_ONTHEAIR;
        }else if(s.equals("similarMovie")){
            setTitle("Similar Movies");
            category = MOVIES_SIMILAR;
            similarId = i.getLongExtra("id",0);
        }else if(s.equals("similarTv")){
            setTitle("Similar TV Series");
            category = TV_SIMILAR;
            similarId = i.getLongExtra("id",0);
        }else if(s.equals("castTv")){
            setTitle("All Casts");
            category = CASTTV;
            similarId = i.getLongExtra("id",0);
        }else if(s.equals("castMovie")){
            setTitle("All Casts");
            category = CASTMOVIE;
            similarId = i.getLongExtra("id",0);
        }else if(s.equals("castMovieList")){
            setTitle("See All Movies");
            category = CASTMOVIELIST;
            similarId = i.getLongExtra("id",0);
        }else if(s.equals("castTvList")){
            setTitle("See All TV Series");
            category = CASTTVLIST;
            similarId = i.getLongExtra("id",0);
        }

        changeTheme();

        if(category == CASTMOVIE || category == CASTTV)
        {
            layoutManager = new GridLayoutManager(this, 2);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,40,true));
        }
        else if(!isSmallView){
            layoutManager = new GridLayoutManager(this, 1);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,25,true));
        }
        else
            layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        if(category<6){
            items = new ArrayList<>();
            if(isSmallView){
                squrareAdapter = new AdapterSquareView(this,1,items,null, new ViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //open movie Details
                        MoviesResult result = items.get(position);

                        Intent intent = new Intent(ViewAllActivity.this, DetailsScrollingActivity.class);
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
            }else{
                rectangularAdapter = new AdapterRectangularView(this, 1, items, null, new ViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //open movie Details
                        MoviesResult result = items.get(position);

                        Intent intent = new Intent(ViewAllActivity.this, DetailsScrollingActivity.class);
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
            }
        }else if(category ==CASTTV||category==CASTMOVIE){
            loadAllCast();
            return;
        }else if(category == CASTMOVIELIST){
            loadAllCastMovieList();
        }else if(category == CASTTVLIST){
            loadAllCastTvList();
        }else{
            tvItems = new ArrayList<>();
            if(isSmallView){
                squrareAdapter = new AdapterSquareView(this,2,null,tvItems, new ViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //open tv Details
                        TvResult result = tvItems.get(position);

                        Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
                        intent.putExtra("posterPath",result.getPosterPath());
                        intent.putExtra("backdropPath",result.getBackdropPath());
                        intent.putExtra("title",result.getName());
                        intent.putExtra("id",result.getId());
                        intent.putExtra("isMovie",false);
                        intent.putExtra("overview",result.getOverview());
                        startActivity(intent);
                    }
                });
            }else{
                rectangularAdapter = new AdapterRectangularView(this,2,null,tvItems, new ViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //open tv Details
                        TvResult result = tvItems.get(position);

                        Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
                        intent.putExtra("posterPath",result.getPosterPath());
                        intent.putExtra("backdropPath",result.getBackdropPath());
                        intent.putExtra("title",result.getName());
                        intent.putExtra("id",result.getId());
                        intent.putExtra("isMovie",false);
                        intent.putExtra("overview",result.getOverview());
                        startActivity(intent);
                    }
                });
            }

        }
        if(isSmallView)
            recyclerView.setAdapter(squrareAdapter);
        else
            recyclerView.setAdapter(rectangularAdapter);
        if(category == CASTMOVIELIST|| category ==CASTTVLIST)
            return;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrolledOutItem = layoutManager.findFirstVisibleItemPosition();

                if(isScrolled && (currentItem + scrolledOutItem)==totalItem){
                    isScrolled = false;
                    page++;
                    openAll();
                }
            }
        });
        openAll();
    }

    private void changeTheme() {
        if(MainActivity.isBlackTheme){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                    .getColor(R.color.darkToolbarPrimary,null)));
            ConstraintLayout rootLayout = findViewById(R.id.viewAllRootLayout);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.darkBackground,null));
        }else{
            ConstraintLayout rootLayout = findViewById(R.id.viewAllRootLayout);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.lightBackground,null));
        }
    }

    private void loadAllCastTvList() {
        tvItems = new ArrayList<>();
        if(isSmallView){
            squrareAdapter = new AdapterSquareView(this, 2,null, tvItems, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open tv Details
                    TvResult result = tvItems.get(position);

                    Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
                    intent.putExtra("posterPath",result.getPosterPath());
                    intent.putExtra("backdropPath",result.getBackdropPath());
                    intent.putExtra("title",result.getName());
                    intent.putExtra("id",result.getId());
                    intent.putExtra("isMovie",false);
                    intent.putExtra("overview",result.getOverview());
                    startActivity(intent);
                }
            });
        }else{
            rectangularAdapter = new AdapterRectangularView(this, 2,null, tvItems, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open tv Details
                    TvResult result = tvItems.get(position);

                    Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
                    intent.putExtra("posterPath",result.getPosterPath());
                    intent.putExtra("backdropPath",result.getBackdropPath());
                    intent.putExtra("title",result.getName());
                    intent.putExtra("id",result.getId());
                    intent.putExtra("isMovie",false);
                    intent.putExtra("overview",result.getOverview());
                    startActivity(intent);
                }
            });
        }

        Call<TvCreditsRoot> creditsRootCall = ApiClient.getMovieDbServices().getTvCredits(similarId,zzApiKey.getApiKey(),"en-US",1);
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
                    if(isSmallView)
                        squrareAdapter.notifyDataSetChanged();
                    else
                        rectangularAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TvCreditsRoot> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllCastMovieList() {
        items = new ArrayList<>();
        if(isSmallView){
            squrareAdapter = new AdapterSquareView(this, 1, items, null, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open movie Details
                    MoviesResult result = items.get(position);
                    Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
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
        }else{
            rectangularAdapter = new AdapterRectangularView(this, 1, items, null, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open movie Details
                    MoviesResult result = items.get(position);
                    Intent intent = new Intent(ViewAllActivity.this,DetailsScrollingActivity.class);
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
        }

        Call<MovieCreditsRoot> creditsRootCall = ApiClient.getMovieDbServices().getMovieCredits(similarId,zzApiKey.getApiKey(),"en-US",1);
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
                    items.add(item);
                    if(isSmallView)
                        squrareAdapter.notifyDataSetChanged();
                    else
                        rectangularAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MovieCreditsRoot> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllCast() {
        castItems = new ArrayList<>();
        castsAdapter = new AdapterCasts(ViewAllActivity.this, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open cast detatils activity
                Intent intent1 = new Intent(ViewAllActivity.this,CastDetailsActivity.class);
                CastRootCast item = castItems.get(position);
                intent1.putExtra("id",item.getId());
                intent1.putExtra("name",item.getName());
                intent1.putExtra("profilePath",item.getProfilePath());
                startActivity(intent1);
            }
        }, castItems);
        recyclerView.setAdapter(castsAdapter);
        Call<CastRoot> rootCall;
        if(category==CASTMOVIE)
            rootCall = ApiClient.getMovieDbServices().getMovieCast(similarId,zzApiKey.getApiKey(),"en-US",1);
        else
            rootCall = ApiClient.getMovieDbServices().getTvCast(similarId,zzApiKey.getApiKey(),"en-US",1);

        rootCall.enqueue(new Callback<CastRoot>() {
            @Override
            public void onResponse(Call<CastRoot> call, Response<CastRoot> response) {
                CastRoot root = response.body();
                castItems.addAll(root.getCast());
                castsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastRoot> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this,"No Network",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                openAll();
            }
        },5000);
    }

    private void openAll() {
        String apiKey = zzApiKey.getApiKey();
        if (category < 6) {//for Movies
            Call<MoviesRoot> resultCall;
            if (category == MOVIES_POPULAR)
                resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey, "en-US", page);
            else if (category == MOVIES_NOWSHOWING)
                resultCall = ApiClient.getMovieDbServices().getNowShowing(apiKey, "en-US", page);
            else if (category == MOVIES_TOPRATED)
                resultCall = ApiClient.getMovieDbServices().getTopRatedMovies(apiKey, "en-US", page);
            else if (category == MOVIES_SIMILAR)
                resultCall = ApiClient.getMovieDbServices().getSimilarMovies(similarId,apiKey, "en-US", page);
            else
                resultCall = ApiClient.getMovieDbServices().getUpcomingMovies(apiKey, "en-US", page);

            resultCall.enqueue(new Callback<MoviesRoot>() {
                @Override
                public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                    List<MoviesResult> resultList = response.body().getResults();
                    items.addAll(resultList);
                    if(isSmallView)
                        squrareAdapter.notifyDataSetChanged();
                    else
                        rectangularAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MoviesRoot> call, Throwable t) {
                    Toast.makeText(ViewAllActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        }else{//for Tv
            Call<TvRoot> resultCall;
            if(category ==TV_AIRINGTODAY)
                resultCall= ApiClient.getMovieDbServices().getAiringTodayTv(apiKey,"en-US",1);
            else if(category ==TV_POPULAR)
                resultCall= ApiClient.getMovieDbServices().getPopularTv(apiKey,"en-US",1);
            else if(category ==TV_TOPRATED)
                resultCall= ApiClient.getMovieDbServices().getTopRatedTv(apiKey,"en-US",1);
            else if(category ==TV_SIMILAR)
                resultCall= ApiClient.getMovieDbServices().getSimilarTv(similarId,apiKey,"en-US",1);
            else
                resultCall= ApiClient.getMovieDbServices().getOnTheAirTv(apiKey,"en-US",1);
            resultCall.enqueue(new Callback<TvRoot>() {
                @Override
                public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                    List<TvResult> resultList = response.body().getTvResults();
                    tvItems.addAll(resultList);
                    if(isSmallView)
                        squrareAdapter.notifyDataSetChanged();
                    else
                        rectangularAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvRoot> call, Throwable t) {
                    Toast.makeText(ViewAllActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(category == CASTMOVIE || category == CASTTV)
        {
            return false;
        }
        getMenuInflater().inflate(R.menu.menu_view_all,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.smallViewMenu){
            if(!isSmallView){
                isSmallView = true;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISSMALLVIEW",true);
                editor.apply();
                startActivity(getIntent());
                finish();
            }
            return true;
        }else if(id == R.id.largeViewMenu){
            if(isSmallView){
                isSmallView = false;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISSMALLVIEW",false);
                editor.apply();
                startActivity(getIntent());
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


