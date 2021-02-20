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

class BollywoodViewModel : ViewModel() {

    private var context: Context? = null
    var compositeDisposable: CompositeDisposable? = null

    var bollywoodCardsData: MutableLiveData<List<List<String>>?> = MutableLiveData()
    var dialogData: MutableLiveData<List<List<String>>?> = MutableLiveData()

    fun loadData(){
        Log.d("TAG", "loadData: ")
        compositeDisposable = CompositeDisposable()
        fetchBollywoodCards()
    }

    private fun fetchBollywoodCards(){
        Log.d("TAG", "fetchBollywood: ")
        val singleton: Singleton? = Singleton.get()
        val dataService: DataService? = singleton!!.getDataService()

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_BOLLYWOOD_CARDS, DataFactory().KEY)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError(Consumer { t ->
                    Log.d("TAG", "fetchBollywood Error ${t.localizedMessage}")
                })
                ?.subscribe(Consumer { t ->
                    Log.d("TAG", "fetchBollywood Response ${t.getValues()}")
                    changeBollywoodCardsDataSet(t.getValues())
                })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    fun changeBollywoodCardsDataSet(allAppsList: List<List<String>>?){
        bollywoodCardsData.value = allAppsList
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