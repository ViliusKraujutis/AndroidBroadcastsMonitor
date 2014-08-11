/**
 *
 */

package lt.andro.broadcastlogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import lt.andro.broadcastlogger.contentprovider.BroadcastContentProvider;
import lt.andro.broadcastlogger.db.BroadcastTable;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * {@link AnyBroadcastReceiver}<br/>
 * <br/>
 *
 * @author Vilius Kraujutis
 * @since Dec 23, 2012 3:30:11 AM
 */
public class AnyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = AnyBroadcastReceiver.class.getSimpleName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static final String EXTRA_ACTION = "EXTRA_ACTION";
    public static final String EXTRA_EXTRAS = "EXTRA_EXTRAS";

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        if (pIntent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            pContext.startService(new Intent(pContext, BroadcastMonitoringService.class));
        }

        Log.d(TAG, "Received Broadcast's intent is: " + pIntent.toString());
        String action = pIntent.getAction();
        String extrasString = getExtrasString(pIntent);

        saveReceivedBroadcastDetails(pContext, action, extrasString);

        // TODO implement setting so this would be optional vibrate(pContext);

        BroadcastMonitoringService.showNotification(pContext, action);
    }

    /**
     * @param pContext
     * @param pAction
     * @param pExtrasString
     */
    private void saveReceivedBroadcastDetails(Context pContext, String pAction, String pExtrasString) {
        ContentValues values = new ContentValues();
        values.put(BroadcastTable.COLUMN_ACTION, pAction);
        values.put(BroadcastTable.COLUMN_EXTRAS, pExtrasString);
        values.put(BroadcastTable.COLUMN_TIMESTAMP,
                dateFormat.format(Calendar.getInstance().getTime()));

        pContext.getContentResolver().insert(BroadcastContentProvider.CONTENT_URI, values);
        Log.i(TAG, "Saved broadcast: Action: " + pAction  + ", Extras: " + pExtrasString + ", Time: " + dateFormat.format(Calendar.getInstance().getTime()));
    }

    private String getExtrasString(Intent pIntent) {
        String extrasString = "";
        Bundle extras = pIntent.getExtras();
        try {
            if (extras != null) {
                Set<String> keySet = extras.keySet();
                for (String key : keySet) {
                    try {
                        String extraValue = pIntent.getExtras().get(key).toString();
                        extrasString += key + ": " + extraValue + "\n";
                    } catch (Exception e) {
                        Log.d(TAG, "Exception 2 in getExtrasString(): " + e.toString());
                        extrasString += key + ": Exception:" + e.getMessage() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in getExtrasString(): " + e.toString());
            extrasString += "Exception:" + e.getMessage() + "\n";
        }
        Log.d(TAG, "extras=" + extrasString);
        return extrasString;
    }

    // private void vibrate(Context pContext) {
    // Vibrator vibrator = (Vibrator)pContext.getSystemService(Service.VIBRATOR_SERVICE);
    // vibrator.vibrate(30);
    // }
}
