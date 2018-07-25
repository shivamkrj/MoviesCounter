package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity {

    ArrayList<UpcomingResult> items;
    AdapterSquareView adapter;
    RecyclerView recyclerView;
    int page =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        recyclerView = findViewById(R.id.recyclerView);
        Intent i = getIntent();
        String s = i.getStringExtra("url");
        if(s.equals("popular")){
            openAllPopular();
        }
    }

    private void openAllPopular() {
        items = new ArrayList<>();
        adapter = new AdapterSquareView(this,items, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerView.setAdapter(adapter);
        EndlessScrollListener listener = new EndlessScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                openAllPopular();
            }
        };

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,,false);
        recyclerView.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<UpcomingRoot> resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey,"en-US",page);
        resultCall.enqueue(new Callback<UpcomingRoot>() {
            @Override
            public void onResponse(Call<UpcomingRoot> call, Response<UpcomingRoot> response) {
                List<UpcomingResult> resultList = response.body().getResults();

                items.addAll(resultList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<UpcomingRoot> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
        page++;
    }
    public static abstract class OnScrollListener extends RecyclerView.OnScrollListener{
        public OnScrollListener() {
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(dy==10){

            }
        }
    }
}


