package app.com.lamdbui.android.beerview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lamdbui on 4/26/17.
 */

public interface BreweryDbInterface {

    @GET("locations")
    Call<BreweryResponse> getBreweries(
            @Query("key") String apiKey,
            @Query("postalCode") String postalCode);

    @GET("search/geo/point")
    Call<BreweryResponse> getBreweriesNearby(
            @Query("key") String apiKey,
            @Query("lat") double latitude,
            @Query("lng") double longitude);
}
