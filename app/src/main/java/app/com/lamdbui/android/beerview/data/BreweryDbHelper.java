package app.com.lamdbui.android.beerview.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.lamdbui.android.beerview.data.BreweryContract.BeerTable;
import app.com.lamdbui.android.beerview.data.BreweryContract.BreweryTable;

/**
 * Created by lamdbui on 5/2/17.
 */

public class BreweryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "brewery.db";

    public BreweryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

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
                + BreweryTable.COLS.COUNTRY_ISO_CODE + " TEXT, "
                // additional association fields
                + BreweryTable.COLS.FAVORITE + " TEXT "
                + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_BREWERY_TABLE);

        // Create table for the Beer
        final String SQL_CREATE_BEER_TABLE = "CREATE TABLE "
                + BeerTable.TABLE_NAME + " ("
                + BeerTable._ID + " TEXT PRIMARY KEY, "
                + BeerTable.COLS.ID + " TEXT UNIQUE NOT NULL, "
                + BeerTable.COLS.NAME + " TEXT NOT NULL, "
                + BeerTable.COLS.NAME_DISPLAY + " TEXT, "
                + BeerTable.COLS.DESCRIPTION + " TEXT, "
                + BeerTable.COLS.ABV + " REAL, "
                + BeerTable.COLS.GLASSWARE_ID + " INTEGER, "
                + BeerTable.COLS.SRM_ID + " INTEGER, "
                + BeerTable.COLS.AVAILABLE_ID + " INTEGER, "
                + BeerTable.COLS.STYLE_ID + " INTEGER, "
                + BeerTable.COLS.IS_ORGANIC + " INTEGER, "
                + BeerTable.COLS.SERVING_TEMPERATURE + " TEXT, "
                + BeerTable.COLS.SERVING_TEMPERATURE_DISPLAY + " TEXT, "
                + BeerTable.COLS.ORIGINAL_GRAVITY + " REAL, "
                + BeerTable.COLS.LABELS_ICON + " TEXT, "
                + BeerTable.COLS.LABELS_MEDIUM + " TEXT, "
                + BeerTable.COLS.LABELS_LARGE + " TEXT, "
                // additional association fields
                + BeerTable.COLS.FAVORITE + " TEXT "
                + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_BEER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop and recreate the tables for now.
        // we probably want to do something better in the future...
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BreweryTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
