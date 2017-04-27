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
//    @SerializedName("brewery/established")
//    private String mEstablished;
    @SerializedName("brewery")
    private JsonBrewery mBrewery;

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

    public JsonBrewery getBrewery() {
        return mBrewery;
    }

    public void setBrewery(JsonBrewery brewery) {
        mBrewery = brewery;
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
        builder.append("established: " + mBrewery.getEstablished() + "\n");
        builder.append("image_medium_url" + mBrewery.getImages().getMediumUrl() + "\n");

        return builder.toString();
    }

    private class JsonBrewery {
        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("nameShortDisplay")
        private String mNameShortDisplay;
        @SerializedName("website")
        private String mWebsite;
        @SerializedName("established")
        private String mEstablished;
        @SerializedName("isOrganic")
        private String mIsOrganic;
        @SerializedName("images")
        private JsonBreweryImages mImages;

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
