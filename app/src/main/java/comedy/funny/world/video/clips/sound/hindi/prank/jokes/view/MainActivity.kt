package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import video.song.app.change.hindi.Artists.audio.store.view.adapter.RateDialogAdapter
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.BaseActivity
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.BuildConfig
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils.CustomViewPager
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.utils.ForceUpdateChecker
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.ArtistsListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.ArtistsViewModel
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.BollywoodViewModel
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.LatestViewModel
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.RegionalViewModel
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewpager.AppPagerAdapter

class MainActivity : BaseActivity(), ForceUpdateChecker.OnUpdateNeededListener, ArtistsListener {

    var viewPager: CustomViewPager? = null
    var viewPagerTab: TabLayout? =null
    var fragmentPagerAdapter: FragmentPagerAdapter ?= null

    var regionalViewModel: RegionalViewModel? = null
    var latestViewModel: LatestViewModel? = null
    var bollywoodViewModel: BollywoodViewModel? = null
    var artistsViewModel: ArtistsViewModel? = null

    var rateDialogAdapter: RateDialogAdapter? = null
    var rvRate: RecyclerView? = null
    var rateList: ArrayList<List<String>>? = ArrayList()

    var firebaseAnalytics: FirebaseAnalytics? = null

    override val bindingVariable: Int
        get() = 0

    override val layoutId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        setupViewPager()

        ForceUpdateChecker().with(this)!!.onUpdateNeeded(this).check()

        latestViewModel = ViewModelProvider(this).get(LatestViewModel::class.java)
        regionalViewModel = ViewModelProvider(this).get(RegionalViewModel::class.java)
        bollywoodViewModel = ViewModelProvider(this).get(BollywoodViewModel::class.java)
        artistsViewModel = ViewModelProvider(this).get(ArtistsViewModel::class.java)

        latestViewModel?.loadData()
        latestViewModel!!.rateDialogData.observe(this, Observer { t ->
            Log.d("TAG", "RateApps $t")
            rateList!!.clear()
            rateList!!.addAll(t!!)
        })

        viewPagerTab!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("TAG", "onTabSelected: " + tab.position)
                val bundleAppUsage = Bundle()
                bundleAppUsage.putString("tab_click", tab.text.toString())
                onUpdateLogEvent(bundleAppUsage, "app_usage", false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        setSupportActionBar(toolbar)
    }

    fun initViews(){
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        viewPager = findViewById(R.id.vpPager)
        viewPagerTab = findViewById(R.id.view_pager_tab)
    }

    fun setupViewPager(){
        fragmentPagerAdapter = AppPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = fragmentPagerAdapter
        val limit = if ((fragmentPagerAdapter as AppPagerAdapter).getCount() > 1) (fragmentPagerAdapter as AppPagerAdapter).getCount() - 1 else 1
        viewPager!!.offscreenPageLimit = limit;
        viewPager!!.currentItem = 1;
        viewPager!!.setSwipePagingEnabled(false)
        viewPagerTab!!.setupWithViewPager(viewPager)
    }

    fun rateButton(view: View) {
        val rateDialog = Dialog(this, R.style.DialogTheme)
        rateDialog.setContentView(R.layout.dialog_show)
        rvRate = rateDialog.findViewById(R.id.rvDialog)
        setRateRecycler()
        rateDialog.show()
    }

    fun setRateRecycler(){
        rateDialogAdapter = RateDialogAdapter(this@MainActivity,rateList!!,this)
        rvRate.apply {
            rvRate?.layoutManager = GridLayoutManager(this@MainActivity, 3)
            rvRate?.adapter = rateDialogAdapter
        }
    }

    override fun onDestroy() {
        regionalViewModel?.reset()
        latestViewModel?.reset()
        bollywoodViewModel?.reset()
        artistsViewModel?.reset()
        super.onDestroy()
    }

    override fun onUpdateNeeded(updateUrl: String?) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue viewing live news.")
            .setPositiveButton(
                "Update"
            ) { dialog, which -> redirectStore(updateUrl!!) }.setNegativeButton(
                "No, thanks"
            ) { dialog, which -> finish() }.create()
        dialog.show()
    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun onUpdateLogEvent(bundle: Bundle, eventName: String, isUrlVisited: Boolean){
        Log.d("TAG", "onUpdateLogEvent: ")
        if(BuildConfig.DEBUG){
            return
        }
        else{
            if(isUrlVisited){
                firebaseAnalytics!!.logEvent(eventName, bundle)
                firebaseAnalytics!!.logEvent("url_visited", bundle)
            }
            else
                firebaseAnalytics!!.logEvent(eventName, bundle)
        }
    }

    override fun onArtistsCardClick(item: List<String>) {
        try {
            val appStoreIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+item.get(2)))
            appStoreIntent.setPackage("com.android.vending")
            startActivity(appStoreIntent)
        } catch (exception: ActivityNotFoundException) {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=fund.stock.share.market.money.stakeholder.finance.economy.live")
                    )
            )
        }
    }
}
