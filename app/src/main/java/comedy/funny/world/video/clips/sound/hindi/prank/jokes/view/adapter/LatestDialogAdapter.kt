package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.DialogListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.viewholder.DialogViewHolder

class LatestDialogAdapter(val context: Context?, val songsList: List<List<String>>, val dialogListener: DialogListener) : RecyclerView.Adapter<DialogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        return DialogViewHolder(LayoutInflater.from(context).inflate(R.layout.card_random_songs_portal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+songsList.size)
        Glide.with(context!!)
                .load(songsList.get(position).get(3))
                .into(holder.ivSongIcon)

        holder.tvSongName.text = songsList.get(position).get(1)

        holder.ivSongIcon.setOnClickListener{
            dialogListener.onClickSongs(songsList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }
}