package comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
    private fun NetworkUtils() {
        // This class is not publicly instantiable
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false
    }
}