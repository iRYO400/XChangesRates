package workshop.akbolatss.xchangesrates.presentation.root

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import me.yokeyword.fragmentation.SupportActivity
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import workshop.akbolatss.xchangesrates.R
import workshop.akbolatss.xchangesrates.databinding.ActivityMainBinding
import workshop.akbolatss.xchangesrates.presentation.chart.ChartFragment
import workshop.akbolatss.xchangesrates.presentation.snapshots.SnapshotListFragment

class RootActivity : SupportActivity(), SpaceOnClickListener {

    private val viewModel by currentScope.viewModel<RootViewModel>(this)

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@RootActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSpaceView(savedInstanceState)
        initFragments()
        observeViewModel()
        initAd()
    }

    private fun initFragments() {
        if (findFragment(ScreenState.CHART.fragment) == null
            || findFragment(ScreenState.SNAPSHOTS.fragment) == null
        ) {
            loadMultipleRootFragment(
                R.id.flContainer, 0,
                ChartFragment.newInstance(),
                SnapshotListFragment.newInstance()
            )
        }
    }

    private fun initSpaceView(savedInstanceState: Bundle?) {
        binding.spaceView.initWithSaveInstanceState(savedInstanceState)
        binding.spaceView.addSpaceItem(
            SpaceItem(
                getString(R.string.bottom_bar_charts),
                R.drawable.ic_round_score_24
            )
        )
        binding.spaceView.addSpaceItem(
            SpaceItem(
                getString(R.string.bottom_bar_snapshots),
                R.drawable.ic_round_insert_chart_outlined_24
            )
        )
        binding.spaceView.setSpaceOnClickListener(this)
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(this, Observer {
            it?.let { event ->
                event.getContentIfNotHandled()?.let { state ->
                    val fragment = findFragment(state.fragment)
                    showHideFragment(fragment)
                }
                binding.spaceView.setCentreButtonIcon(event.peekContent().buttonIcon)
            }
        })
    }

    private fun initAd() {
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
//        val adView = AdView(this).apply {
//            adSize = AdSize.BANNER
//            adUnitId = getString(R.string.bottomBanner)
//        }
//        binding.coordinator.addView(adView)
    }

    override fun onCentreButtonClick() {
        viewModel.screenState.value?.let { event ->
            if (event.peekContent() == ScreenState.CHART)
                findFragment(ChartFragment::class.java).onSaveSnapshot()
            else if (event.peekContent() == ScreenState.SNAPSHOTS)
                findFragment(SnapshotListFragment::class.java).updateAllSnapshots()
        }
    }

    override fun onItemReselected(itemIndex: Int, itemName: String?) = Unit

    override fun onItemClick(itemIndex: Int, itemName: String?) {
        when (itemIndex) {
            0 -> viewModel.showCharts()
            1 -> viewModel.showList()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let { currFocus ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                currFocus.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        return super.dispatchTouchEvent(ev)
    }

}
