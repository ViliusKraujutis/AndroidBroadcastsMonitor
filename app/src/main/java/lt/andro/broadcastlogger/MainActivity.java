package lt.andro.broadcastlogger;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import lt.andro.broadcastlogger.contentprovider.BroadcastContentProvider;
import lt.andro.broadcastlogger.db.BroadcastTable;

import static lt.andro.broadcastlogger.ServiceManagerKt.toggleService;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Intent mService;
    private ListView mMainListView;
    private SimpleCursorAdapter mSimpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        LoaderManager supportLoaderManager = getSupportLoaderManager();
        supportLoaderManager.initLoader(0, null, this);

        mMainListView = findViewById(R.id.mainListView);
        fillData();

        mService = new Intent(this, BroadcastMonitoringService.class);
        toggleService(this, mService);
    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[]{
                BroadcastTable.COLUMN_ACTION, BroadcastTable.COLUMN_EXTRAS,
                BroadcastTable.COLUMN_TIMESTAMP
        };
        // Fields on the UI to which we map
        int[] to = new int[]{
                R.id.broadcastAction, R.id.broadcastExtras, R.id.broadcastTimestamp
        };

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
        mSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_broadcast, null, from,
                to, 0);

        mMainListView.setAdapter(mSimpleCursorAdapter);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.actionbarsherlock.app.SherlockActivity#onCreateOptionsMenu(com.actionbarsherlock.view
     * .Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        getMenuInflater().inflate(R.menu.activity_main, pMenu);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Watson#onMenuItemSelected(int,
     * com.actionbarsherlock.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menuClean:
                Log.d(TAG, "Starting service");
                getContentResolver().delete(BroadcastContentProvider.CONTENT_URI, null, null);
                return true;
            case R.id.menuToggle:
                Log.d(TAG, "Toggling service");
                pItem.setTitle(getString(
                        toggleService(this, mService) ? R.string.menu_stop : R.string.menu_start
                ));
                return true;
            case R.id.menuSettings:
                // TODO enable and implement Settings
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(pItem);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.LoaderManager.LoaderCallbacks#onCreateLoader(int,
     * android.os.Bundle)
     */
    @Override
    public Loader<Cursor> onCreateLoader(int pArg0, Bundle pArg1) {
        String[] projection = {
                BroadcastTable.COLUMN_ID, BroadcastTable.COLUMN_ACTION,
                BroadcastTable.COLUMN_EXTRAS, BroadcastTable.COLUMN_TIMESTAMP
        };
        CursorLoader cursorLoader = new CursorLoader(this, BroadcastContentProvider.CONTENT_URI,
                projection, null, null, BroadcastTable.COLUMN_TIMESTAMP + " DESC");
        return cursorLoader;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.LoaderManager.LoaderCallbacks#onLoadFinished(android.support.v4.content
     * .Loader, java.lang.Object)
     */
    @Override
    public void onLoadFinished(Loader<Cursor> pArg0, Cursor pNewCursor) {
        Log.d(TAG, "onLoadFinished");
        mSimpleCursorAdapter.swapCursor(pNewCursor);
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.LoaderManager.LoaderCallbacks#onLoaderReset(android.support.v4.content
     * .Loader)
     */
    @Override
    public void onLoaderReset(Loader<Cursor> pArg0) {
        Log.d(TAG, "onLoadReset");
        mSimpleCursorAdapter.swapCursor(null);
    }

}
