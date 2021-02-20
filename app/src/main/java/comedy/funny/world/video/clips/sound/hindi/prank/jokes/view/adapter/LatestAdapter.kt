package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.LatestListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.viewholder.LatestViewHolder

class LatestAdapter(val context: Context?, val cardsList: List<List<String>>, val latestListener: LatestListener) : RecyclerView.Adapter<LatestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestViewHolder {
        return LatestViewHolder(LayoutInflater.from(context).inflate(R.layout.card_random_songs_portal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+cardsList.size)
            Glide.with(context!!)
                .load(cardsList.get(position).get(3))
                .into(holder.ivSongIcon)

        holder.tvSongName.text = cardsList.get(position).get(1)

        holder.ivSongIcon.setOnClickListener{
            latestListener.onLatestCardClick(cardsList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }
}