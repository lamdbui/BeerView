package app.com.lamdbui.android.beerview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lamdbui on 4/25/17.
 */

public class Brewery {

    public static final String LOG_TAG = Brewery.class.getSimpleName();

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("streetAddress")
    private String mStreetAddress;
    @SerializedName("locality")
    private String mLocality;
    @SerializedName("region")
    private String mRegion;
    @SerializedName("postalCode")
    private String mPostalCode;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("latitude")
    private double mLatitude;
    @SerializedName("longitude")
    private double mLongitude;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStreetAddress() {
        return mStreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        mStreetAddress = streetAddress;
    }

    public String getLocality() {
        return mLocality;
    }

    public void setLocality(String locality) {
        mLocality = locality;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: " + mId + "\n");
        builder.append("name: " + mName + "\n");
        builder.append("street_address: " + mStreetAddress + "\n");
        builder.append("locality: " + mLocality + "\n");
        builder.append("region: " + mRegion + "\n");
        builder.append("postal_code: " + mPostalCode + "\n");
        builder.append("phone: " + mPhone + "\n");
        builder.append("website: " + mWebsite + "\n");
        builder.append("latitude: " + mLatitude + "\n");
        builder.append("longitude: " + mLongitude + "\n");

        return builder.toString();
    }
}
