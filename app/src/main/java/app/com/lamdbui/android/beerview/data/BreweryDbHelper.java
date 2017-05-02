package app.com.lamdbui.android.beerview.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lamdbui on 5/2/17.
 */

public class BreweryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "breweries.db";

//    private String mId;
//    private String mName;
//    private String mNameShort;
//    private String mDescription;
//    private String mWebsite;
//    private String mHoursOfOperation;
//    private int mEstablished;
//    private boolean mIsOrganic;
//    private String mImagesIcon;
//    private String mImagesMedium;
//    private String mImagesLarge;
//    private String mImagesSquareMedium;
//    private String mImagesSquareLarge;
//    private String mStreetAddress;
//    private String mLocality;
//    private String mRegion;
//    private String mPostalCode;
//    private String mPhone;
//    private double mLatitude;
//    private double mLongitude;
//    private boolean mIsPrimary;
//    private boolean mIsPlanning;
//    private boolean mIsClosed;
//    private boolean mOpenToPublic;
//    private String mLocationType;
//    private String mLocationTypeDisplay;
//    private String mCountryIsoCode;

    //final String SQL_CREATE_BREWERY_TABLE = "CREATE TABLE "

    public BreweryDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
