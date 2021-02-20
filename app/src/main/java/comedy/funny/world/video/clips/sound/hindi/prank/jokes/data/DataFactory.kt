package comedy.funny.world.video.clips.sound.hindi.prank.jokes.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DataFactory {

    val URL_CAROUSEL_IMAGES = BASE_URL + "1tZG0Z-E8AmJm1M5mAh7TR5eAA5QLKMNvUXBYopGmyZw/values/Sheet1!A2:E/"
    val URL_LATEST_CARDS = BASE_URL + "1-_IIVRRN9KIyAzuj4fYilWvKdsYnrwcCXz5izBt79-s/values/Sheet1!A2:D/"

    val URL_BOLLYWOOD_CARDS = BASE_URL + "17W0Cbs9KcwEFCZayELgmRk7BUs1yDK94O12C5jBHBF0/values/Sheet1!A2:D/"

    val URL_REGIONAL_CARDS = BASE_URL + "1VY35OSBTC5VJoUwLltdDRFLInm-U3OtAX7fhUjXLB_E/values/Sheet1!A2:D/"

    val URL_ARTISTS_CARDS = BASE_URL + "1wZbvNkONFkSV0oK_4yBWGQll5Z4eY1FWWcoPxpinh-E/values/Sheet1!A2:D/"

    val URL_RATE_APPS = BASE_URL + "1P4NoNP7jt1u6j2vmI3XN6AM0ph-6cUWbJvIrIWPjHNQ/values/Sheet1!A2:E/"

    val URL_CARD_START = BASE_URL

    val URL_CARD_END = "/values/Sheet1!A2:E/"

    val KEY = "AIzaSyB21bRVxi8IVsINBqyRDKwq3b6bWr4gRys"

    companion object{
        private val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets/"

        fun create(): DataService? {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(DataService::class.java)
        }
    }
}
