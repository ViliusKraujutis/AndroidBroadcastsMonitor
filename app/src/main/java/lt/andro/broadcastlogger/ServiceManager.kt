package lt.andro.broadcastlogger

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import java.lang.ref.WeakReference

/**
 * Created by Vitalii Dmitriev
 */
class ServiceManager(private val serviceIntent: Intent, context: Context) {
    private var launched = false

    /**
     * Starts and stops a service, whose [Intent] was provided in `ServiceManager`'s
     * constructor.
     */
    fun toggleService(context: Context): Boolean {
        launched = if (launched) {
            context.stopService(serviceIntent)
            false
        } else {
            context.startService(serviceIntent)
            true
        }
        return launched
    }
}
