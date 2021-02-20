package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.listener.BollywoodListener
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.viewholder.BollywoodViewHolder

class BollywoodAdapter(val context: Context?, val songsList: List<List<String>>, val bollywoodListener: BollywoodListener) : RecyclerView.Adapter<BollywoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BollywoodViewHolder {
        return BollywoodViewHolder(LayoutInflater.from(context).inflate(R.layout.card_random_songs_portal_layout,parent,false))
    }

    override fun onBindViewHolder(holder: BollywoodViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+songsList.size)
        Glide.with(context!!)
                .load(songsList.get(position).get(3))
                .into(holder.ivSongIcon)

        holder.tvSongName.text = songsList.get(position).get(1)

        holder.ivSongIcon.setOnClickListener{
            bollywoodListener.onBollywoodCardClick(songsList.get(position))
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }
}