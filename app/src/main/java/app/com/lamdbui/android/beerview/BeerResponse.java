package app.com.lamdbui.android.beerview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lamdbui on 5/8/17.
 */

public class BeerResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private BeerData mData;

    private class BeerData {
        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("nameDisplay")
        private String mNameDisplay;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("abv")
        private String mAbv;
        @SerializedName("glasswareId")
        private int mGlasswareId;
        @SerializedName("srmId")
        private int mSrmId;
        @SerializedName("availableId")
        private int mAvailableId;
        @SerializedName("styleId")
        private int mStyleId;
        @SerializedName("isOrganic")
        private String mIsOrganic;
        @SerializedName("labels")
        private JsonBeerLabels mLabels;
        @SerializedName("servingTemperature")
        private String mServingTemperature;
        @SerializedName("servingTemperatureDisplay")
        private String mServingTemperatureDisplay;
        @SerializedName("originalGravity")
        private String mOriginalGravity;
    }

    private class JsonBeerLabels {
        @SerializedName("icon")
        private String mIcon;
        @SerializedName("medium")
        private String mMedium;
        @SerializedName("large")
        private String mLarge;
    }

    public Beer convertResponseToBeer(BeerResponse response) {

        BeerData jsonData = response.mData;

        Beer beer = new Beer();

        beer.setId(jsonData.mId);
        beer.setName(jsonData.mName);
        beer.setNameDisplay(jsonData.mNameDisplay);
        beer.setDescription(jsonData.mDescription);
        beer.setAbv(Double.parseDouble(jsonData.mAbv));
        beer.setGlasswareId(jsonData.mGlasswareId);
        beer.setSrmId(jsonData.mSrmId);
        beer.setAvailableId(jsonData.mAvailableId);
        beer.setStyleId(jsonData.mStyleId);
        beer.setOrganic((jsonData.mIsOrganic).equals("Y") ? true : false);
        beer.setServingTemperature(jsonData.mServingTemperature);
        beer.setServingTemperatureDisplay(jsonData.mServingTemperatureDisplay);
        beer.setOriginalGravity(Double.parseDouble(jsonData.mOriginalGravity));
        beer.setLabelsIcon(jsonData.mLabels.mIcon);
        beer.setLabelsMedium(jsonData.mLabels.mIcon);
        beer.setLabelsLarge(jsonData.mLabels.mLarge);

        return beer;
    }

    public Beer getBeer() {
        return convertResponseToBeer(this);
    }
}