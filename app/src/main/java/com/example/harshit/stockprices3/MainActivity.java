package com.example.harshit.stockprices3;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<StockData>> {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static String REQUEST_URL =
            "https://forex.1forge.com/1.0.3/quotes?pairs=EURUSD,GBPJPY,AUDUSD&api_key=VEAlvRsjpxvKoLV7nw66PNO7OuSyGJLw";

    private static final int LOADER_ID = 1;

    private StockAdapter mAdapter;

    private TextView mEmptyStateTextView;

    //private String[] stockNames = {"AAPL", "GOOGL", "MSFT", "AMZN", "FB", "BRK-A", "BABA", "JNJ", "JPM", "XOM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView stockListView = (ListView) findViewById(R.id.list);
        mAdapter = new StockAdapter(this, new ArrayList<StockData>());
        stockListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        stockListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<StockData>> onCreateLoader(int id, Bundle bundle) {
        return new StockLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<StockData>> loader, List<StockData> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_stocks);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<StockData>> loader) {
        mAdapter.clear();
    }
}
