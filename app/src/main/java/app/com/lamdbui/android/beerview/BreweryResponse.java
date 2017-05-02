package app.com.lamdbui.android.beerview;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lamdbui on 4/26/17.
 */

public class BreweryResponse {

    @SerializedName("currentPage")
    private int mCurrentPage;
    @SerializedName("numberOfPages")
    private int mNumberofPages;
    @SerializedName("totalResults")
    private int mTotalResults;
    @SerializedName("data")
    private List<BreweryData> mData;

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

    public List<BreweryData> getData() {
        return mData;
    }

    public void setData(List<BreweryData> data) {
        mData = data;
    }

    // convert our response to a flattened Brewery object
    public Brewery convertResponseToBrewery(BreweryData data) {
        Brewery brewery = new Brewery();

        brewery.setId(data.getBrewery().getId());
        brewery.setName(data.getBrewery().getName());
        brewery.setNameShort(data.getBrewery().getNameShortDisplay());
        brewery.setDescription(data.getBrewery().getDescription());
        brewery.setWebsite(data.getBrewery().getWebsite());
        if(data.getBrewery().getEstablished() != null)
            brewery.setEstablished(Integer.parseInt(data.getBrewery().getEstablished()));
        brewery.setOrganic((data.getBrewery().getIsOrganic().equals("Y")) ? true : false);
        if(data.getBrewery().getImages() != null) {
            brewery.setImagesIcon(data.getBrewery().getImages().getIconUrl());
            brewery.setImagesMedium(data.getBrewery().getImages().getMediumUrl());
            brewery.setImagesLarge(data.getBrewery().getImages().getLargeUrl());
            brewery.setImagesSquareMedium(data.getBrewery().getImages().getSquareMediumUrl());
            brewery.setImagesSquareLarge(data.getBrewery().getImages().getSquareLargeUrl());
        }
        brewery.setStreetAddress(data.getStreetAddress());
        brewery.setHoursOfOperation(data.getHoursOfOperation());
        brewery.setLocality(data.getLocality());
        brewery.setRegion(data.getRegion());
        brewery.setPostalCode(data.getPostalCode());
        brewery.setPhone(data.getPhone());
        brewery.setLatitude(data.getLatitude());
        brewery.setLongitude(data.getLongitude());
        brewery.setPrimary((data.getIsPrimary().equals("Y")) ? true : false);
        brewery.setPlanning((data.getInPlanning().equals("Y")) ? true : false);
        brewery.setClosed((data.getIsClosed().equals("Y")) ? true : false);
        brewery.setOpenToPublic((data.getOpenToPublic().equals("Y")) ? true : false);
        brewery.setLocationType(data.getLocationType());
        brewery.setLocationTypeDisplay(data.getLocationTypeDisplay());
        brewery.setCountryIsoCode(data.getCountryIsoCode());

        return brewery;
    }
    public List<Brewery> getBreweries() {
        List<Brewery> breweries = new ArrayList<>();

        for(BreweryData data : mData) {
            breweries.add(convertResponseToBrewery(data));
        }

        return breweries;
    }

    private class BreweryData {
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
        @SerializedName("brewery")
        private JsonBreweryData mBrewery;

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

        public JsonBreweryData getBrewery() {
            return mBrewery;
        }

        public void setBrewery(JsonBreweryData brewery) {
            mBrewery = brewery;
        }
    }

    private class JsonBreweryData {
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

        private class JsonBreweryImages {
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
