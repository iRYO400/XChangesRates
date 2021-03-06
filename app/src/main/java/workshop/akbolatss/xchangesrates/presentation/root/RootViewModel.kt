package workshop.akbolatss.xchangesrates.presentation.root

import androidx.lifecycle.MutableLiveData
import workshop.akbolatss.xchangesrates.utils.android.Event
import workshop.akbolatss.xchangesrates.base.BaseViewModel

class RootViewModel : BaseViewModel() {

    val screenState = MutableLiveData<Event<ScreenState>>()

    init {
        showCharts()
    }

    fun showCharts() {
        if (screenState.value?.peekContent() != ScreenState.CHART)
            screenState.value = Event(ScreenState.CHART)
    }

    fun showList() {
        if (screenState.value?.peekContent() != ScreenState.SNAPSHOTS)
            screenState.value = Event(ScreenState.SNAPSHOTS)
    }

}
