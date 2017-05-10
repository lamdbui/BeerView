package app.com.lamdbui.android.beerview.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lamdbui on 5/2/17.
 */

public class BreweryContract {

    public static final String CONTENT_AUTHORITY = "app.com.lamdbui.android.beerview";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // possible paths
    public static final String PATH_BREWERY = "brewery";

    // possible subpaths
    public static final String SUBPATH_FAVOITES = "favorites";

    // class that defines the Brewery Table Schema
    public static final class BreweryTable implements BaseColumns {

        public static final String TABLE_NAME = "brewery";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BREWERY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BREWERY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BREWERY;

        public static final class COLS {

            // extra field to identify associations
            public static final String FAVORITE = "favorite";

            // from the Brewery class
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String NAME_SHORT = "name_short";
            public static final String DESCRIPTION = "description";
            public static final String WEBSITE = "website";
            public static final String HOURS_OF_OPERATION = "hours_of_operation";
            public static final String ESTABLISHED = "established";
            public static final String IS_ORGANIC = "is_organic";
            public static final String IMAGES_ICON = "images_icon";
            public static final String IMAGES_MEDIUM = "images_medium";
            public static final String IMAGES_LARGE = "images_large";
            public static final String IMAGES_SQUARE_MEDIUM = "images_square_medium";
            public static final String IMAGES_SQUARE_LARGE = "images_square_large";
            public static final String ADDRESS = "address";
            public static final String LOCALITY = "locality";
            public static final String REGION = "region";
            public static final String POSTAL_CODE = "postal_code";
            public static final String PHONE = "phone";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String IS_PRIMARY = "is_primary";
            public static final String IS_PLANNING = "is_planning";
            public static final String IS_CLOSED = "is_closed";
            public static final String OPEN_TO_PUBLIC = "open_to_public";
            public static final String LOCATION_TYPE = "location_type";
            public static final String LOCATION_TYPE_DISPLAY = "location_tyoe_display";
            public static final String COUNTRY_ISO_CODE = "country_iso_code";
        }

        public static Uri buildBreweryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
