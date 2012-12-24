
package lt.andro.broadcastlogger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    protected static final String LOCAL_RECORDS_BROADCAST = "LOCAL_RECORDS_BROADCAST";
    private AnyBroadcastReceiver mAnyBroadcastReceiver;

    /*
     * (non-Javadoc)
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
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
        mAnyBroadcastReceiver = new AnyBroadcastReceiver();
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

        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.ACTION_PASSWORD_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.ACTION_PASSWORD_EXPIRING"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.ACTION_PASSWORD_FAILED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.ACTION_PASSWORD_SUCCEEDED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.DEVICE_ADMIN_DISABLED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.app.action.DEVICE_ADMIN_ENABLED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.SCAN_MODE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.adapter.action.STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.ACL_CONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.ACL_DISCONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.BOND_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.CLASS_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.FOUND"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.NAME_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.device.action.UUID"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.devicepicker.action.DEVICE_SELECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.devicepicker.action.LAUNCH"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.hardware.action.NEW_PICTURE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.hardware.action.NEW_VIDEO"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.ACTION_POWER_CONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.ACTION_POWER_DISCONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.ACTION_SHUTDOWN"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.AIRPLANE_MODE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.BATTERY_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.BATTERY_LOW"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.BATTERY_OKAY"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.BOOT_COMPLETED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.CAMERA_BUTTON"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.CONFIGURATION_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.DATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.DEVICE_STORAGE_LOW"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.DEVICE_STORAGE_OK"));
        registerReceiver(mAnyBroadcastReceiver,
                new IntentFilter("android.intent.action.DOCK_EVENT"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.DREAMING_STARTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.DREAMING_STOPPED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.FETCH_VOICEMAIL"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.GTALK_CONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.GTALK_DISCONNECTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.HEADSET_PLUG"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.INPUT_METHOD_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.LOCALE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MANAGE_PACKAGE_STORAGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_BAD_REMOVAL"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_BUTTON"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_CHECKING"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_EJECT"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_MOUNTED"));
        registerReceiver(mAnyBroadcastReceiver,
                new IntentFilter("android.intent.action.MEDIA_NOFS"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_REMOVED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_SCANNER_FINISHED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_SCANNER_STARTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_SHARED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_UNMOUNTABLE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MEDIA_UNMOUNTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.MY_PACKAGE_REPLACED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.NEW_OUTGOING_CALL"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.NEW_VOICEMAIL"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_ADDED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_DATA_CLEARED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_FIRST_LAUNCH"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_FULLY_REMOVED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_INSTALL"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_NEEDS_VERIFICATION"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_REMOVED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_REPLACED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_RESTARTED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PACKAGE_VERIFIED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PHONE_STATE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PROVIDER_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.PROXY_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.intent.action.REBOOT"));
        registerReceiver(mAnyBroadcastReceiver,
                new IntentFilter("android.intent.action.SCREEN_OFF"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.TIMEZONE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.intent.action.TIME_SET"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.intent.action.TIME_TICK"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.UID_REMOVED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.USER_PRESENT"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.intent.action.WALLPAPER_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.AUDIO_BECOMING_NOISY"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.RINGER_MODE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.SCO_AUDIO_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.VIBRATE_SETTING_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.net.nsd.STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.NETWORK_IDS_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.net.wifi.RSSI_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.WIFI_STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.p2p.CONNECTION_STATE_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.p2p.DISCOVERY_STATE_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.p2p.PEERS_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.p2p.STATE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.p2p.THIS_DEVICE_CHANGED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.supplicant.CONNECTION_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.net.wifi.supplicant.STATE_CHANGE"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED"));
        registerReceiver(mAnyBroadcastReceiver, new IntentFilter(
                "android.speech.tts.engine.TTS_DATA_INSTALLED"));

        Log.d(TAG, "Registered receivers.");
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
