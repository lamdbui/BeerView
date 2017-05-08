package app.com.lamdbui.android.beerview.data;

import android.net.Uri;

/**
 * Created by lamdbui on 5/2/17.
 */

public class BreweryContract {

    public static final String CONTENT_AUTHORITY = "app.com.lamdbui.android.beerview";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // possible paths
    public static final String PATH_BREWERIES = "breweries";
}
