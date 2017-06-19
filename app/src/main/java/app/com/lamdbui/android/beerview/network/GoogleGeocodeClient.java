package app.com.lamdbui.android.beerview.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lamdbui on 6/19/17.
 */

public class GoogleGeocodeClient {

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/geocode/";

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
