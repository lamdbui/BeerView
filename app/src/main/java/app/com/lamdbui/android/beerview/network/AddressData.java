package app.com.lamdbui.android.beerview.network;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.lamdbui.android.beerview.model.Address;

/**
 * Created by lamdbui on 6/19/17.
 */

public class AddressData {

    @SerializedName("address_components")
    List<AddressComponent> mAddressComponents;
    @SerializedName("formatted_address")
    String mFormattedAddress;
    @SerializedName("geometry")
    GeometryComponent mGeometry;

    private class AddressComponent {
        @SerializedName("long_name")
        String mLongName;
        @SerializedName("short_name")
        String mShortName;
        @SerializedName("types")
        List<String> mTypes;

        public String getLongName() {
            return mLongName;
        }

        public String getShortName() {
            return mShortName;
        }

        public List<String> getTypes() {
            return mTypes;
        }
    }

    private class GeometryComponent {
        @SerializedName("bounds")
        BoundsLatitudeLongitude mBounds;
        @SerializedName("location")
        LatitudeLongitude mLocation;
        @SerializedName("location_type")
        String mLocationType;
        @SerializedName("viewport")
        BoundsLatitudeLongitude mViewport;
    }

    private class BoundsLatitudeLongitude {
        @SerializedName("northeast")
        LatitudeLongitude mNortheast;
        @SerializedName("southwest")
        LatitudeLongitude mSouthwest;
    }

    private class LatitudeLongitude {
        @SerializedName("lat")
        double mLatitude;
        @SerializedName("lng")
        double mLongitude;
    }

    private AddressComponent getAddressComponent(String componentName) {
        for(AddressComponent ac : mAddressComponents) {
            if(ac.mTypes.contains(componentName)) {
                return ac;
            }
        }
        return null;
    }

    public Address convertAddressDataToAddress() {
        Address address = new Address();

        AddressComponent currentComponent = getAddressComponent("street_number");
        if(currentComponent != null)
            address.setStreetNumber(currentComponent.getLongName());

        // 'neighborhood' or 'locality' might be available
        currentComponent = getAddressComponent("locality");
        if(currentComponent != null)
            address.setCity(currentComponent.getLongName());
        if(address.getCity() == null) {
            currentComponent = getAddressComponent("neighborhood");
            if (currentComponent != null) {
                address.setCity(currentComponent.getLongName());
            }
        }

        currentComponent = getAddressComponent("administrative_area_level_1");
        if(currentComponent != null)
            address.setState(currentComponent.getLongName());

        currentComponent = getAddressComponent("postal_code");
        if(currentComponent != null)
            address.setPostalCode(currentComponent.getLongName());

        if(mGeometry != null) {
            address.setLatLng(new LatLng(mGeometry.mLocation.mLatitude, mGeometry.mLocation.mLongitude));
            if(mGeometry.mBounds != null) {
                LatLng northeastBound = new LatLng(mGeometry.mBounds.mNortheast.mLatitude, mGeometry.mBounds.mNortheast.mLongitude);
                LatLng southwestBound = new LatLng(mGeometry.mBounds.mSouthwest.mLatitude, mGeometry.mBounds.mSouthwest.mLongitude);
                address.setLatLngBounds(new LatLngBounds(southwestBound, northeastBound));
            }
        }

        return address;
    }
}
