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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.dialog_show.*
import kotlinx.android.synthetic.main.fragment_artists.*
import video.song.app.change.hindi.Artists.audio.store.view.adapter.ArtistsAdapter
import video.song.app.change.hindi.Artists.audio.store.view.adapter.ArtistsDialogAdapter
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.base.BaseFragment
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.MainActivity
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.WebActivity
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.ArtistsListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.DialogListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel.ArtistsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InvestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistsFragment : BaseFragment(), ArtistsListener, DialogListener{
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    var firebaseAnalytics: FirebaseAnalytics? = null

    var artistsAdapter: ArtistsAdapter? = null
    var dialogAdapter: ArtistsDialogAdapter? = null

    var artistsViewModel: ArtistsViewModel? = null

    var rvArtists: RecyclerView? = null
    var rvDialog: RecyclerView? = null

    var artistsCardsList: ArrayList<List<String>>? = ArrayList()
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
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setRecyclerView()

        artistsViewModel = ViewModelProvider(activity!!).get(ArtistsViewModel::class.java)
        artistsViewModel?.loadData()

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        artistsViewModel!!.artistsCardsData.observe(this, Observer { t ->
            Log.d("TAG", "ArtistsCards $t")
            artistsCardsList!!.clear()
            artistsCardsList!!.addAll(t!!)
            artistsAdapter!!.notifyDataSetChanged()
        })
    }

    fun initViews(view: View){
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity!!)
        rvArtists = view.findViewById(R.id.rvArtists)
    }


    fun onShowDialog(List: ArrayList<List<String>>) {
        val dialog = Dialog(context!!,R.style.DialogTheme)
        dialog.setContentView(R.layout.dialog_show)
        rvDialog = dialog.findViewById(R.id.rvDialog)

        dialogAdapter = ArtistsDialogAdapter(context, List,this)
        rvDialog.apply {
            rvDialog?.layoutManager = GridLayoutManager(context!!,2)
            rvDialog?.adapter = dialogAdapter
        }
        dialog.show()
    }

    fun setRecyclerView(){
        artistsAdapter = ArtistsAdapter(context, artistsCardsList!!,this)
        rvArtists.apply {
            rvArtists?.layoutManager = GridLayoutManager(context!!,2)
            rvArtists?.adapter = artistsAdapter
        }
    }

    override fun onDestroy() {
        artistsViewModel?.reset()
        super.onDestroy()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InvestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            ArtistsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onArtistsCardClick(item: List<String>) {
        artistsViewModel!!.fetchDialog(item.get(2))
        artistsViewModel!!.dialogData.observe(this, Observer { t ->
            Log.d("TAG", "dialogData $t")
            dialogList!!.clear()
            dialogList!!.addAll(t!!)
            dialogAdapter!!.notifyDataSetChanged()
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
