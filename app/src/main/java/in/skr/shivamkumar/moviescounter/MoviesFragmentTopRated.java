package in.skr.shivamkumar.moviescounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragmentTopRated.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragmentTopRated#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragmentTopRated extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<MoviesResult> items;
    AdapterRectangularView adapter;
    View view;

    public MoviesFragmentTopRated() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragmentTopRated.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragmentTopRated newInstance(String param1, String param2) {
        MoviesFragmentTopRated fragment = new MoviesFragmentTopRated();
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
        view = inflater.inflate(R.layout.fragment_movies_fragment_top_rated, container, false);
        FrameLayout frameLayout = view.findViewById(R.id.rootLayoutMoviesFragmentNowShowing);
        if(MainActivity.isBlackTheme){
            frameLayout.setBackgroundColor(getResources().getColor(R.color.darkBackground,null));
        }
        recyclerView = view.findViewById(R.id.recyclerViewFragmentMoviesPopular);
        items = new ArrayList<>();

        adapter = new AdapterRectangularView(getContext(),1,items,null, new ViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open movie Details
                MoviesResult result = items.get(position);
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
        recyclerView.setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,25,true));

        recyclerView.setLayoutManager(layoutManager);

        String apiKey = zzApiKey.getApiKey();
        final Call<MoviesRoot> resultCall = ApiClient.getMovieDbServices().getTopRatedMovies(apiKey,"en-US",1);
        resultCall.enqueue(new Callback<MoviesRoot>() {
            @Override
            public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
                List<MoviesResult> resultList = response.body().getResults();
                items.addAll(resultList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesRoot> call, Throwable t) {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
