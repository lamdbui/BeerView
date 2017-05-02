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

//    @SerializedName("id")
//    private String mId;
//    @SerializedName("name")
//    private String mName;
//    @SerializedName("streetAddress")
//    private String mStreetAddress;
//    @SerializedName("locality")
//    private String mLocality;
//    @SerializedName("region")
//    private String mRegion;
//    @SerializedName("postalCode")
//    private String mPostalCode;
//    @SerializedName("phone")
//    private String mPhone;
//    @SerializedName("website")
//    private String mWebsite;
//    @SerializedName("latitude")
//    private double mLatitude;
//    @SerializedName("longitude")
//    private double mLongitude;
//    //    @SerializedName("brewery/established")
////    private String mEstablished;
//    @SerializedName("brewery")
//    private JsonBrewery mBrewery;


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
