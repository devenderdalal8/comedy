package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_latest.*
import kotlinx.android.synthetic.main.fragment_latest.view.*

import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.base.BaseFragment
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.MainActivity
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.WebActivity
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter.LatestAdapter
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter.LatestDialogAdapter
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.DialogListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.LatestListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.LatestViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LatestFragment: BaseFragment(), LatestListener, DialogListener {

    private var param1: Int? = null
    private var param2: String? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    var firebaseAnalytics: FirebaseAnalytics? = null
    
    var latestAdapter: LatestAdapter? = null
    var dialogAdapter: LatestDialogAdapter? = null

    var latestViewModel: LatestViewModel? = null

    var rvLatest: RecyclerView? = null
    var rvDialog: RecyclerView? = null

    var carouselView: CarouselView? = null
    var carouselImagesList: ArrayList<List<String>>? = ArrayList()

    var latestCardsList: ArrayList<List<String>>? = ArrayList()
    var dialogList: ArrayList<List<String>>? = ArrayList()
    
    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setRecyclerView()

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        latestViewModel = ViewModelProvider(activity!!).get(LatestViewModel::class.java)
        latestViewModel?.loadData()

        latestViewModel!!.carouselImagesLiveData.observe(this, Observer { t ->
            Log.d("TAG", "HomeFragment Live carousel $t")
            carouselImagesList!!.clear()
            carouselImagesList!!.addAll(t!!)
            onLoadCarouselImages()
        })

        latestViewModel!!.latestCardsData.observe(this, Observer { t ->
            Log.d("TAG", "LatestCards $t")
            latestCardsList!!.clear()
            latestCardsList!!.addAll(t!!)
            latestAdapter!!.notifyDataSetChanged()
        })
    }

    fun initViews(view: View){
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity!!)
        carouselView = view.findViewById(R.id.cvLatest)
        rvLatest = view.findViewById(R.id.rvLatest)
    }

    fun onShowDialog(List: ArrayList<List<String>>) {
        val dialog = Dialog(context!!,R.style.DialogTheme)
        dialog.setContentView(R.layout.dialog_show)
        rvDialog = dialog.findViewById(R.id.rvDialog)

        dialogAdapter = LatestDialogAdapter(context, List,this)
        rvDialog.apply {
            rvDialog?.layoutManager = GridLayoutManager(context!!,2)
            rvDialog?.adapter = dialogAdapter
        }

        dialog.show()
    }

    fun setRecyclerView(){
        latestAdapter = LatestAdapter(context, latestCardsList!!,this)
        rvLatest.apply {
            rvLatest?.layoutManager = GridLayoutManager(context!!,2)
            rvLatest?.adapter = latestAdapter
        }
    }

    fun onLoadCarouselImages(){
        Log.d("TAG", "onLoadCarouselImages: " + carouselImagesList!!.size)
        carouselView!!.setImageListener(imageListener)
        carouselView!!.setImageClickListener(imageClickListener)
        carouselView!!.pageCount = carouselImagesList!!.size
    }

    var imageClickListener: ImageClickListener = ImageClickListener { position ->
        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", carouselImagesList!![position][1])
        intent?.putExtra("url", carouselImagesList!![position][2])
        intent?.putExtra("app_icon", carouselImagesList!![position][4])

        val bundle = Bundle()
        bundle.putString("title", carouselImagesList!![position][1])
        bundle.putString("url", carouselImagesList!![position][2])
        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "carousel_images_visited", true)
        startActivity(intent)
    }


    var imageListener: ImageListener = ImageListener { position, imageView ->
        Log.d("TAG", "onLoadCarouselImages: position " + position +" data "+ carouselImagesList!![position][3])
        Glide.with(imageView.context)
                .load(carouselImagesList!![position][3])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    override fun onDestroy() {
        latestViewModel?.reset()
        super.onDestroy()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            LatestFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onLatestCardClick(item: List<String>) {
        latestViewModel!!.fetchDialog(item.get(2))
        latestViewModel!!.dialogData.observe(this, Observer { t ->
            Log.d("TAG", "dialogData $t")
            dialogList!!.clear()
            dialogList!!.addAll(t!!)
            dialogAdapter?.notifyDataSetChanged()
        })
        onShowDialog(dialogList!!)
    }

    override fun onClickSongs(item: List<String>) {
        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))
        (activity as MainActivity?)!!.onUpdateLogEvent(bundle,"song_visited",true)

        val intent: Intent? = Intent(activity,WebActivity::class.java)
        intent?.putExtra("url",item.get(2))
        startActivity(intent)
    }
}