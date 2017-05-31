package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import app.com.lamdbui.android.beerview.model.Beer;

/**
 * Created by lamdbui on 5/8/17.
 */

public class BeerResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private BeerData mData;

    public Beer getBeer() {
        return mData.convertDataToBeer();
    }
}