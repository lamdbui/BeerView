package app.com.lamdbui.android.beerview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lamdbui on 5/8/17.
 */

public class Beer implements Parcelable {

    private String mId;
    private String mName;
    private String mNameDisplay;
    private String mDescription;
    private double mAbv;
    private int mGlasswareId;
    private int mSrmId;
    private int mAvailableId;
    private int mStyleId;
    private boolean mIsOrganic;
    private String mServingTemperature;
    private String mServingTemperatureDisplay;
    private double mOriginalGravity;
    private String mLabelsIcon;
    private String mLabelsMedium;
    private String mLabelsLarge;

    public Beer() {

    }

    private Beer(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mNameDisplay = in.readString();
        mDescription = in.readString();
        mAbv = in.readDouble();
        mGlasswareId = in.readInt();
        mSrmId = in.readInt();
        mAvailableId = in.readInt();
        mStyleId = in.readInt();
        mIsOrganic = (in.readInt() == 0) ? false : true;
        mServingTemperature = in.readString();
        mServingTemperatureDisplay = in.readString();
        mOriginalGravity = in.readDouble();
        mLabelsIcon = in.readString();
        mLabelsMedium = in.readString();
        mLabelsLarge = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mNameDisplay);
        parcel.writeString(mDescription);
        parcel.writeDouble(mAbv);
        parcel.writeInt(mGlasswareId);
        parcel.writeInt(mSrmId);
        parcel.writeInt(mAvailableId);
        parcel.writeInt(mStyleId);
        parcel.writeInt(mIsOrganic ? 1 : 0);
        parcel.writeString(mServingTemperature);
        parcel.writeString(mServingTemperatureDisplay);
        parcel.writeDouble(mOriginalGravity);
        parcel.writeString(mLabelsIcon);
        parcel.writeString(mLabelsMedium);
        parcel.writeString(mLabelsLarge);
    }

    public static final Creator<Beer> CREATOR = new Parcelable.Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel parcel) {
            return new Beer(parcel);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
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

    public String getNameDisplay() {
        return mNameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        mNameDisplay = nameDisplay;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getAbv() {
        return mAbv;
    }

    public void setAbv(double abv) {
        mAbv = abv;
    }

    public int getGlasswareId() {
        return mGlasswareId;
    }

    public void setGlasswareId(int glasswareId) {
        mGlasswareId = glasswareId;
    }

    public int getSrmId() {
        return mSrmId;
    }

    public void setSrmId(int srmId) {
        mSrmId = srmId;
    }

    public int getAvailableId() {
        return mAvailableId;
    }

    public void setAvailableId(int availableId) {
        mAvailableId = availableId;
    }

    public int getStyleId() {
        return mStyleId;
    }

    public void setStyleId(int styleId) {
        mStyleId = styleId;
    }

    public boolean isOrganic() {
        return mIsOrganic;
    }

    public void setOrganic(boolean organic) {
        mIsOrganic = organic;
    }

    public String getServingTemperature() {
        return mServingTemperature;
    }

    public void setServingTemperature(String servingTemperature) {
        mServingTemperature = servingTemperature;
    }

    public String getServingTemperatureDisplay() {
        return mServingTemperatureDisplay;
    }

    public void setServingTemperatureDisplay(String servingTemperatureDisplay) {
        mServingTemperatureDisplay = servingTemperatureDisplay;
    }

    public double getOriginalGravity() {
        return mOriginalGravity;
    }

    public void setOriginalGravity(double originalGravity) {
        mOriginalGravity = originalGravity;
    }

    public String getLabelsIcon() {
        return mLabelsIcon;
    }

    public void setLabelsIcon(String labelsIcon) {
        mLabelsIcon = labelsIcon;
    }

    public String getLabelsMedium() {
        return mLabelsMedium;
    }

    public void setLabelsMedium(String labelsMedium) {
        mLabelsMedium = labelsMedium;
    }

    public String getLabelsLarge() {
        return mLabelsLarge;
    }

    public void setLabelsLarge(String labelsLarge) {
        mLabelsLarge = labelsLarge;
    }
}
