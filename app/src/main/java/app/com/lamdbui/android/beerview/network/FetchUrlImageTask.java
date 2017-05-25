package app.com.lamdbui.android.beerview.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.R.attr.bitmap;

/**
 * Created by lamdbui on 5/25/17.
 */

public class FetchUrlImageTask extends AsyncTask<String, Void, Bitmap> {

    public static final String LOG_TAG = FetchUrlImageTask.class.getSimpleName();

    // callback access from the caller
    private OnCompletedFetchUrlImageTaskListener mCallback;

    // Calling Activity or Fragment must implement this interface
    public interface OnCompletedFetchUrlImageTaskListener {
        public void completedFetchUrlImageTask(Bitmap bitmap);
    }

    public FetchUrlImageTask(OnCompletedFetchUrlImageTaskListener callback) {
        super();

        // attach callback from caller
        mCallback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String imageUrl = url[0];

        Bitmap image = null;

        try {
            InputStream input = new URL(imageUrl).openStream();
            image = BitmapFactory.decodeStream(input);
        }
        catch(IOException e) {
            Log.e(LOG_TAG, "Error fetching the image from the server: " + e.getMessage().toString());
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mCallback.completedFetchUrlImageTask(bitmap);
    }
}
