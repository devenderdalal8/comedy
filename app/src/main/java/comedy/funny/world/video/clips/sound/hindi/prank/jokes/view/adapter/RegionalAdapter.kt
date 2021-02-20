package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_random_songs_portal_layout.view.*
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.RegionalClickListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.viewholder.RegionalViewHolder

class RegionalAdapter(val context: Context?, val songsList: List<List<String>>, val regionalListener: RegionalClickListener) : RecyclerView.Adapter<RegionalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionalViewHolder {
        return RegionalViewHolder(LayoutInflater.from(context).inflate(R.layout.card_random_songs_portal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RegionalViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+songsList.size)
        Glide.with(context!!)
                .load(songsList.get(position).get(3))
                .into(holder.ivSongIcon)

        holder.tvSongName.text = songsList.get(position).get(1)

        holder.ivSongIcon.setOnClickListener{
            regionalListener.onRegionalCardClick(songsList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }
}