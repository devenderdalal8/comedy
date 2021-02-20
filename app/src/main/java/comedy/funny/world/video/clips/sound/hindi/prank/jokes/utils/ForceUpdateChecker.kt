package comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class ForceUpdateChecker(
    var context: Context? = null,
    var onUpdateNeededListener: OnUpdateNeededListener? = null
) {
    private val TAG = ForceUpdateChecker::class.java.simpleName

    val KEY_UPDATE_REQUIRED = "force_update_required"
    val KEY_CURRENT_VERSION = "force_update_current_version"
    val KEY_UPDATE_URL = "force_update_store_url"

    interface OnUpdateNeededListener {
        fun onUpdateNeeded(updateUrl: String?)
    }

    fun with(context: Context): Builder? {
        return Builder(context)
    }

    fun check() {
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            val currentVersion: String = remoteConfig.getString(KEY_CURRENT_VERSION)
            val appVersion = getAppVersion(context)
            val updateUrl: String = remoteConfig.getString(KEY_UPDATE_URL)
            Log.d(
                TAG,
                "check: appVersion $appVersion currentVersion to update$currentVersion"
            )
            if (compareVersionNames(appVersion, currentVersion) == -1
                && onUpdateNeededListener != null
            ) {
                onUpdateNeededListener!!.onUpdateNeeded(updateUrl)
            }
        }
    }

    fun compareVersionNames(oldVersionName: String, newVersionName: String): Int {
        var res = 0
        val oldNumbers = oldVersionName.split("\\.".toRegex()).toTypedArray()
        val newNumbers = newVersionName.split("\\.".toRegex()).toTypedArray()

        // To avoid IndexOutOfBounds
        val maxIndex = Math.min(oldNumbers.size, newNumbers.size)
        for (i in 0 until maxIndex) {
            val oldVersionPart = Integer.valueOf(oldNumbers[i])
            val newVersionPart = Integer.valueOf(newNumbers[i])
            if (oldVersionPart < newVersionPart) {
                res = -1
                break
            } else if (oldVersionPart > newVersionPart) {
                res = 1
                break
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.size != newNumbers.size) {
            res = if (oldNumbers.size > newNumbers.size) 1 else -1
        }
        return res
    }


    private fun getAppVersion(context: Context?): String {
        var result = ""
        try {
            result =
                context!!.getPackageManager().getPackageInfo(context.packageName, 0).versionName
            result = result.replace("[a-zA-Z]|-".toRegex(), "")
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, e.message)
        }
        return result
    }

    class Builder(private val context: Context) {
        private var onUpdateNeededListener: OnUpdateNeededListener? = null
        fun onUpdateNeeded(onUpdateNeededListener: OnUpdateNeededListener?): Builder {
            this.onUpdateNeededListener = onUpdateNeededListener
            return this
        }

        fun build(): ForceUpdateChecker {
            return ForceUpdateChecker(context, onUpdateNeededListener)
        }

        fun check(): ForceUpdateChecker {
            val forceUpdateChecker = build()
            forceUpdateChecker.check()
            return forceUpdateChecker
        }
    }
}