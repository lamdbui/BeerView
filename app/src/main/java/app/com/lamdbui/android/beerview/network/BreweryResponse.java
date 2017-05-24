package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.Brewery;
import app.com.lamdbui.android.beerview.BreweryLocation;

/**
 * Created by lamdbui on 5/23/17.
 */

public class BreweryResponse {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("nameShortDisplay")
    private String mNameShortDisplay;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("established")
    private String mEstablished;
    @SerializedName("isOrganic")
    private String mIsOrganic;
    @SerializedName("images")
    private JsonBreweryImages mImages;
    @SerializedName("locations")
    private List<BreweryLocationResponse.BreweryLocationData> mLocations;

    public Brewery convertResponseToBrewery() {
        Brewery brewery = new Brewery();

        brewery.setId(mId);
        brewery.setName(mName);
        brewery.setNameShortDisplay(mNameShortDisplay);
        brewery.setDescription(mDescription);
        brewery.setWebsite(mWebsite);
        brewery.setEstablished(Integer.parseInt(mEstablished));
        brewery.setOrganic(mIsOrganic.equals("Y") ? true : false);
        if(mImages != null) {
            brewery.setImagesIcon(mImages.getIconUrl());
            brewery.setImagesMedium(mImages.getMediumUrl());
            brewery.setImagesLarge(mImages.getLargeUrl());
            brewery.setImagesSquareMedium(mImages.getSquareMediumUrl());
            brewery.setImagesSquareLarge(mImages.getSquareLargeUrl());
        }
        // TODO: Need to convert the BreweryLocationData to BreweryLocation

        List<BreweryLocation> breweryLocations = new ArrayList<>();

        for(BreweryLocationResponse.BreweryLocationData locationData : mLocations) {
            //BreweryLocation breweryLocation = locationData.convertToBreweryLocation();
            breweryLocations.add(locationData.convertToBreweryLocation());
        }
        brewery.setLocations(breweryLocations);
        //brewery.setLocations(data.mLocations);

        return brewery;
    }
}
