package comedy.funny.world.video.clips.sound.hindi.prank.jokes.data

import comedy.funny.world.video.clips.sound.hindi.prank.jokes.model.AllAppsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface DataService {
    @GET
    fun fetchAllApps(@Url url: String, @Query("key") key: String): Observable<AllAppsModel>

}