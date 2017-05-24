package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.Beer;
import app.com.lamdbui.android.beerview.Brewery;
import app.com.lamdbui.android.beerview.BreweryLocation;

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
        @SerializedName("breweries")
        private List<BreweryResponse.BreweryData> mBreweryResponses;
        //private List<Brewery> mBreweries;
        //private List<BreweryLocationResponse.BreweryLocationData> mBreweries;
        @SerializedName("style")
        private JsonBeerStyle mBeerStyle;
    }

    private class JsonBeerLabels {
        @SerializedName("icon")
        private String mIcon;
        @SerializedName("medium")
        private String mMedium;
        @SerializedName("large")
        private String mLarge;
    }

    private class JsonBeerStyle {
        @SerializedName("id")
        private String mId;
        @SerializedName("categoryId")
        private String mCategoryId;
        @SerializedName("name")
        private String mName;
        @SerializedName("shortName")
        private String mShortName;
        @SerializedName("description")
        private String mDescription;
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
        beer.setBeerStyleId(Integer.parseInt(jsonData.mBeerStyle.mId));
        beer.setBeerStyleName(jsonData.mBeerStyle.mName);
        beer.setBeerStyleShortName(jsonData.mBeerStyle.mShortName);
        beer.setBeerStyleDescription(jsonData.mBeerStyle.mDescription);

        List<Brewery> breweries = new ArrayList<>();

        for(BreweryResponse.BreweryData brewery : jsonData.mBreweryResponses) {
            breweries.add(brewery.convertResponseToBrewery());
        }
        beer.setBreweries(breweries);

        return beer;
    }

    public Beer getBeer() {
        return convertResponseToBeer(this);
    }
}