package app.com.lamdbui.android.beerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lamdbui on 4/26/17.
 */

public class BreweryDbClient {

    public static final String BASE_URL = "http://api.brewerydb.com/v2/";

    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {
        if(sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
