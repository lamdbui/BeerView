package app.com.lamdbui.android.beerview.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Address;

/**
 * Created by lamdbui on 6/19/17.
 */

public class AddressResponse {

    @SerializedName("results")
    List<AddressData> mAddressDataList;

    public List<Address> getAddressList() {
        List<Address> addressList = new ArrayList<>();

        for(AddressData ad : mAddressDataList) {
            Address address = ad.convertAddressDataToAddress();
            addressList.add(address);
        }

        return addressList;
    }
}
