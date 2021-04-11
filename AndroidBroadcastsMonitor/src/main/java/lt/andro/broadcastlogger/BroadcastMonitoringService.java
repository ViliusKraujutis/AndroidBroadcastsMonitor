package lt.andro.broadcastlogger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * {@link BroadcastMonitoringService}<br/>
 * <br/>
 *
 * @author Vilius Kraujutis
 * @since Dec 23, 2012 12:36:17 PM
 */
public class BroadcastMonitoringService extends Service {

    private static final String TAG = BroadcastMonitoringService.class.getSimpleName();
    private static final int NOTIFICATION = 1;
    private AnyBroadcastReceiver mAnyBroadcastReceiver;
    private static boolean sRunning;

    /*
     * (non-Javadoc)
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mAnyBroadcastReceiver = new AnyBroadcastReceiver();
        sRunning = true;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent pIntent) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent pIntent, int pFlags, int pStartId) {
        Log.d(TAG, "onStartCommand");
        registerAnyBroadcastReceiver();
        return Service.START_STICKY;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(mAnyBroadcastReceiver);
        } catch (Exception e) {
            // TODO Skipping Exception which might be raising when the receiver is not yet
            // registered
            Log.d(TAG,
                    "Skipping Exception which might be raising when the receiver is not yet registered");

        }
        sRunning = false;
        hideNotification(getApplicationContext());
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public static boolean isRunning() {
        return sRunning;
    }

    private void registerAnyBroadcastReceiver() {
        try {
            registerBroadcastReceiverForActions();
            registerBroadcastReceiverForActionsWithDataType();
            registerBroadcastReceiverForActionsWithSchemes();
            Log.d(TAG, "Registered receivers.");

        } catch (Exception e) {
            Log.d(TAG, "Exception while registering: " + e.getMessage());
        }
    }

    private void registerBroadcastReceiverForActions() {
        IntentFilter intentFilter = new IntentFilter();
        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    /**
     * @throws MalformedMimeTypeException
     */
    private void registerBroadcastReceiverForActionsWithDataType()
            throws MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter();

        // This needed for broadcasts like new picture, which is data type: "image/*"
        intentFilter.addDataType("*/*");

        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    private void registerBroadcastReceiverForActionsWithSchemes() throws MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter();

        // needed for uninstalls
        intentFilter.addDataScheme("package");

        // needed for file system mounts
        intentFilter.addDataScheme("file");

        // other schemes
        intentFilter.addDataScheme("geo");
        intentFilter.addDataScheme("market");
        intentFilter.addDataScheme("http");
        intentFilter.addDataScheme("tel");
        intentFilter.addDataScheme("mailto");
        intentFilter.addDataScheme("about");
        intentFilter.addDataScheme("https");
        intentFilter.addDataScheme("ftps");
        intentFilter.addDataScheme("ftp");
        intentFilter.addDataScheme("javascript");

        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    /**
     * Since we don't want to filter which actions have data and which don't we register two
     * different receivers with full list of actions.
     *
     * @param pIntentFilter
     */
    private void addAllKnownActions(IntentFilter pIntentFilter) {
        // System Broadcast
        List<String> sysBroadcasts = Arrays.asList(getResources().getStringArray(R.array.system_broadcast));
        for (String sysBroadcast : sysBroadcasts) {
            pIntentFilter.addAction(sysBroadcast);
        }

        //Custom Broadcast.
        List<String> customBroadcasts = Arrays.asList(getResources().getStringArray(R.array.custom_broadcast));
        for (String customBroadcast : customBroadcasts) {
            pIntentFilter.addAction(customBroadcast);
        }
    }

    /**
     * Show a notification while this service is running.
     */
    @SuppressWarnings("deprecation")
    public static void showNotification(Context pContext, String pBroadcastAction) {
        Intent mainActivityIntent = new Intent(pContext,
                MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(pContext, 0, mainActivityIntent, 0);
        CharSequence title = pContext.getText(R.string.notification_title);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(pContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(pBroadcastAction);

        builder.setContentIntent(contentIntent);

        int notificationId = 1;
        NotificationManager mngr = (NotificationManager) pContext.getSystemService(NOTIFICATION_SERVICE);
        if (mngr != null) {
            mngr.notify(notificationId, builder.build());
        }


    }

    private void hideNotification(Context pContext) {
        ((NotificationManager) pContext.getSystemService(NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }

}
