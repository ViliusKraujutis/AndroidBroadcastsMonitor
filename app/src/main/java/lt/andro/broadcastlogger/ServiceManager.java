package lt.andro.broadcastlogger;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by Vitalii Dmitriev
 */
public class ServiceManager {

    private Intent serviceIntent;
    private boolean launched;
    private WeakReference<Context> contextReference;

    private Context getContext() {
        return contextReference.get();
    }

    public ServiceManager(@NonNull Intent intent, @NonNull Context context) {
        serviceIntent = intent;
        contextReference = new WeakReference<Context>(context);
    }

    /**
     * Starts and stops a service, whose {@link Intent} was provided in {@code ServiceManager}'s
     * constructor.
     */
    public void toggleService() {
        if (launched && isServiceRunning(serviceIntent)) {
            getContext().stopService(serviceIntent);
            launched = false;
        } else {
            getContext().startService(serviceIntent);
            launched = true;
        }
    }

    /**
     * Checks whether a service is actually running or not.
     *
     * @param intent which is used to launch a service and check its status
     * @return true if a service is currently running, false otherwise
     */
    private boolean isServiceRunning(@NonNull Intent intent) {
        ComponentName name = intent.getComponent();
        ActivityManager manager =
                (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (null != name && null != manager) {
            for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (name.getClass().getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
