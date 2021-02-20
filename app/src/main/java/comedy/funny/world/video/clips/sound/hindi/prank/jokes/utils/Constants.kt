package comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils

import comedy.funny.world.video.clips.sound.hindi.prank.jokes.BuildConfig


class Constants {

    val SHOW_ADS = "show_ads"

    val ADMOB_NATIVE_TEST = "ca-app-pub-3940256099942544/2247696110"
    val ADMOB_BANNER_TEST = "ca-app-pub-3940256099942544/6300978111"
    val ADMOB_INTERSTITIAL_TEST = "ca-app-pub-3940256099942544/1033173712"
    val ADMOB_NATIVE_HOME_1 = "ca-app-pub-9928966600221551/1829042715"
    val ADMOB_NATIVE_HOME_2 = "ca-app-pub-9928966600221551/4263634363"
    val ADMOB_NATIVE_CAT_1 = "ca-app-pub-9928966600221551/1637471027"
    val ADMOB_NATIVE_CAT_2 = "ca-app-pub-9928966600221551/4072062671"
    val ADMOB_BANNER_WEB = "ca-app-pub-9928966600221551/5550162854"
    val ADMOB_INTERSTITIAL_WEB_EXIT = "ca-app-pub-9928966600221551/4366564381"

    fun getNativeHome1(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_NATIVE_TEST
        else
            ADMOB_NATIVE_HOME_1
    }

    fun getNativeHome2(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_NATIVE_TEST
        else
            ADMOB_NATIVE_HOME_2
    }

    fun getNativeCat1(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_NATIVE_TEST
        else
            ADMOB_NATIVE_CAT_1
    }

    fun getNativeCat2(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_NATIVE_TEST
        else
            ADMOB_NATIVE_CAT_2
    }

    fun getBannerWeb(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_BANNER_TEST
        else
            ADMOB_BANNER_WEB
    }

    fun getInterstitialWebExit(): String{
        return if (BuildConfig.DEBUG)
            ADMOB_INTERSTITIAL_TEST
        else
            ADMOB_INTERSTITIAL_WEB_EXIT
    }

}