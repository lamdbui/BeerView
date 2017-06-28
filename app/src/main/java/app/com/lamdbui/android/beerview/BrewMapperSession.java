package app.com.lamdbui.android.beerview;

import android.content.Context;

/**
 * Created by lamdbui on 6/28/17.
 */

public class BrewMapperSession {

    private static BrewMapperSession sBrewMapperSession;

    private Context mContext;

    private String mPostalCode;

    public static BrewMapperSession get(Context context) {
        if(sBrewMapperSession == null) {
            sBrewMapperSession = new BrewMapperSession(context);
        }
        return sBrewMapperSession;
    }

    private BrewMapperSession(Context context) {
        mContext = context;
        mPostalCode = "";
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }
}
