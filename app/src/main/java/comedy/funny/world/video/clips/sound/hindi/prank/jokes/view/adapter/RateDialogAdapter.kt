package video.song.app.change.hindi.Artists.audio.store.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.ArtistsListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.viewholder.ArtistsViewHolder

class RateDialogAdapter(val context: Context?, val appsList: List<List<String>>, val artistsListener: ArtistsListener) : RecyclerView.Adapter<ArtistsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        return ArtistsViewHolder(LayoutInflater.from(context).inflate(R.layout.card_all_apps_portal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+appsList.size)
        Glide.with(context!!)
                .load(appsList.get(position).get(3))
                .into(holder.ivSongIcon)

        holder.tvSongName.text = appsList.get(position).get(1)

        holder.ivSongIcon.setOnClickListener{
            artistsListener.onArtistsCardClick(appsList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return appsList.size
    }
}