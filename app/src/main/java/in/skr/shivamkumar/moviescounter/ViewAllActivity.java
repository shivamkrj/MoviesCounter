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
    AdapterSquareView adapter;
    RecyclerView recyclerView;
    int page =1;
    boolean isScrolled = false;
    int currentItem,totalItem,scrolledOutItem;
    GridLayoutManager layoutManager;
   // LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        recyclerView = findViewById(R.id.recyclerView);
        Intent i = getIntent();
        String s = i.getStringExtra("url");

        items = new ArrayList<>();
        adapter = new AdapterSquareView(this,1,items,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        if(s.equals("popular")){
            openAllPopular();
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolled = true;
                    Log.d("play", "LOAD NEXT ITEM2");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("play","onScrolled "+page);

                currentItem = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrolledOutItem = layoutManager.findFirstVisibleItemPosition();

                if(isScrolled && (currentItem + scrolledOutItem)==totalItem){
                    isScrolled = false;
                    page++;
                    openAllPopular();
                    Log.d("play 2", "LOAD NEXT ITEM");
                }


            }
        });
    }

    private void fetchData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                openAllPopular();
            }
        },5000);
    }

    private void openAllPopular() {
        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey,"en-US",page);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                items.addAll(resultList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this,"TvDetailsRootNetwork Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}


