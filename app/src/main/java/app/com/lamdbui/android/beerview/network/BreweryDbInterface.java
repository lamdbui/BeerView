package app.com.lamdbui.android.beerview.network;

import app.com.lamdbui.android.beerview.network.BeerListResponse;
import app.com.lamdbui.android.beerview.network.BeerResponse;
import app.com.lamdbui.android.beerview.network.BreweryLocationResponse;
import app.com.lamdbui.android.beerview.network.BreweryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lamdbui on 4/26/17.
 */

public interface BreweryDbInterface {

    @GET("locations")
    Call<BreweryLocationResponse> getBreweries(
            @Query("key") String apiKey,
            @Query("postalCode") String postalCode);

    @GET("search/geo/point")
    Call<BreweryLocationResponse> getBreweriesNearby(
            @Query("key") String apiKey,
            @Query("lat") double latitude,
            @Query("lng") double longitude);

    @GET("brewery/{id}")
    Call<BreweryResponse> getBrewery(
            @Path("id") String id,
            @Query("key") String apiKey,
            @Query("withLocations") String yesOrNo);

    @GET("brewery/{id}/beers")
    Call<BeerListResponse> getBeersAtBrewery(
            @Path("id") String id,
            @Query("key") String apiKey,
            @Query("withBreweries") String yesOrNo);

    @GET("beer/{id}")
    Call<BeerResponse> getBeer(
            @Path("id") String id,
            @Query("key") String apiKey,
            @Query("withBreweries") String yesOrNo);
}
