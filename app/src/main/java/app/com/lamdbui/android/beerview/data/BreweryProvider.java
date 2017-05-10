package app.com.lamdbui.android.beerview.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import app.com.lamdbui.android.beerview.Brewery;

/**
 * Created by lamdbui on 5/9/17.
 */

public class BreweryProvider extends ContentProvider {

    public static final int BREWERY = 100;
    public static final int BREWERY_FAVORITES = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private BreweryDbHelper mBreweryDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BreweryContract.CONTENT_AUTHORITY;

        // add our URI paths
        matcher.addURI(authority,
                BreweryContract.PATH_BREWERY,
                BREWERY);
        matcher.addURI(authority,
                BreweryContract.PATH_BREWERY + "/" + BreweryContract.SUBPATH_FAVOITES,
                BREWERY_FAVORITES);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mBreweryDbHelper = new BreweryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;

        switch(sUriMatcher.match(uri)) {
            case BREWERY:
            case BREWERY_FAVORITES:
                retCursor = mBreweryDbHelper.getReadableDatabase().query(
                        BreweryContract.BreweryTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        // tell Cursor what URI to watch so it is aware of data changes
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case BREWERY:
            case BREWERY_FAVORITES:
                return BreweryContract.BreweryTable.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mBreweryDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch(match) {
            case BREWERY:
                long _id = db.insert(BreweryContract.BreweryTable.TABLE_NAME, null, contentValues);
                if(_id > 0)
                    returnUri = BreweryContract.BreweryTable.buildBreweryUri(_id);
                else
                    throw new SQLException("Failed to insert row into: " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mBreweryDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        switch(match) {
            case BREWERY:
                rowsDeleted = db.delete(
                        BreweryContract.BreweryTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String where, @Nullable String[] whereArgs) {
        final SQLiteDatabase db = mBreweryDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsUpdated;

        switch(match) {
            case BREWERY:
                rowsUpdated = db.update(
                        BreweryContract.BreweryTable.TABLE_NAME,
                        contentValues,
                        where,
                        whereArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mBreweryDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case BREWERY:
                db.beginTransaction();
                int insertCount = 0;
                try {
                    for(ContentValues value : values) {
                        long _id = db.insert(BreweryContract.BreweryTable.TABLE_NAME, null, value);
                        if(_id != -1)
                            insertCount++;
                    }
                    db.setTransactionSuccessful();
                }
                finally {
                    db.endTransaction();
                }

                return insertCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
