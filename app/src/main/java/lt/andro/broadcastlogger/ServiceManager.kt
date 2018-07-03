package lt.andro.broadcastlogger

import android.content.Context
import android.content.Intent

/**
 * Starts and stops a service, whose [Intent] was provided in `ServiceManager`'s
 * constructor.
 *
 * @return true if the [BroadcastMonitoringService] starts, false otherwise.
 */
fun Context.toggleService(intent: Intent) = if (BroadcastMonitoringService.isRunning()) {
    this.stopService(intent)
    false
} else {
    this.startService(intent)
    true
}
