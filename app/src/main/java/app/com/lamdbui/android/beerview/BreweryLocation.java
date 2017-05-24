package app.com.lamdbui.android.beerview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lamdbui on 4/25/17.
 */

public class BreweryLocation implements Parcelable {

    public static final String LOG_TAG = BreweryLocation.class.getSimpleName();

    private String mId;
    private String mName;
    private String mNameShort;
    private String mDescription;
    private String mWebsite;
    // TODO: Do some conversion that makes more sense than a String here
    private String mHoursOfOperation;
    private int mEstablished;
    private boolean mIsOrganic;
    private String mImagesIcon;
    private String mImagesMedium;
    private String mImagesLarge;
    private String mImagesSquareMedium;
    private String mImagesSquareLarge;
    private String mStreetAddress;
    private String mLocality;
    private String mRegion;
    private String mPostalCode;
    private String mPhone;
    private double mLatitude;
    private double mLongitude;
    private boolean mIsPrimary;
    private boolean mIsPlanning;
    private boolean mIsClosed;
    private boolean mOpenToPublic;
    private String mLocationType;
    private String mLocationTypeDisplay;
    private String mCountryIsoCode;

    // additional fields
    private boolean mIsFavorite;

    public BreweryLocation() {

    }

    private BreweryLocation(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mNameShort = in.readString();
        mDescription = in.readString();
        mWebsite = in.readString();
        mHoursOfOperation = in.readString();
        mEstablished = in.readInt();
        mIsOrganic = (in.readInt() == 1) ? true : false;
        mImagesIcon = in.readString();
        mImagesMedium = in.readString();
        mImagesLarge = in.readString();
        mImagesSquareMedium = in.readString();
        mImagesSquareLarge = in.readString();
        mStreetAddress = in.readString();
        mLocality = in.readString();
        mRegion = in.readString();
        mPostalCode = in.readString();
        mPhone = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mIsPrimary = (in.readInt() == 1) ? true : false;
        mIsPlanning = (in.readInt() == 1) ? true : false;
        mIsClosed = (in.readInt() == 1) ? true : false;
        mOpenToPublic = (in.readInt() == 1) ? true : false;
        mLocationType = in.readString();
        mLocationTypeDisplay = in.readString();
        mCountryIsoCode = in.readString();
        mIsFavorite = (in.readInt() == 1) ? true : false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mNameShort);
        parcel.writeString(mDescription);
        parcel.writeString(mWebsite);
        parcel.writeString(mHoursOfOperation);
        parcel.writeInt(mEstablished);
        parcel.writeInt(mIsOrganic ? 1 : 0);
        parcel.writeString(mImagesIcon);
        parcel.writeString(mImagesMedium);
        parcel.writeString(mImagesLarge);
        parcel.writeString(mImagesSquareMedium);
        parcel.writeString(mImagesSquareLarge);
        parcel.writeString(mStreetAddress);
        parcel.writeString(mLocality);
        parcel.writeString(mRegion);
        parcel.writeString(mPostalCode);
        parcel.writeString(mPhone);
        parcel.writeDouble(mLatitude);
        parcel.writeDouble(mLongitude);
        parcel.writeInt(mIsPrimary ? 1 : 0);
        parcel.writeInt(mIsPlanning ? 1 : 0);
        parcel.writeInt(mIsClosed ? 1 : 0);
        parcel.writeInt(mOpenToPublic ? 1 : 0);
        parcel.writeString(mLocationType);
        parcel.writeString(mLocationTypeDisplay);
        parcel.writeString(mCountryIsoCode);
        parcel.writeInt(mIsFavorite ? 1 : 0);
    }

    public static final Parcelable.Creator<BreweryLocation> CREATOR =
            new Parcelable.Creator<BreweryLocation>() {
                @Override
                public BreweryLocation createFromParcel(Parcel parcel) {
                    return new BreweryLocation(parcel);
                }

                @Override
                public BreweryLocation[] newArray(int size) {
                    return new BreweryLocation[size];
                }
            };

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

    public String getNameShort() {
        return mNameShort;
    }

    public void setNameShort(String nameShort) {
        mNameShort = nameShort;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getHoursOfOperation() {
        return mHoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        mHoursOfOperation = hoursOfOperation;
    }

    public int getEstablished() {
        return mEstablished;
    }

    public void setEstablished(int established) {
        mEstablished = established;
    }

    public boolean isOrganic() {
        return mIsOrganic;
    }

    public void setOrganic(boolean organic) {
        mIsOrganic = organic;
    }

    public String getImagesIcon() {
        return mImagesIcon;
    }

    public void setImagesIcon(String imagesIcon) {
        mImagesIcon = imagesIcon;
    }

    public String getImagesMedium() {
        return mImagesMedium;
    }

    public void setImagesMedium(String imagesMedium) {
        mImagesMedium = imagesMedium;
    }

    public String getImagesLarge() {
        return mImagesLarge;
    }

    public void setImagesLarge(String imagesLarge) {
        mImagesLarge = imagesLarge;
    }

    public String getImagesSquareMedium() {
        return mImagesSquareMedium;
    }

    public void setImagesSquareMedium(String imagesSquareMedium) {
        mImagesSquareMedium = imagesSquareMedium;
    }

    public String getImagesSquareLarge() {
        return mImagesSquareLarge;
    }

    public void setImagesSquareLarge(String imagesSquareLarge) {
        mImagesSquareLarge = imagesSquareLarge;
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

    public boolean isPrimary() {
        return mIsPrimary;
    }

    public void setPrimary(boolean primary) {
        mIsPrimary = primary;
    }

    public boolean isPlanning() {
        return mIsPlanning;
    }

    public void setPlanning(boolean planning) {
        mIsPlanning = planning;
    }

    public boolean isClosed() {
        return mIsClosed;
    }

    public void setClosed(boolean closed) {
        mIsClosed = closed;
    }

    public boolean isOpenToPublic() {
        return mOpenToPublic;
    }

    public void setOpenToPublic(boolean openToPublic) {
        mOpenToPublic = openToPublic;
    }

    public String getLocationType() {
        return mLocationType;
    }

    public void setLocationType(String locationType) {
        mLocationType = locationType;
    }

    public String getLocationTypeDisplay() {
        return mLocationTypeDisplay;
    }

    public void setLocationTypeDisplay(String locationTypeDisplay) {
        mLocationTypeDisplay = locationTypeDisplay;
    }

    public String getCountryIsoCode() {
        return mCountryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        mCountryIsoCode = countryIsoCode;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
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
        builder.append("established: " + mEstablished + "\n");
        builder.append("image_icon: " + mImagesIcon + "\n");

        // additional fields
        builder.append("favorite: " + (mIsFavorite ? "Y" : "N") + "\n");

        return builder.toString();
    }
}