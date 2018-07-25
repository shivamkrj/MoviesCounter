package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TvFragment extends Fragment {
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewAiringToday;
    RecyclerView recyclerViewOnTheAir;
    RecyclerView recyclerViewPopular;
    RecyclerView recyclerViewTopRated;
    ArrayList<TvResult> airingTodayItems;
    ArrayList<TvResult> onTheAirItems;
    ArrayList<TvResult> popularItems;
    ArrayList<TvResult> topRatedItems;
    AdapterRectangularView popularAdapter;
    AdapterRectangularView airingAdapter;
    AdapterSquareView onTheAirAdapter;
    AdapterSquareView topRatedAdapter;

    private OnFragmentInteractionListener mListener;

    public TvFragment() {
        // Required empty public constructor
    }

    public static TvFragment newInstance(String param1, String param2) {
        TvFragment fragment = new TvFragment();
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
        view = inflater.inflate(R.layout.fragment_tv, container, false);

        loadAiringToaday();
        loadPopular();
        loadOnTheAir();
        loadTopRated();

        return view;
    }

    private void loadTopRated() {
        topRatedItems = new ArrayList<>();
        topRatedAdapter = new AdapterSquareView(getContext(),2,null,topRatedItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
            }
        });
        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRatedTv);
        recyclerViewTopRated.setAdapter(topRatedAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTopRated.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<TvRoot> resultCall = ApiClient.getMovieDbServices().getTopRatedTv(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<TvRoot>() {
            @Override
            public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                List<TvResult> resultList = response.body().getTvResults();
                topRatedItems.addAll(resultList);
                topRatedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPopular() {
        popularItems = new ArrayList<>();
        popularAdapter = new AdapterRectangularView(getContext(),2,null,popularItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
            }
        });
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopularTv);
        recyclerViewPopular.setAdapter(popularAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopular.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<TvRoot> resultCall = ApiClient.getMovieDbServices().getPopularTv(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<TvRoot>() {
            @Override
            public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                List<TvResult> resultList = response.body().getTvResults();
                if(resultList==null){
                    Log.d("play tv","null result list");
                    return;
                }
                popularItems.addAll(resultList);
                popularAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<TvRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOnTheAir() {
        onTheAirItems = new ArrayList<>();
        onTheAirAdapter = new AdapterSquareView(getContext(),2,null,onTheAirItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
            }
        });
        recyclerViewOnTheAir = view.findViewById(R.id.recyclerViewOnTheAir);
        recyclerViewOnTheAir.setAdapter(onTheAirAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewOnTheAir.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<TvRoot> resultCall = ApiClient.getMovieDbServices().getPopularTv(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<TvRoot>() {
            @Override
            public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                List<TvResult> resultList = response.body().getTvResults();
                onTheAirItems.addAll(resultList);
                onTheAirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAiringToaday() {
        airingTodayItems = new ArrayList<>();
        airingAdapter = new AdapterRectangularView(getContext(),2,null,airingTodayItems, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open tv Details
            }
        });
        recyclerViewAiringToday = view.findViewById(R.id.recyclerViewAiringToday);
        recyclerViewAiringToday.setAdapter(airingAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewAiringToday.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<TvRoot> resultCall = ApiClient.getMovieDbServices().getAiringTodayTv(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<TvRoot>() {
            @Override
            public void onResponse(Call<TvRoot> call, Response<TvRoot> response) {
                List<TvResult> resultList = response.body().getTvResults();
                airingTodayItems.addAll(resultList);
                airingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
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

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
