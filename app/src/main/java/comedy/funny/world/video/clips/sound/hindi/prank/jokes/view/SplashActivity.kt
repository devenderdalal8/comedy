package comedy.funny.world.video.clips.sound.hindi.prank.jokes.view

import android.os.Bundle
import android.os.Handler
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.R
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.BaseActivity


class SplashActivity : BaseActivity() {

    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            openActivity(null, MainActivity::class.java)
            finish()
        }, 1000)
    }
}