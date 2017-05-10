package app.com.lamdbui.android.beerview.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.lamdbui.android.beerview.Brewery;
import app.com.lamdbui.android.beerview.data.BreweryContract.BreweryTable;

/**
 * Created by lamdbui on 5/2/17.
 */

public class BreweryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "brewery.db";

    public BreweryDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

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

        // Create the table for the Breweries
        final String SQL_CREATE_BREWERY_TABLE = "CREATE TABLE "
                + BreweryTable.TABLE_NAME + " ("
                + BreweryTable._ID + " INTEGER PRIMARY KEY, "
                + BreweryTable.COLS.ID + " TEXT UNIQUE NOT NULL, "
                + BreweryTable.COLS.NAME + " TEXT NOT NULL, "
                + BreweryTable.COLS.NAME_SHORT + " TEXT, "
                + BreweryTable.COLS.DESCRIPTION + " TEXT, "
                + BreweryTable.COLS.WEBSITE + " TEXT, "
                + BreweryTable.COLS.HOURS_OF_OPERATION + " TEXT, "
                + BreweryTable.COLS.ESTABLISHED + " INTEGER, "
                + BreweryTable.COLS.IS_ORGANIC + " INTEGER, "
                + BreweryTable.COLS.IMAGES_ICON + " TEXT, "
                + BreweryTable.COLS.IMAGES_MEDIUM + " TEXT, "
                + BreweryTable.COLS.IMAGES_LARGE + " TEXT, "
                + BreweryTable.COLS.IMAGES_SQUARE_MEDIUM + " TEXT, "
                + BreweryTable.COLS.IMAGES_SQUARE_LARGE + " TEXT, "
                + BreweryTable.COLS.ADDRESS + " TEXT, "
                + BreweryTable.COLS.LOCALITY + " TEXT, "
                + BreweryTable.COLS.REGION + " TEXT, "
                + BreweryTable.COLS.POSTAL_CODE + " TEXT, "
                + BreweryTable.COLS.PHONE + " TEXT, "
                + BreweryTable.COLS.LATITUDE + " REAL , "
                + BreweryTable.COLS.LONGITUDE + " REAL, "
                + BreweryTable.COLS.IS_PRIMARY + " INTEGER, "
                + BreweryTable.COLS.IS_PLANNING + " INTEGER, "
                + BreweryTable.COLS.IS_CLOSED + " INTEGER, "
                + BreweryTable.COLS.OPEN_TO_PUBLIC + " INTEGER, "
                + BreweryTable.COLS.LOCATION_TYPE + " TEXT, "
                + BreweryTable.COLS.LOCATION_TYPE_DISPLAY + " TEXT, "
                + BreweryTable.COLS.COUNTRY_ISO_CODE + " TEXT "
                + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_BREWERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop and recreate the tables for now.
        // we probably want to do something better in the future...
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BreweryTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
