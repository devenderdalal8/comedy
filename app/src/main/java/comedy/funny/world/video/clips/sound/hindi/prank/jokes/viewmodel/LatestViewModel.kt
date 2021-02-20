package comedy.funny.world.video.clips.sound.hindi.prank.jokes.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.Singleton
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.data.DataFactory
import comedy.funny.world.video.clips.sound.hindi.prank.jokes.data.DataService

class LatestViewModel : ViewModel() {
    private var context: Context? = null
    var compositeDisposable: CompositeDisposable? = null

    var carouselImagesLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
    var latestCardsData: MutableLiveData<List<List<String>>?> = MutableLiveData()
    var dialogData: MutableLiveData<List<List<String>>?> = MutableLiveData()
    var rateDialogData: MutableLiveData<List<List<String>>?> = MutableLiveData()

    fun loadData(){
        Log.d("TAG", "loadData: ")
        compositeDisposable = CompositeDisposable()
        fetchCarouselImages()
        fetchRateDialog()
        fetchLatestCards()
    }

    private fun fetchCarouselImages(){
        Log.d("TAG", "fetchCarouselImages: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_CAROUSEL_IMAGES,DataFactory().KEY)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError(Consumer { t ->
                    Log.d("TAG", "fetchCarouselImages Error ${t.localizedMessage}")
                })
                ?.subscribe(Consumer { t ->
                    Log.d("TAG", "fetchCarouselImages Response ${t.getValues()}")
                    changeCarouselDataSet(t.getValues())
                })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }


    fun changeCarouselDataSet(carouselList: List<List<String>>?){
        carouselImagesLiveData.value = carouselList
    }


    private fun fetchLatestCards(){
        Log.d("TAG", "fetchLatest: ")
        val singleton: Singleton? = Singleton.get()
        val dataService: DataService? = singleton!!.getDataService()


        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_LATEST_CARDS, DataFactory().KEY)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError(Consumer { t ->
                    Log.d("TAG", "fetchLatest Error ${t.localizedMessage}")
                })
                ?.subscribe(Consumer { t ->
                    Log.d("TAG", "fetchLatest Response ${t.getValues()}")
                    changeLatestCardsDataSet(t.getValues())
                })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    fun changeLatestCardsDataSet(allAppsList: List<List<String>>?){
        latestCardsData.value = allAppsList
    }

    fun fetchDialog(String: String) {
        Log.d("TAG", "fetchLatest Dialog: ")
        val singleton: Singleton? = Singleton.get()
        val dataService: DataService? = singleton!!.getDataService()

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_CARD_START + String + DataFactory().URL_CARD_END, DataFactory().KEY)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError(Consumer { t ->
                    Log.d("TAG", "fetchLatest Dialog Error ${t.localizedMessage}")
                })
                ?.subscribe(Consumer { t ->
                    Log.d("TAG", "fetchLatest Dialog Response ${t.getValues()}")
                    changeDialogDataSet(t.getValues())
                })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    fun changeDialogDataSet(List: List<List<String>>?){
        dialogData.value = List
    }

    private fun fetchRateDialog(){
        Log.d("TAG", "fetchRateDialog: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_RATE_APPS,DataFactory().KEY)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError(Consumer { t ->
                    Log.d("TAG", "fetchRateDialog Error ${t.localizedMessage}")
                })
                ?.subscribe(Consumer { t ->
                    Log.d("TAG", "fetchRateDialog Response ${t.getValues()}")
                    changeRateDataSet(t.getValues())
                })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }


    fun changeRateDataSet(appList: List<List<String>>?){
        rateDialogData.value = appList
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
        }
    }

    fun reset() {
        unSubscribeFromObservable()
        compositeDisposable = null
        context = null
    }
}