package workshop.akbolatss.xchangesrates.presentation.chart

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import workshop.akbolatss.xchangesrates.utils.android.Event
import workshop.akbolatss.xchangesrates.R
import workshop.akbolatss.xchangesrates.base.BaseViewModel
import workshop.akbolatss.xchangesrates.base.resource.Either
import workshop.akbolatss.xchangesrates.base.resource.Failure
import workshop.akbolatss.xchangesrates.base.resource.onFailure
import workshop.akbolatss.xchangesrates.base.resource.onSuccess
import workshop.akbolatss.xchangesrates.domain.model.Chart
import workshop.akbolatss.xchangesrates.domain.model.ChartDot
import workshop.akbolatss.xchangesrates.domain.model.Exchange
import workshop.akbolatss.xchangesrates.domain.usecase.CreateOrUpdateSnapshotUseCase
import workshop.akbolatss.xchangesrates.domain.usecase.DownloadChartUseCase
import workshop.akbolatss.xchangesrates.domain.usecase.GetSnapshotCountUseCase
import workshop.akbolatss.xchangesrates.domain.usecase.LoadExchangesUseCase
import workshop.akbolatss.xchangesrates.presentation.model.ChartPeriod
import workshop.akbolatss.xchangesrates.presentation.model.defaultChartPeriod
import workshop.akbolatss.xchangesrates.presentation.model.defaultChartPeriodList
import java.math.BigDecimal

class ChartViewModel(
    private val findExchangesUseCase: LoadExchangesUseCase,
    private val loadChartUseCase: DownloadChartUseCase,
    private val createOrUpdateSnapshotUseCase: CreateOrUpdateSnapshotUseCase,
    private val getSnapshotCountUseCase: GetSnapshotCountUseCase
) : BaseViewModel() {

    val exchangeList = MutableLiveData<List<Exchange>>()
    val exchange = MediatorLiveData<Exchange>()
    val exchangeError = MutableLiveData<Int>()

    val coinList = MediatorLiveData<List<String>>()
    val coin = MutableLiveData<String>()
    val coinError = MutableLiveData<Int>()

    val currencyList = MediatorLiveData<List<String>>()
    val currency = MutableLiveData<String>()
    val currencyError = MutableLiveData<Int>()

    val chart = MutableLiveData<Chart>()
    val chartError = MutableLiveData<Int>()
    val charts = MediatorLiveData<List<ChartDot>>()

    val chartPeriodList = MutableLiveData<List<ChartPeriod>>()
    val selectedPeriod = MutableLiveData<ChartPeriod>()

    val rate = MediatorLiveData<BigDecimal>()

    val snapshotCreated = MutableLiveData<Event<Int>>()
    val snapshotCreatedError = MutableLiveData<Event<Int>>()

    val showInterstitialAd = MutableLiveData<Boolean>()

    init {
        setObservers()
        initChartPeriod()
        loadAll()
    }

    private fun setObservers() {
        exchange.addSource(exchangeList) { exchanges ->
            if (!exchanges.isNullOrEmpty()) {
                exchange.value = exchanges.first()
            }
        }
        coinList.addSource(exchange) { exchange ->
            loadCoinsWithDefault(exchange)
        }
        currencyList.addSource(coin) { coin ->
            loadCurrenciesWithDefault(coin)
        }
        rate.addSource(chart) {
            rate.value = it.rate
        }
        charts.addSource(chart) {
            charts.value = it.chartDots
        }
    }

    private fun initChartPeriod() {
        chartPeriodList.value = defaultChartPeriodList
        selectedPeriod.value = defaultChartPeriod()
    }

    private fun loadCoinsWithDefault(exchange: Exchange) {
        if (exchange.currencies.isNotEmpty()) {
            val coins = exchange.currencies.keys.toList().sorted()
            coinList.postValue(coins)

            if (coins.isNotEmpty())
                coin.value = coins.first()
        }
    }

    private fun loadCurrenciesWithDefault(coin: String) {
        exchange.value?.let { exchange ->
            val currencies =
                exchange.currencies[coin]?.sorted()
            currencyList.postValue(currencies)

            if (!currencies.isNullOrEmpty())
                currency.value = currencies.first()
        }
    }

    private fun loadAll() {
        launchOperation(operation = { scope ->
            findExchangesUseCase(scope, LoadExchangesUseCase.Params())
        }, success = {
            exchangeList.value = it
        })
    }

    fun tryLoadChart() {
        val exchange = exchange.value
        if (exchange == null) {
            exchangeError.value = R.string.chart_selected_exchange_error
            return
        }
        val coin = coin.value
        if (coin.isNullOrBlank()) {
            coinError.value = R.string.chart_selected_coin_error
            return
        }
        val currency = currency.value
        if (currency.isNullOrBlank()) {
            currencyError.value = R.string.chart_selected_currency_error
            return
        }
        val timing = selectedPeriod.value ?: defaultChartPeriod()

        loadChart(exchange, coin, currency, timing.code)
    }

    private fun loadChart(
        exchange: Exchange,
        coin: String,
        currency: String,
        timing: String
    ) {
        launchOperation(
            operation = { scope ->
                loadChartUseCase(
                    scope, DownloadChartUseCase.Params(
                        exchange = exchange.id,
                        coin = coin,
                        currency = currency,
                        timing = timing
                    )
                )
            }, success = chart::setValue
        )
    }

    fun toggleSelected(chartPeriod: ChartPeriod) {
        chartPeriodList.value?.let { list ->
            val updatedList = list.map { period ->
                if (period.code == chartPeriod.code)
                    period.copy(isSelected = true)
                else
                    period.copy(isSelected = false)
            }
            chartPeriodList.value = updatedList
            selectedPeriod.value = chartPeriod
        }
    }

    fun tryCreateSnapshot() {
        val exchange = exchange.value
        if (exchange == null) {
            exchangeError.value = R.string.chart_selected_exchange_error
            return
        }
        val coin = coin.value
        if (coin.isNullOrBlank()) {
            coinError.value = R.string.chart_selected_coin_error
            return
        }
        val currency = currency.value
        if (currency.isNullOrBlank()) {
            currencyError.value = R.string.chart_selected_currency_error
            return
        }

        createSnapshot(exchange.caption, coin, currency)
    }

    private fun createSnapshot(exchange: String, coin: String, currency: String) =
        executeUseCase { scope ->
            val chart = chart.value
            if (chart == null) {
                this.chartError.value = R.string.chart_create_snapshot_error_not_loaded
                return@executeUseCase Either.failure(Failure.ChartNotLoaded)
            }
            createOrUpdateSnapshotUseCase(
                scope, CreateOrUpdateSnapshotUseCase.Params(
                    exchange, coin, currency, chart
                )
            ).onSuccess {
                snapshotCreated.value = Event(R.string.chart_create_snapshot_success)
                tryShowInterstitialAd()
            }.onFailure {
                if (it is Failure.SnapshotAlreadyExists)
                    snapshotCreatedError.value =
                        Event(R.string.chart_create_snapshot_error_already_exists)
                else
                    snapshotCreatedError.value = Event(R.string.chart_create_snapshot_error_unknown)
            }
        }

    private fun tryShowInterstitialAd() {
        executeUseCase { scope ->
            getSnapshotCountUseCase(scope, GetSnapshotCountUseCase.Params())
                .onSuccess {
                    if (it > 3)
                        showInterstitialAd.value = true
                }
        }
    }

}
