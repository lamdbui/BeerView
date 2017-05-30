package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.BreweryLocation;

/**
 * Created by lamdbui on 4/26/17.
 */

public class BreweryLocationResponse {

    @SerializedName("currentPage")
    private int mCurrentPage;
    @SerializedName("numberOfPages")
    private int mNumberofPages;
    @SerializedName("totalResults")
    private int mTotalResults;
    @SerializedName("data")
    private List<BreweryLocationData> mData;

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public int getNumberofPages() {
        return mNumberofPages;
    }

    public void setNumberofPages(int numberofPages) {
        mNumberofPages = numberofPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public List<BreweryLocationData> getData() {
        return mData;
    }

    public void setData(List<BreweryLocationData> data) {
        mData = data;
    }

    // convert our response to a flattened BreweryLocation object
    public BreweryLocation convertResponseToBreweryLocation(BreweryLocationData data) {
        BreweryLocation breweryLocation = new BreweryLocation();

        breweryLocation.setId(data.getBrewery().getId());
        breweryLocation.setName(data.getBrewery().getName());
        breweryLocation.setNameShort(data.getBrewery().getNameShortDisplay());
        breweryLocation.setDescription(data.getBrewery().getDescription());
        breweryLocation.setWebsite(data.getBrewery().getWebsite());
        if(data.getBrewery().getEstablished() != null)
            breweryLocation.setEstablished(Integer.parseInt(data.getBrewery().getEstablished()));
        breweryLocation.setOrganic((data.getBrewery().getIsOrganic().equals("Y")) ? true : false);
        if(data.getBrewery().getImages() != null) {
            breweryLocation.setImagesIcon(data.getBrewery().getImages().getIconUrl());
            breweryLocation.setImagesMedium(data.getBrewery().getImages().getMediumUrl());
            breweryLocation.setImagesLarge(data.getBrewery().getImages().getLargeUrl());
            breweryLocation.setImagesSquareMedium(data.getBrewery().getImages().getSquareMediumUrl());
            breweryLocation.setImagesSquareLarge(data.getBrewery().getImages().getSquareLargeUrl());
        }
        breweryLocation.setStreetAddress(data.getStreetAddress());
        breweryLocation.setHoursOfOperation(data.getHoursOfOperation());
        breweryLocation.setLocality(data.getLocality());
        breweryLocation.setRegion(data.getRegion());
        breweryLocation.setPostalCode(data.getPostalCode());
        breweryLocation.setPhone(data.getPhone());
        breweryLocation.setLatitude(data.getLatitude());
        breweryLocation.setLongitude(data.getLongitude());
        breweryLocation.setPrimary((data.getIsPrimary().equals("Y")) ? true : false);
        breweryLocation.setPlanning((data.getInPlanning().equals("Y")) ? true : false);
        breweryLocation.setClosed((data.getIsClosed().equals("Y")) ? true : false);
        breweryLocation.setOpenToPublic((data.getOpenToPublic().equals("Y")) ? true : false);
        breweryLocation.setLocationType(data.getLocationType());
        breweryLocation.setLocationTypeDisplay(data.getLocationTypeDisplay());
        breweryLocation.setCountryIsoCode(data.getCountryIsoCode());
        breweryLocation.setBreweryId(data.getBreweryId());
        //breweryLocation.setBreweryId(data.get);

        return breweryLocation;
    }
    public List<BreweryLocation> getBreweries() {
        List<BreweryLocation> breweries = new ArrayList<>();

        for(BreweryLocationData data : mData) {
            breweries.add(convertResponseToBreweryLocation(data));
        }

        return breweries;
    }

    public class BreweryLocationData {
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
        @SerializedName("hoursOfOperation")
        private String mHoursOfOperation;
        @SerializedName("latitude")
        private double mLatitude;
        @SerializedName("longitude")
        private double mLongitude;
        @SerializedName("isPrimary")
        private String mIsPrimary;
        @SerializedName("inPlanning")
        private String mInPlanning;
        @SerializedName("isClosed")
        private String mIsClosed;
        @SerializedName("openToPublic")
        private String mOpenToPublic;
        @SerializedName("locationType")
        private String mLocationType;
        @SerializedName("locationTypeDisplay")
        private String mLocationTypeDisplay;
        @SerializedName("countryIsoCode")
        private String mCountryIsoCode;
        @SerializedName("breweryId")
        private String mBreweryId;
        // Do we need this one?
        @SerializedName("brewery")
        private JsonBreweryData mBrewery;

        // convert our response to a flattened BreweryLocation object
        public BreweryLocation convertToBreweryLocation() {
            BreweryLocation breweryLocation = new BreweryLocation();

            breweryLocation.setId(mId);
            breweryLocation.setName(mName);
            breweryLocation.setStreetAddress(mStreetAddress);
            breweryLocation.setLocality(mLocality);
            breweryLocation.setRegion(mRegion);
            breweryLocation.setPostalCode(mPostalCode);
            breweryLocation.setPhone(mPhone);
            breweryLocation.setWebsite(mWebsite);
            breweryLocation.setHoursOfOperation(mHoursOfOperation);
            breweryLocation.setLatitude(mLatitude);
            breweryLocation.setLongitude(mLongitude);
            breweryLocation.setPrimary(mIsPrimary.equals("Y") ? true : false);
            breweryLocation.setPlanning(mInPlanning.equals("Y") ? true : false);
            breweryLocation.setClosed(mIsClosed.equals("Y") ? true : false);
            breweryLocation.setOpenToPublic(mOpenToPublic.equals("Y") ? true : false);
            breweryLocation.setLocationType(mLocationType);
            breweryLocation.setLocationTypeDisplay(mLocationTypeDisplay);
            breweryLocation.setCountryIsoCode(mCountryIsoCode);
            if(mBreweryId != null)
                breweryLocation.setBreweryId(mBreweryId);

            return breweryLocation;
        }

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

        public String getHoursOfOperation() {
            return mHoursOfOperation;
        }

        public void setHoursOfOperation(String hoursOfOperation) {
            mHoursOfOperation = hoursOfOperation;
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

        public String getIsPrimary() {
            return mIsPrimary;
        }

        public void setIsPrimary(String isPrimary) {
            mIsPrimary = isPrimary;
        }

        public String getInPlanning() {
            return mInPlanning;
        }

        public void setInPlanning(String inPlanning) {
            mInPlanning = inPlanning;
        }

        public String getIsClosed() {
            return mIsClosed;
        }

        public void setIsClosed(String isClosed) {
            mIsClosed = isClosed;
        }

        public String getOpenToPublic() {
            return mOpenToPublic;
        }

        public void setOpenToPublic(String openToPublic) {
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

        public String getBreweryId() {
            return mBreweryId;
        }

        public void setBreweryId(String breweryId) {
            mBreweryId = breweryId;
        }

        public JsonBreweryData getBrewery() {
            return mBrewery;
        }

        public void setBrewery(JsonBreweryData brewery) {
            mBrewery = brewery;
        }
    }

    public class JsonBreweryData {
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

        public JsonBreweryData() {
            mId = null;
            mName = null;
            mNameShortDisplay = null;
            mDescription = null;
            mWebsite = null;
            mEstablished = null;
            mIsOrganic = null;
            mImages = null;
        }

        public String getEstablished() {
            return mEstablished;
        }

        public void setEstablished(String established) {
            mEstablished = established;
        }

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

        public String getNameShortDisplay() {
            return mNameShortDisplay;
        }

        public void setNameShortDisplay(String nameShortDisplay) {
            mNameShortDisplay = nameShortDisplay;
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

        public String getIsOrganic() {
            return mIsOrganic;
        }

        public void setIsOrganic(String isOrganic) {
            mIsOrganic = isOrganic;
        }

        public JsonBreweryImages getImages() {
            return mImages;
        }

        public void setImages(JsonBreweryImages images) {
            mImages = images;
        }

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
    }
}
