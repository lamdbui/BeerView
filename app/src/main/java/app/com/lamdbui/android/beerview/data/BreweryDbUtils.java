package app.com.lamdbui.android.beerview.data;

import android.content.ContentValues;
import android.database.Cursor;

import app.com.lamdbui.android.beerview.BreweryLocation;

/**
 * Created by lamdbui on 5/10/17.
 */

public class BreweryDbUtils {

    public static ContentValues convertBreweryToContentValues(BreweryLocation breweryLocation) {

        // return if we get a bad BreweryLocation object
        if(breweryLocation == null)
            return null;

        ContentValues breweryValues = new ContentValues();

        breweryValues.put(BreweryContract.BreweryTable.COLS.FAVORITE, breweryLocation.isFavorite());

        breweryValues.put(BreweryContract.BreweryTable.COLS.ID, breweryLocation.getId());
        breweryValues.put(BreweryContract.BreweryTable.COLS.NAME, breweryLocation.getName());
        breweryValues.put(BreweryContract.BreweryTable.COLS.NAME_SHORT, breweryLocation.getNameShort());
        breweryValues.put(BreweryContract.BreweryTable.COLS.DESCRIPTION, breweryLocation.getDescription());
        breweryValues.put(BreweryContract.BreweryTable.COLS.WEBSITE, breweryLocation.getWebsite());
        breweryValues.put(BreweryContract.BreweryTable.COLS.HOURS_OF_OPERATION, breweryLocation.getHoursOfOperation());
        breweryValues.put(BreweryContract.BreweryTable.COLS.ESTABLISHED, breweryLocation.getEstablished());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_ORGANIC, breweryLocation.isOrganic());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_ICON, breweryLocation.getImagesIcon());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_MEDIUM, breweryLocation.getImagesMedium());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_LARGE, breweryLocation.getImagesLarge());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_MEDIUM, breweryLocation.getImagesSquareMedium());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_LARGE, breweryLocation.getImagesSquareLarge());
        breweryValues.put(BreweryContract.BreweryTable.COLS.ADDRESS, breweryLocation.getStreetAddress());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCALITY, breweryLocation.getLocality());
        breweryValues.put(BreweryContract.BreweryTable.COLS.REGION, breweryLocation.getRegion());
        breweryValues.put(BreweryContract.BreweryTable.COLS.POSTAL_CODE, breweryLocation.getPostalCode());
        breweryValues.put(BreweryContract.BreweryTable.COLS.PHONE, breweryLocation.getPhone());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LATITUDE, breweryLocation.getLatitude());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LONGITUDE, breweryLocation.getLongitude());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_PRIMARY, breweryLocation.isPrimary());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_PLANNING, breweryLocation.isPlanning());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_CLOSED, breweryLocation.isClosed());
        breweryValues.put(BreweryContract.BreweryTable.COLS.OPEN_TO_PUBLIC, breweryLocation.isOpenToPublic());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCATION_TYPE, breweryLocation.getLocationType());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCATION_TYPE_DISPLAY, breweryLocation.getLocationTypeDisplay());
        breweryValues.put(BreweryContract.BreweryTable.COLS.COUNTRY_ISO_CODE, breweryLocation.getCountryIsoCode());

        return breweryValues;
    }

    public static BreweryLocation convertCursorToBrewery(Cursor c) {

        BreweryLocation breweryLocation = new BreweryLocation();

        breweryLocation.setFavorite(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.FAVORITE))));

        breweryLocation.setId(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ID)));
        breweryLocation.setName(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.NAME)));
        breweryLocation.setNameShort(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.NAME_SHORT)));
        breweryLocation.setDescription(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.DESCRIPTION)));
        breweryLocation.setWebsite(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.WEBSITE)));
        breweryLocation.setHoursOfOperation(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.HOURS_OF_OPERATION)));
        breweryLocation.setEstablished(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ESTABLISHED)));
        breweryLocation.setOrganic(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_ORGANIC))));
        breweryLocation.setImagesIcon(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_ICON)));
        breweryLocation.setImagesMedium(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_MEDIUM)));
        breweryLocation.setImagesLarge(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_LARGE)));
        breweryLocation.setImagesSquareMedium(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_MEDIUM)));
        breweryLocation.setImagesSquareLarge(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_LARGE)));
        breweryLocation.setStreetAddress(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ADDRESS)));
        breweryLocation.setLocality(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCALITY)));
        breweryLocation.setRegion(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.REGION)));
        breweryLocation.setPostalCode(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.POSTAL_CODE)));
        breweryLocation.setPhone(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.PHONE)));
        breweryLocation.setLatitude(c.getDouble(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LATITUDE)));
        breweryLocation.setLongitude(c.getDouble(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LONGITUDE)));
        breweryLocation.setPrimary(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_PRIMARY))));
        breweryLocation.setPlanning(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_PLANNING))));
        breweryLocation.setClosed(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_CLOSED))));
        breweryLocation.setOpenToPublic(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.OPEN_TO_PUBLIC))));
        breweryLocation.setLocationType(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCATION_TYPE)));
        breweryLocation.setLocationTypeDisplay(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCATION_TYPE_DISPLAY)));
        breweryLocation.setCountryIsoCode(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.COUNTRY_ISO_CODE)));

        return breweryLocation;
    }

    // This assumes true(1) and false(0)
    private static boolean convertIntToBoolean(int i) {
        return (i == 1) ? true : false;
    }
}
