package app.com.lamdbui.android.beerview;

/**
 * Created by lamdbui on 4/25/17.
 */

public class Brewery {

    public static final String LOG_TAG = Brewery.class.getSimpleName();

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

        return builder.toString();
    }
}
