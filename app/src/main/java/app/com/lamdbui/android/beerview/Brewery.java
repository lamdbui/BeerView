package app.com.lamdbui.android.beerview;

/**
 * Created by lamdbui on 4/25/17.
 */

public class Brewery {

    public static final String LOG_TAG = Brewery.class.getSimpleName();

    private int mId;
    private String mName;
    private String mStreetAddress;
    private String mLocality;
    private String mRegion;
    private String mPostalCode;
    private String mPhone;
    private String mWebsite;
    private double mLatitude;
    private double mLongitude;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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
