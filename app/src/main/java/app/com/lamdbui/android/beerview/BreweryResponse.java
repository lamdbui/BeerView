package app.com.lamdbui.android.beerview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lamdbui on 4/26/17.
 */

public class BreweryResponse {

    @SerializedName("currentPage")
    private int mCurrentPage;
    @SerializedName("numberOfPages")
    private int mNumberofPages;
    @SerializedName("totalResults")
    private int mTotalResults;
    @SerializedName("data")
    private List<Brewery> mData;

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public int getNumberofPages() {
        return mNumberofPages;
    }

    public void setNumberofPages(int numberofPages) {
        mNumberofPages = numberofPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public List<Brewery> getData() {
        return mData;
    }

    public void setData(List<Brewery> data) {
        mData = data;
    }
}
