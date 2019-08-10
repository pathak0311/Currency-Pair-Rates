package com.example.harshit.stockprices3;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class StockLoader extends AsyncTaskLoader<List<StockData>> {
    private static final String LOG_TAG = StockLoader.class.getName();
    private String mUrl;

    public StockLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<StockData> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<StockData> stocks = QueryUtils.fetchEarthquakeData(mUrl);
        return stocks;
    }
}
