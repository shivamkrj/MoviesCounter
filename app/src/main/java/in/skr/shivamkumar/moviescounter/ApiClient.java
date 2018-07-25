package in.skr.shivamkumar.moviescounter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static MovieDbServices services;
    public static Retrofit getRetrofitInstance(){
        if (retrofit==null){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }
    public static MovieDbServices getMovieDbServices(){
        if(services==null){
            services = getRetrofitInstance().create(MovieDbServices.class);
        }
        return services;
    }
}
