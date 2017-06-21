package app.com.lamdbui.android.beerview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by lamdbui on 6/19/17.
 */

public class Address implements Parcelable {
    private String mStreetNumber;
    private String mStreetName;
    private String mNeighborhood;
    private String mCity;
    private String mCounty;
    private String mState;
    private String mCountry;
    private String mPostalCode;
    private LatLng mLatLng;
    private LatLngBounds mLatLngBounds;

    public Address() {
        mStreetNumber = null;
        mStreetName = null;
        mNeighborhood = null;
        mCity = null;
        mCounty = null;
        mState = null;
        mCountry = null;
        mPostalCode = null;
        mLatLng = null;
        mLatLngBounds = null;
    }

    private Address(Parcel in) {
        mStreetNumber = in.readString();
        mStreetName = in.readString();
        mNeighborhood = in.readString();
        mCity = in.readString();
        mCounty = in.readString();
        mState = in.readString();
        mCountry = in.readString();
        mPostalCode = in.readString();
        mLatLng = new LatLng(in.readDouble(), in.readDouble());
        LatLng southwest = new LatLng(in.readDouble(), in.readDouble());
        LatLng northeast = new LatLng(in.readDouble(), in.readDouble());
        mLatLngBounds = new LatLngBounds(southwest, northeast);
    }

    public static final Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel parcel) {
            return new Address(parcel);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mStreetNumber);
        parcel.writeString(mStreetName);
        parcel.writeString(mNeighborhood);
        parcel.writeString(mCity);
        parcel.writeString(mCounty);
        parcel.writeString(mState);
        parcel.writeString(mCountry);
        parcel.writeString(mPostalCode);
        parcel.writeDouble(mLatLng.latitude);
        parcel.writeDouble(mLatLng.longitude);
        parcel.writeDouble(mLatLngBounds.southwest.latitude);
        parcel.writeDouble(mLatLngBounds.southwest.longitude);
        parcel.writeDouble(mLatLngBounds.northeast.latitude);
        parcel.writeDouble(mLatLngBounds.northeast.longitude);
    }

    public String getStreetNumber() {
        return mStreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        mStreetNumber = streetNumber;
    }

    public String getStreetName() {
        return mStreetName;
    }

    public void setStreetName(String streetName) {
        mStreetName = streetName;
    }

    public String getNeighborhood() {
        return mNeighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        mNeighborhood = neighborhood;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCounty() {
        return mCounty;
    }

    public void setCounty(String county) {
        mCounty = county;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public LatLngBounds getLatLngBounds() {
        return mLatLngBounds;
    }

    public void setLatLngBounds(LatLngBounds latLngBounds) {
        mLatLngBounds = latLngBounds;
    }
}
