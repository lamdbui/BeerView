package app.com.lamdbui.android.beerview.data;

import android.content.ContentValues;
import android.database.Cursor;

import app.com.lamdbui.android.beerview.Brewery;

/**
 * Created by lamdbui on 5/10/17.
 */

public class BreweryDbUtils {

    public static ContentValues convertBreweryToContentValues(Brewery brewery) {

        // return if we get a bad Brewery object
        if(brewery == null)
            return null;

        ContentValues breweryValues = new ContentValues();

        breweryValues.put(BreweryContract.BreweryTable.COLS.FAVORITE, brewery.isFavorite());

        breweryValues.put(BreweryContract.BreweryTable.COLS.ID, brewery.getId());
        breweryValues.put(BreweryContract.BreweryTable.COLS.NAME, brewery.getName());
        breweryValues.put(BreweryContract.BreweryTable.COLS.NAME_SHORT, brewery.getNameShort());
        breweryValues.put(BreweryContract.BreweryTable.COLS.DESCRIPTION, brewery.getDescription());
        breweryValues.put(BreweryContract.BreweryTable.COLS.WEBSITE, brewery.getWebsite());
        breweryValues.put(BreweryContract.BreweryTable.COLS.HOURS_OF_OPERATION, brewery.getHoursOfOperation());
        breweryValues.put(BreweryContract.BreweryTable.COLS.ESTABLISHED, brewery.getEstablished());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_ORGANIC, brewery.isOrganic());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_ICON, brewery.getImagesIcon());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_MEDIUM, brewery.getImagesMedium());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_LARGE, brewery.getImagesLarge());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_MEDIUM, brewery.getImagesSquareMedium());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_LARGE, brewery.getImagesSquareLarge());
        breweryValues.put(BreweryContract.BreweryTable.COLS.ADDRESS, brewery.getStreetAddress());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCALITY, brewery.getLocality());
        breweryValues.put(BreweryContract.BreweryTable.COLS.REGION, brewery.getRegion());
        breweryValues.put(BreweryContract.BreweryTable.COLS.POSTAL_CODE, brewery.getPostalCode());
        breweryValues.put(BreweryContract.BreweryTable.COLS.PHONE, brewery.getPhone());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LATITUDE, brewery.getLatitude());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LONGITUDE, brewery.getLongitude());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_PRIMARY, brewery.isPrimary());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_PLANNING, brewery.isPlanning());
        breweryValues.put(BreweryContract.BreweryTable.COLS.IS_CLOSED, brewery.isClosed());
        breweryValues.put(BreweryContract.BreweryTable.COLS.OPEN_TO_PUBLIC, brewery.isOpenToPublic());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCATION_TYPE, brewery.getLocationType());
        breweryValues.put(BreweryContract.BreweryTable.COLS.LOCATION_TYPE_DISPLAY, brewery.getLocationTypeDisplay());
        breweryValues.put(BreweryContract.BreweryTable.COLS.COUNTRY_ISO_CODE, brewery.getCountryIsoCode());

//        // from the Brewery class
//        public static final String ID = "id";
//        public static final String NAME = "name";
//        public static final String NAME_SHORT = "name_short";
//        public static final String DESCRIPTION = "description";
//        public static final String WEBSITE = "website";
//        public static final String HOURS_OF_OPERATION = "hours_of_operation";
//        public static final String ESTABLISHED = "established";
//        public static final String IS_ORGANIC = "is_organic";
//        public static final String IMAGES_ICON = "images_icon";
//        public static final String IMAGES_MEDIUM = "images_medium";
//        public static final String IMAGES_LARGE = "images_large";
//        public static final String IMAGES_SQUARE_MEDIUM = "images_square_medium";
//        public static final String IMAGES_SQUARE_LARGE = "images_square_large";
//        public static final String ADDRESS = "address";
//        public static final String LOCALITY = "locality";
//        public static final String REGION = "region";
//        public static final String POSTAL_CODE = "postal_code";
//        public static final String PHONE = "phone";
//        public static final String LATITUDE = "latitude";
//        public static final String LONGITUDE = "longitude";
//        public static final String IS_PRIMARY = "is_primary";
//        public static final String IS_PLANNING = "is_planning";
//        public static final String IS_CLOSED = "is_closed";
//        public static final String OPEN_TO_PUBLIC = "open_to_public";
//        public static final String LOCATION_TYPE = "location_type";
//        public static final String LOCATION_TYPE_DISPLAY = "location_tyoe_display";
//        public static final String COUNTRY_ISO_CODE = "country_iso_code";

        return breweryValues;
    }

    public static Brewery convertCursorToBrewery(Cursor c) {

        Brewery brewery = new Brewery();

        //int favorite = c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.FAVORITE));
        brewery.setFavorite(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.FAVORITE))));

        brewery.setId(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ID)));
        brewery.setName(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.NAME)));
        brewery.setNameShort(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.NAME_SHORT)));
        brewery.setDescription(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.DESCRIPTION)));
        brewery.setWebsite(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.WEBSITE)));
        brewery.setHoursOfOperation(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.HOURS_OF_OPERATION)));
        brewery.setEstablished(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ESTABLISHED)));
        //int organic = c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_ORGANIC));
        brewery.setOrganic(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_ORGANIC))));
        brewery.setImagesIcon(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_ICON)));
        brewery.setImagesMedium(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_MEDIUM)));
        brewery.setImagesLarge(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_LARGE)));
        brewery.setImagesSquareMedium(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_MEDIUM)));
        brewery.setImagesSquareLarge(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IMAGES_SQUARE_LARGE)));
        brewery.setStreetAddress(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ADDRESS)));
        brewery.setLocality(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCALITY)));
        brewery.setRegion(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.REGION)));
        brewery.setPostalCode(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.POSTAL_CODE)));
        brewery.setPhone(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.PHONE)));
        brewery.setLatitude(c.getDouble(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LATITUDE)));
        brewery.setLongitude(c.getDouble(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LONGITUDE)));
        //int primary = c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_PRIMARY));
        brewery.setPrimary(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_PRIMARY))));
        brewery.setPlanning(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_PLANNING))));
        brewery.setClosed(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.IS_CLOSED))));
        brewery.setOpenToPublic(convertIntToBoolean(c.getInt(c.getColumnIndex(BreweryContract.BreweryTable.COLS.OPEN_TO_PUBLIC))));
        brewery.setLocationType(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCATION_TYPE)));
        brewery.setLocationTypeDisplay(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.LOCATION_TYPE_DISPLAY)));
        brewery.setCountryIsoCode(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.COUNTRY_ISO_CODE)));

        return brewery;
    }

    // This assumes true(1) and false(0)
    private static boolean convertIntToBoolean(int i) {
        return (i == 1) ? true : false;
    }
}
