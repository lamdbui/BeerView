package app.com.lamdbui.android.beerview.data;

import android.content.ContentValues;
import android.database.Cursor;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.BreweryLocation;

/**
 * Created by lamdbui on 5/10/17.
 */

public class BreweryDbUtils {

    public static ContentValues convertBeerToContentValues(Beer beer) {

        // return if we get a bad Beer object
        if(beer == null)
            return null;

        ContentValues beerValues = new ContentValues();
        // TODO: Implement convertBeerToContentValues

//        // extra field to identify associations
//        public static final String FAVORITE = "favorite";
//
//        // from the Beer class
//        public static final String ID = "id";
//        public static final String NAME = "name";
//        public static final String NAME_DISPLAY = "name_display";
//        public static final String DESCRIPTION = "description";
//        public static final String ABV = "abv";
//        public static final String GLASSWARE_ID = "glassware_id";
//        public static final String SRM_ID = "srm_id";
//        public static final String AVAILABLE_ID = "available_id";
//        public static final String STYLE_ID = "style_id";
//        public static final String IS_ORGANIC = "is_organic";
//        public static final String SERVING_TEMPERATURE = "serving_temperature";
//        public static final String SERVING_TEMPERATURE_DISPLAY = "serving_temperature_display";
//        public static final String ORIGINAL_GRAVITY = "orginal_gravity";
//        public static final String LABELS_ICON = "labels_icon";
//        public static final String LABELS_MEDIUM = "labels_medium";
//        public static final String LABELS_LARGE = "labels_large";

        beerValues.put(BreweryContract.BeerTable.COLS.ID, beer.getId());
        beerValues.put(BreweryContract.BeerTable.COLS.NAME, beer.getName());
        beerValues.put(BreweryContract.BeerTable.COLS.NAME_DISPLAY, beer.getNameDisplay());
        beerValues.put(BreweryContract.BeerTable.COLS.DESCRIPTION, beer.getDescription());
        beerValues.put(BreweryContract.BeerTable.COLS.ABV, beer.getAbv());
        beerValues.put(BreweryContract.BeerTable.COLS.GLASSWARE_ID, beer.getGlasswareId());
        beerValues.put(BreweryContract.BeerTable.COLS.SRM_ID, beer.getSrmId());
        beerValues.put(BreweryContract.BeerTable.COLS.AVAILABLE_ID, beer.getAvailableId());
        beerValues.put(BreweryContract.BeerTable.COLS.STYLE_ID, beer.getStyleId());
        beerValues.put(BreweryContract.BeerTable.COLS.IS_ORGANIC, beer.isOrganic());
        beerValues.put(BreweryContract.BeerTable.COLS.SERVING_TEMPERATURE, beer.getServingTemperature());
        beerValues.put(BreweryContract.BeerTable.COLS.SERVING_TEMPERATURE_DISPLAY, beer.getServingTemperatureDisplay());
        beerValues.put(BreweryContract.BeerTable.COLS.ORIGINAL_GRAVITY, beer.getOriginalGravity());
        beerValues.put(BreweryContract.BeerTable.COLS.LABELS_ICON, beer.getLabelsIcon());
        beerValues.put(BreweryContract.BeerTable.COLS.LABELS_MEDIUM, beer.getLabelsMedium());
        beerValues.put(BreweryContract.BeerTable.COLS.LABELS_LARGE, beer.getLabelsLarge());

        return beerValues;
    }

    public static Beer convertCursorToBeer(Cursor c) {
        Beer beer = new Beer();

        // from the Beer class
//        public static final String ID = "id";
//        public static final String NAME = "name";
//        public static final String NAME_DISPLAY = "name_display";
//        public static final String DESCRIPTION = "description";
//        public static final String ABV = "abv";
//        public static final String GLASSWARE_ID = "glassware_id";
//        public static final String SRM_ID = "srm_id";
//        public static final String AVAILABLE_ID = "available_id";
//        public static final String STYLE_ID = "style_id";
//        public static final String IS_ORGANIC = "is_organic";
//        public static final String SERVING_TEMPERATURE = "serving_temperature";
//        public static final String SERVING_TEMPERATURE_DISPLAY = "serving_temperature_display";
//        public static final String ORIGINAL_GRAVITY = "orginal_gravity";
//        public static final String LABELS_ICON = "labels_icon";
//        public static final String LABELS_MEDIUM = "labels_medium";
//        public static final String LABELS_LARGE = "labels_large";

        //breweryLocation.setId(c.getString(c.getColumnIndex(BreweryContract.BreweryTable.COLS.ID)));
        beer.setId(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.ID)));
        beer.setName(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.NAME)));
        beer.setNameDisplay(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.NAME_DISPLAY)));
        beer.setDescription(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.DESCRIPTION)));
        String abvString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.ABV));
        if(abvString != null && !abvString.isEmpty())
            beer.setAbv(Double.parseDouble(abvString));
        else
            beer.setAbv(0);
        String glasswareIdString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.GLASSWARE_ID));
        if(glasswareIdString != null && !glasswareIdString.isEmpty())
            beer.setGlasswareId(Integer.parseInt(glasswareIdString));
        else
            beer.setGlasswareId(-1);
        String srmIdString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.SRM_ID));
        if(srmIdString != null && !srmIdString.isEmpty())
            beer.setSrmId(Integer.parseInt(srmIdString));
        else
            beer.setSrmId(-1);
        String availableIdString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.AVAILABLE_ID));
        if(availableIdString != null && !availableIdString.isEmpty())
            beer.setAvailableId(Integer.parseInt(availableIdString));
        else
            beer.setAvailableId(-1);
        String styleIdString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.STYLE_ID));
        if(styleIdString != null && !styleIdString.isEmpty())
            beer.setStyleId(Integer.parseInt(styleIdString));
        else
            beer.setAvailableId(-1);
        beer.setOrganic(convertYorNtoBoolean(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.IS_ORGANIC))));
        beer.setServingTemperature(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.SERVING_TEMPERATURE)));
        beer.setServingTemperatureDisplay(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.SERVING_TEMPERATURE_DISPLAY)));
        String originalGravityString = c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.ORIGINAL_GRAVITY));
        if(originalGravityString != null && !originalGravityString.isEmpty())
            beer.setOriginalGravity(Double.parseDouble(originalGravityString));
        else
            beer.setOriginalGravity(0);
        beer.setLabelsIcon(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.LABELS_ICON)));
        beer.setLabelsMedium(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.LABELS_MEDIUM)));
        beer.setLabelsLarge(c.getString(c.getColumnIndex(BreweryContract.BeerTable.COLS.LABELS_LARGE)));

        return beer;
    }

    public static ContentValues convertBreweryLocationToContentValues(BreweryLocation breweryLocation) {

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

    private static boolean convertYorNtoBoolean(String b) { return (b.equals("Y")) ? true : false; }
}
