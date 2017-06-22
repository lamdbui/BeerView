package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;

/**
 * Created by lamdbui on 5/24/17.
 */

public class BeerData {
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
    @SerializedName("style")
    private JsonBeerStyle mBeerStyle;

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

    public Beer convertDataToBeer() {

        Beer beer = new Beer();

        beer.setId(mId);
        beer.setName(mName);
        beer.setNameDisplay(mNameDisplay);
        beer.setDescription(mDescription);
        if(mAbv != null)
            beer.setAbv(Double.parseDouble(mAbv));
        beer.setGlasswareId(mGlasswareId);
        beer.setSrmId(mSrmId);
        beer.setAvailableId(mAvailableId);
        beer.setStyleId(mStyleId);
        beer.setOrganic(mIsOrganic.equals("Y") ? true : false);
        beer.setServingTemperature(mServingTemperature);
        beer.setServingTemperatureDisplay(mServingTemperatureDisplay);
        if(mOriginalGravity != null)
            beer.setOriginalGravity(Double.parseDouble(mOriginalGravity));
        if(mLabels != null) {
            beer.setLabelsIcon(mLabels.mIcon);
            beer.setLabelsMedium(mLabels.mMedium);
            beer.setLabelsLarge(mLabels.mLarge);
        }
        if(mBeerStyle != null) {
            beer.setBeerStyleId(Integer.parseInt(mBeerStyle.mId));
            beer.setBeerStyleName(mBeerStyle.mName);
            beer.setBeerStyleShortName(mBeerStyle.mShortName);
            beer.setBeerStyleDescription(mBeerStyle.mDescription);
        }
        List<Brewery> breweries = new ArrayList<>();

        if(mBreweryResponses != null) {
            for (BreweryResponse.BreweryData brewery : mBreweryResponses) {
                breweries.add(brewery.convertResponseToBrewery());
            }
            beer.setBreweries(breweries);
        }

        return beer;
    }
}