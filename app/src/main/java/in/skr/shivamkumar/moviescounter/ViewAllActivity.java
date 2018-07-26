package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
    AdapterSquareView adapter;
    RecyclerView recyclerView;
    int page =1;
    boolean isScrolled = false;
    int currentItem,totalItem,scrolledOutItem;
    GridLayoutManager layoutManager;
    private final int MOVIES_POPULAR = 1;
    private final int MOVIES_UPCOMING = 2;
    private final int MOVIES_NOWSHOWING = 3;
    private final int MOVIES_TOPRATED = 4;
    private final int TV_AIRINGTODAY = 6;
    private final int TV_ONTHEAIR = 7;
    private final int TV_POPULAR = 8;
    private final int TV_TOPRATED = 9;
    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        recyclerView = findViewById(R.id.recyclerView);
        Intent i = getIntent();
        String s = i.getStringExtra("url");

        items = new ArrayList<>();
        tvItems = new ArrayList<>();

        recyclerView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        if(s.equals("popular")){
            category = MOVIES_POPULAR;
        }else if(s.equals("upcoming")){
            category = MOVIES_UPCOMING;
        }else if(s.equals("topRated")){
            category = MOVIES_TOPRATED;
        }else if(s.equals("nowShowing")){
            category = MOVIES_NOWSHOWING;
        }else if(s.equals("tvTopRated")){
            category = TV_TOPRATED;
        }else if(s.equals("tvPopular")){
            category = TV_POPULAR;
        }else if(s.equals("airingToday")){
            category = TV_AIRINGTODAY;
        }else if(s.equals("onTheAir")){
            category = TV_ONTHEAIR;
        }
        if(category<5){
            adapter = new AdapterSquareView(this,1,items,null, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open movie Details
                }
            });
        }else{
            adapter = new AdapterSquareView(this,1,null,tvItems, new ViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //open movie Details
                }
            });
        }

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
        if (category < 5) {//for Movies
            Call<MoviesRoot> resultCall;
            if (category == MOVIES_POPULAR)
                resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey, "en-US", page);
            else if (category == MOVIES_NOWSHOWING)
                resultCall = ApiClient.getMovieDbServices().getNowShowing(apiKey, "en-US", page);
            else if (category == MOVIES_TOPRATED)
                resultCall = ApiClient.getMovieDbServices().getTopRatedMovies(apiKey, "en-US", page);
            else
                resultCall = ApiClient.getMovieDbServices().getUpcomingMovies(apiKey, "en-US", page);

            resultCall.enqueue(new Callback<MoviesRoot>() {
                @Override
                public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                    List<MoviesResult> resultList = response.body().getResults();
                    items.addAll(resultList);
                    adapter.notifyDataSetChanged();
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
            else
                resultCall= ApiClient.getMovieDbServices().getOnTheAirTv(apiKey,"en-US",1);
            resultCall.enqueue(new Callback<TvRoot>() {
                @Override
                public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                    List<TvResult> resultList = response.body().getTvResults();
                    tvItems.addAll(resultList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TvRoot> call, Throwable t) {
                    Toast.makeText(ViewAllActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


