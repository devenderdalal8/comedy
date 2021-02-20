package comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewpager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.view.fragment.*

class AppPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val NUM_ITEMS = 4;

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> {
                return LatestFragment.newInstance(position, "Latest")
            }
            1 -> {
                return RegionalFragment.newInstance(position, "Regional")
            }
            2 -> {
                return BollywoodFragment.newInstance(position, "Bollywood")
            }
            3 -> {
                return ArtistsFragment.newInstance(position, "Artists")
            }

            else -> return LatestFragment.newInstance(position, "Latest")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title : String? = null;
        if (position == 0) {
            title = "Latest"
        } else if (position == 1) {
            title = "Regional"
        }
        else if (position == 2) {
            title = "Bollywood"
        }
        else if (position == 3) {
            title = "Artists"
        }
        return title
    }
}