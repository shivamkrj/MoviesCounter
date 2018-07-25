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
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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
    ArrayList<UpcomingResult> upcomingItems;
    ArrayList<UpcomingResult> topRatedItems;
    ArrayList<UpcomingResult> nowShowingItems;
    ArrayList<UpcomingResult> popularItems;
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
        loadUpcomingMovies();
        loadTopRatedMovies();
        loadPopularMovies();
        loadNowShowingMovies();
        return view;
    }

    private void loadNowShowingMovies() {
        nowShowingItems = new ArrayList<>();
        nowShowingAdapter = new AdapterRectangularView(getContext(),nowShowingItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerViewNowShowing = view.findViewById(R.id.recyclerViewNowShowing);
        recyclerViewNowShowing.setAdapter(nowShowingAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNowShowing.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<UpcomingRoot> resultCall = ApiClient.getMovieDbServices().getNowShowing(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<UpcomingRoot>() {
            @Override
            public void onResponse(Call<UpcomingRoot> call, Response<UpcomingRoot> response) {
                List<UpcomingResult> resultList = response.body().getResults();
                nowShowingItems.addAll(resultList);
                nowShowingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UpcomingRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPopularMovies() {
        popularItems = new ArrayList<>();
        popularAdapter = new AdapterSquareView(getContext(),popularItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular);
        recyclerViewPopular.setAdapter(popularAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopular.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<UpcomingRoot> resultCall = ApiClient.getMovieDbServices().getPopularMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<UpcomingRoot>() {
            @Override
            public void onResponse(Call<UpcomingRoot> call, Response<UpcomingRoot> response) {
                List<UpcomingResult> resultList = response.body().getResults();

                popularItems.addAll(resultList);
                popularAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<UpcomingRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTopRatedMovies() {
        topRatedItems = new ArrayList<>();
        squareAdapter = new AdapterSquareView(getContext(),topRatedItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRated);
        recyclerViewTopRated.setAdapter(squareAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTopRated.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<UpcomingRoot> resultCall = ApiClient.getMovieDbServices().getTopRatedMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<UpcomingRoot>() {
            @Override
            public void onResponse(Call<UpcomingRoot> call, Response<UpcomingRoot> response) {
                List<UpcomingResult> resultList = response.body().getResults();
                String name = resultList.get(0).getTitle();
                Log.d("onresponse",name+" topRated");

                topRatedItems.addAll(resultList);
                squareAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<UpcomingRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUpcomingMovies() {
        upcomingItems = new ArrayList<>();
        rectangularAdapter = new AdapterRectangularView(getContext(),upcomingItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
            }
        });
        recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);
        recyclerViewUpcoming.setAdapter(rectangularAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewUpcoming.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<UpcomingRoot> resultCall = ApiClient.getMovieDbServices().getUpcomingMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<UpcomingRoot>() {
            @Override
            public void onResponse(Call<UpcomingRoot> call, Response<UpcomingRoot> response) {
                List<UpcomingResult> resultList = response.body().getResults();
                upcomingItems.addAll(resultList);
                String name = resultList.get(0).getTitle();
                Log.d("onresponse",name+" name");
                rectangularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UpcomingRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seeAllPopular(View view){
        Intent intent = new Intent(getContext(),ViewAllActivity.class);
        intent.putExtra("url","popular");
        startActivity(intent);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
