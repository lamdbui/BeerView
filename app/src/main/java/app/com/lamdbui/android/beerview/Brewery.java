package app.com.lamdbui.android.beerview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lamdbui on 5/23/17.
 */

public class Brewery implements Parcelable {

    private String mId;
    private String mName;
    private String mNameShortDisplay;
    private String mDescription;
    private String mWebsite;
    private int mEstablished;
    private boolean mIsOrganic;
    private String mImagesIcon;
    private String mImagesMedium;
    private String mImagesLarge;
    private String mImagesSquareMedium;
    private String mImagesSquareLarge;
    private List<BreweryLocation> mLocations;

    public Brewery() {

    }

    private Brewery(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mNameShortDisplay = in.readString();
        mDescription = in.readString();
        mWebsite = in.readString();
        mEstablished = in.readInt();
        mIsOrganic = (in.readInt() == 1) ? true : false;
        mImagesIcon = in.readString();
        mImagesMedium = in.readString();
        mImagesLarge = in.readString();
        mImagesSquareMedium = in.readString();
        mImagesSquareLarge = in.readString();
        mLocations = new ArrayList<>();
        in.readTypedList(mLocations, BreweryLocation.CREATOR);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mNameShortDisplay);
        parcel.writeString(mDescription);
        parcel.writeString(mWebsite);
        parcel.writeInt(mEstablished);
        parcel.writeInt((mIsOrganic) ? 1 : 0);
        parcel.writeString(mImagesIcon);
        parcel.writeString(mImagesMedium);
        parcel.writeString(mImagesLarge);
        parcel.writeString(mImagesSquareMedium);
        parcel.writeString(mImagesSquareLarge);
        parcel.writeTypedList(mLocations);
    }

    public static final Parcelable.Creator<Brewery> CREATOR =
            new Parcelable.Creator<Brewery>() {
                @Override
                public Brewery createFromParcel(Parcel parcel) {
                    return new Brewery(parcel);
                }

                @Override
                public Brewery[] newArray(int size) {
                    return new Brewery[size];
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

    public List<BreweryLocation> getLocations() {
        return mLocations;
    }

    public void setLocations(List<BreweryLocation> locations) {
        mLocations = locations;
    }
}
