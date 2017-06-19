package app.com.lamdbui.android.beerview.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lamdbui on 6/19/17.
 */

public interface GoogleGeocodeInterface {
    @GET("json")
    Call<AddressResponse> getLocationData(
            @Query("address") String address
    );
}
