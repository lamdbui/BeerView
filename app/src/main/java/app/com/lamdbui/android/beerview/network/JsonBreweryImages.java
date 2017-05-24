package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lamdbui on 5/23/17.
 */

public class JsonBreweryImages {
    @SerializedName("icon")
    private String mIconUrl;
    @SerializedName("medium")
    private String mMediumUrl;
    @SerializedName("large")
    private String mLargeUrl;
    @SerializedName("squareMedium")
    private String mSquareMediumUrl;
    @SerializedName("squareLarge")
    private String mSquareLargeUrl;

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getMediumUrl() {
        return mMediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        mMediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return mLargeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        mLargeUrl = largeUrl;
    }

    public String getSquareMediumUrl() {
        return mSquareMediumUrl;
    }

    public void setSquareMediumUrl(String squareMediumUrl) {
        mSquareMediumUrl = squareMediumUrl;
    }

    public String getSquareLargeUrl() {
        return mSquareLargeUrl;
    }

    public void setSquareLargeUrl(String squareLargeUrl) {
        mSquareLargeUrl = squareLargeUrl;
    }
}