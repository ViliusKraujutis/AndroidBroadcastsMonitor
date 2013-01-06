
package lt.andro.broadcastlogger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.IBinder;
import android.util.Log;

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

    /*
     * (non-Javadoc)
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mAnyBroadcastReceiver = new AnyBroadcastReceiver();
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
        hideNotification(getApplicationContext());
        Log.d(TAG, "onDestroy");
        super.onDestroy();
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

        pIntentFilter.addAction("android.app.action.ACTION_PASSWORD_CHANGED");
        pIntentFilter.addAction("android.app.action.ACTION_PASSWORD_EXPIRING");
        pIntentFilter.addAction("android.app.action.ACTION_PASSWORD_FAILED");
        pIntentFilter.addAction("android.app.action.ACTION_PASSWORD_SUCCEEDED");
        pIntentFilter.addAction("android.app.action.DEVICE_ADMIN_DISABLED");
        pIntentFilter.addAction("android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED");
        pIntentFilter.addAction("android.app.action.DEVICE_ADMIN_ENABLED");
        pIntentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        pIntentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        pIntentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        pIntentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.device.action.CLASS_CHANGED");
        pIntentFilter.addAction("android.bluetooth.device.action.FOUND");
        pIntentFilter.addAction("android.bluetooth.device.action.NAME_CHANGED");
        pIntentFilter.addAction("android.bluetooth.device.action.UUID");
        pIntentFilter.addAction("android.bluetooth.devicepicker.action.DEVICE_SELECTED");
        pIntentFilter.addAction("android.bluetooth.devicepicker.action.LAUNCH");
        pIntentFilter.addAction("android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT");
        pIntentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        pIntentFilter
                .addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        pIntentFilter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        pIntentFilter.addAction("android.hardware.action.NEW_VIDEO");
        pIntentFilter.addAction("android.hardware.action.NEW_PICTURE");
        pIntentFilter.addAction("android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS");
        pIntentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        pIntentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        pIntentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        pIntentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        pIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        pIntentFilter.addAction("android.intent.action.BATTERY_LOW");
        pIntentFilter.addAction("android.intent.action.BATTERY_OKAY");
        pIntentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        pIntentFilter.addAction("android.intent.action.CAMERA_BUTTON");
        pIntentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        pIntentFilter.addAction("android.intent.action.DATE_CHANGED");
        pIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
        pIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        pIntentFilter.addAction("android.intent.action.DOCK_EVENT");
        pIntentFilter.addAction("android.intent.action.DREAMING_STARTED");
        pIntentFilter.addAction("android.intent.action.DREAMING_STOPPED");
        pIntentFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        pIntentFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        pIntentFilter.addAction("android.intent.action.FETCH_VOICEMAIL");
        pIntentFilter.addAction("android.intent.action.GTALK_CONNECTED");
        pIntentFilter.addAction("android.intent.action.GTALK_DISCONNECTED");
        pIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
        pIntentFilter.addAction("android.intent.action.INPUT_METHOD_CHANGED");
        pIntentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        pIntentFilter.addAction("android.intent.action.MANAGE_PACKAGE_STORAGE");
        pIntentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        pIntentFilter.addAction("android.intent.action.MEDIA_BUTTON");
        pIntentFilter.addAction("android.intent.action.MEDIA_CHECKING");
        pIntentFilter.addAction("android.intent.action.MEDIA_EJECT");
        pIntentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        pIntentFilter.addAction("android.intent.action.MEDIA_NOFS");
        pIntentFilter.addAction("android.intent.action.MEDIA_REMOVED");
        pIntentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        pIntentFilter.addAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        pIntentFilter.addAction("android.intent.action.MEDIA_SCANNER_STARTED");
        pIntentFilter.addAction("android.intent.action.MEDIA_SHARED");
        pIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTABLE");
        pIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        pIntentFilter.addAction("android.intent.action.MY_PACKAGE_REPLACED");
        pIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        pIntentFilter.addAction("android.intent.action.NEW_VOICEMAIL");
        pIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_FIRST_LAUNCH");
        pIntentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_INSTALL");
        pIntentFilter.addAction("android.intent.action.PACKAGE_NEEDS_VERIFICATION");
        pIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        pIntentFilter.addAction("android.intent.action.PACKAGE_VERIFIED");
        pIntentFilter.addAction("android.intent.action.PHONE_STATE");
        pIntentFilter.addAction("android.intent.action.PROVIDER_CHANGED");
        pIntentFilter.addAction("android.intent.action.PROXY_CHANGE");
        pIntentFilter.addAction("android.intent.action.REBOOT");
        pIntentFilter.addAction("android.intent.action.SCREEN_OFF");
        pIntentFilter.addAction("android.intent.action.SCREEN_ON");
        pIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        pIntentFilter.addAction("android.intent.action.TIME_SET");
        pIntentFilter.addAction("android.intent.action.TIME_TICK");
        pIntentFilter.addAction("android.intent.action.UID_REMOVED");
        pIntentFilter.addAction("android.intent.action.USER_PRESENT");
        pIntentFilter.addAction("android.intent.action.WALLPAPER_CHANGED");
        pIntentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        pIntentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
        pIntentFilter.addAction("android.media.RINGER_MODE_CHANGED");
        pIntentFilter.addAction("android.media.SCO_AUDIO_STATE_CHANGED");
        pIntentFilter.addAction("android.media.VIBRATE_SETTING_CHANGED");
        pIntentFilter.addAction("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
        pIntentFilter.addAction("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION");
        pIntentFilter.addAction("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED");
        pIntentFilter.addAction("android.net.nsd.STATE_CHANGED");
        pIntentFilter.addAction("android.net.wifi.NETWORK_IDS_CHANGED");
        pIntentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        pIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        pIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        pIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        pIntentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        pIntentFilter.addAction("android.net.wifi.p2p.DISCOVERY_STATE_CHANGE");
        pIntentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        pIntentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
        pIntentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
        pIntentFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        pIntentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        pIntentFilter.addAction("android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED");
        pIntentFilter.addAction("android.speech.tts.engine.TTS_DATA_INSTALLED");
    }

    /**
     * Show a notification while this service is running.
     */
    @SuppressWarnings("deprecation")
    public static void showNotification(Context pContext, String pBroadcastAction) {
        // TODO use Notification.Builder
        Notification notification = new Notification(R.drawable.ic_launcher, pBroadcastAction,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(pContext, 0, new Intent(pContext,
                MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(pContext, pContext.getText(R.string.notification_title),
                pBroadcastAction, contentIntent);

        // Send the notification.
        ((NotificationManager)pContext.getSystemService(NOTIFICATION_SERVICE)).notify(NOTIFICATION,
                notification);
    }

    private void hideNotification(Context pContext) {
        ((NotificationManager)pContext.getSystemService(NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }

}
