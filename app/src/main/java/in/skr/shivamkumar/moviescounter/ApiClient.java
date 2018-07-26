package in.skr.shivamkumar.moviescounter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static Retrofit oMDbRetrofit;
    private static MovieDbServices services;
    private static OMDbServices omDbServices;
    public static Retrofit getRetrofitInstance(){
        if (retrofit==null){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }
    public static Retrofit getOMDbRetrofitInstance(){
        if (oMDbRetrofit==null){
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            oMDbRetrofit = builder.build();
        }
        return oMDbRetrofit;
    }
    public static MovieDbServices getMovieDbServices(){
        if(services==null){
            services = getRetrofitInstance().create(MovieDbServices.class);
        }
        return services;
    }
    public static OMDbServices getOMDbSrvices(){
        if(omDbServices==null){
            omDbServices = getOMDbRetrofitInstance().create(OMDbServices.class);
        }
        return omDbServices;
    }
}
