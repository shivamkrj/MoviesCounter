package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerViewUpcoming;
    RecyclerView recyclerViewTopRated;
    RecyclerView recyclerViewPopular;
    RecyclerView recyclerViewNowShowing;
    ArrayList<MoviesResult> upcomingItems;
    ArrayList<MoviesResult> topRatedItems;
    ArrayList<MoviesResult> nowShowingItems;
    ArrayList<MoviesResult> popularItems;
    AdapterRectangularView rectangularAdapter;
    AdapterSquareView squareAdapter;
    AdapterRectangularView nowShowingAdapter;
    AdapterSquareView popularAdapter;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies, container, false);

        //calling loading upcoming movies
        if(MainActivity.isBlackTheme){
            LinearLayout rootLayout = view.findViewById(R.id.moviesRootLayout);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.darkBackground,null));
            TextView textView = view.findViewById(R.id.popularMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.popularTextView);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.nowshowingMovieTitle);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.nowShowingTextView2);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.upcomingMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.upcoming2TextView);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.topRatedMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));
            textView = view.findViewById(R.id.topRated2TextView);
            textView.setTextColor(getResources().getColor(R.color.darkTitleColor,null));

        }else{
            LinearLayout rootLayout = view.findViewById(R.id.moviesRootLayout);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.lightBackground,null));
            TextView textView = view.findViewById(R.id.popularMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.popularTextView);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.nowshowingMovieTitle);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.nowShowingTextView2);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.upcomingMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.upcoming2TextView);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.topRatedMoviesTitle);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
            textView = view.findViewById(R.id.topRated2TextView);
            textView.setTextColor(getResources().getColor(R.color.lightTitleColor,null));
        }
        loadUpcomingMovies();
        loadTopRatedMovies();
        loadPopularMovies();
        loadNowShowingMovies();
        return view;
    }

    private void loadNowShowingMovies() {
        nowShowingItems = new ArrayList<>();
        nowShowingAdapter = new AdapterRectangularView(getContext(),1,nowShowingItems,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = nowShowingItems.get(position);
                Intent intent = new Intent(getContext(),DetailsScrollingActivity.class);
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
        recyclerViewNowShowing = view.findViewById(R.id.recyclerViewNowShowing);
        recyclerViewNowShowing.setAdapter(nowShowingAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNowShowing.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getNowShowing(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                nowShowingItems.addAll(resultList);
                nowShowingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(getContext(),"TvDetailsRootNetwork Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPopularMovies() {
        popularItems = new ArrayList<>();
        popularAdapter = new AdapterSquareView(getContext(),1,popularItems,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = popularItems.get(position);

                Intent intent = new Intent(getContext(),DetailsScrollingActivity.class);
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
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular);
        recyclerViewPopular.setAdapter(popularAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopular.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                popularItems.addAll(resultList);
                popularAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(getContext(),"TvDetailsRootNetwork Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTopRatedMovies() {
        topRatedItems = new ArrayList<>();
        squareAdapter = new AdapterSquareView(getContext(),1,topRatedItems,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = topRatedItems.get(position);

                Intent intent = new Intent(getContext(),DetailsScrollingActivity.class);
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
        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRated);
        recyclerViewTopRated.setAdapter(squareAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTopRated.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getTopRatedMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                String name = resultList.get(0).getTitle();
                Log.d("onresponse",name+" topRated");

                topRatedItems.addAll(resultList);
                squareAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(getContext(),"TvDetailsRootNetwork Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUpcomingMovies() {
        upcomingItems = new ArrayList<>();
        rectangularAdapter = new AdapterRectangularView(getContext(),1,upcomingItems,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = upcomingItems.get(position);

                Intent intent = new Intent(getContext(),DetailsScrollingActivity.class);
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
        recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);
        recyclerViewUpcoming.setAdapter(rectangularAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewUpcoming.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getUpcomingMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                upcomingItems.addAll(resultList);
                String name = resultList.get(0).getTitle();
                Log.d("onresponse",name+" name");
                rectangularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(getContext(),"TvDetailsRootNetwork Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void seeAll(View view){}

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
