package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.Beer;

/**
 * Created by lamdbui on 5/24/17.
 */

public class BeerListResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private List<BeerData> mData;

    public List<Beer> getBeerList() {
        List<Beer> beers = new ArrayList<>();

        for(BeerData beerData : mData) {
            beers.add(beerData.convertDataToBeer());
        }

        return beers;
    }
}
