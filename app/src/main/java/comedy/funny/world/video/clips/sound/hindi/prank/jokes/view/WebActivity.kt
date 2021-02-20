package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils.Constants
import kotlinx.android.synthetic.main.activity_web.*
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R

class WebActivity : AppCompatActivity() {

    var webView: WebView? = null
    var appTitle: String? = null
    var appUrl: String? = null
    var appIcon: String? = null

    var rlWebSplash: RelativeLayout? = null
    var ivAppIcon: ImageView? = null

    lateinit var fullscreenView: View

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web)
        initViews()
        initData()

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        loadWebSplash()

        webViewSettings()

        if (savedInstanceState==null){
            webView?.post {

                webView?.loadUrl(appUrl)
            }
        }


        if(firebaseRemoteConfig!!.getBoolean(Constants().SHOW_ADS)){
            val adRequest = AdRequest.Builder()
                    .build()

            adView.loadAd(adRequest)


            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = Constants().getInterstitialWebExit()
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }

    }

    fun initViews(){
        webView = findViewById(R.id.webViewMain)
        rlWebSplash = findViewById(R.id.rlWebSplash)
        ivAppIcon = findViewById(R.id.ivAppIcon)
    }

    fun initData(){
        val bundle: Bundle? = intent.extras
        appUrl = bundle?.getString("url")
        appIcon = bundle?.getString("app_icon")
        Log.d("TAG", "initData: " + bundle?.getString("url"))

    }

    fun webViewSettings(){
        webView!!.settings.loadsImagesAutomatically = true
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.allowContentAccess = true

        webView!!.settings.useWideViewPort = true
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.domStorageEnabled = true
        webView!!.clearView()
        webView!!.isHorizontalScrollBarEnabled = false
        webView!!.settings.setAppCacheEnabled(true)
        webView!!.settings.databaseEnabled = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView!!.settings.databasePath = "/data/data/" + this.packageName + "/databases/"
        }
        webView!!.isVerticalScrollBarEnabled = false
        webView!!.settings.builtInZoomControls = true
        webView!!.settings.displayZoomControls = false
        webView!!.settings.allowFileAccess = true
        webView!!.settings.pluginState = WebSettings.PluginState.OFF
        webView!!.isScrollbarFadingEnabled = false
        webView!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView!!.settings.defaultZoom = WebSettings.ZoomDensity.FAR
        webView!!.webViewClient = WebViewClient()
        webView!!.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView!!.setInitialScale(1)

        //webView!!.webChromeClient = WebChromeClient()

        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                }
                return false
            }
        }

        webView!!.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if(newProgress >= 80){
                    rlWebSplash!!.visibility = View.GONE
                }
            }
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)

                if (view is FrameLayout){

                    fullscreenView = view
                    fullScreenContainer.addView(fullscreenView)
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    fullScreenContainer.visibility = View.VISIBLE
                    mainContainer.visibility = View.GONE

                }
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                fullScreenContainer.removeView(fullscreenView)
                fullScreenContainer.visibility = View.GONE
                mainContainer.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {

        if(webView!!.canGoBack()){
            webView!!.goBack()
        }
        else{
            if(::mInterstitialAd.isInitialized){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.adListener = object: AdListener() {
                        override fun onAdClosed() {
                            super.onAdClosed()
                            finish()
                        }
                    }
                } else {
                    super.onBackPressed()
                }
            }
            else{
                super.onBackPressed()
            }
        }

    }

    fun loadWebSplash(){
        Glide.with(ivAppIcon!!.context)
                .load(appIcon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivAppIcon!!)
    }

    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView!!.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView!!.restoreState(savedInstanceState)
    }


}