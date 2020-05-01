package com.vmedia.feature.feed.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.room.RoomDatabase
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.obj.event.EventInfo
import com.vmedia.core.common.android.util.addSystemBottomPadding
import com.vmedia.core.common.android.util.addSystemTopPadding
import com.vmedia.core.common.android.util.init
import com.vmedia.core.common.android.view.prefetcher.api.PrefetchRecycledViewPool
import com.vmedia.core.navigation.navigator.feed.FeedNavigator
import com.vmedia.feature.feed.presentation.mvi.FeedIntent
import com.vmedia.feature.feed.presentation.mvi.FeedIntent.ObserveEvents
import com.vmedia.feature.feed.presentation.mvi.FeedState
import com.vmedia.feature.feed.presentation.recycler.FeedAdapter
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class FeedFragment : MviFragment<FeedIntent, FeedState, Nothing>(
    layoutResource = R.layout.feed_fragment,
    initialIntent = ObserveEvents
) {

    private val adapter by lazy {
        FeedAdapter(
            onItemClick = navigator::navigateToEventDetails,
            onOptionsClick = ::onOptionsClick,
            onAssetClick = navigator::navigateToAsset,
            onRevenueClick = navigator::navigateToStatistics
        )
    }

    private val navigator: FeedNavigator
        get() = activity as FeedNavigator


    override fun provideViewModel() = getViewModel<FeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        general_appbar.addSystemTopPadding()
        feed_list.addSystemBottomPadding()

        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        feed_list.adapter = null
    }

    override fun render(state: FeedState) {
        feed_loading.isVisible = state.isLoading && state.payload.isNullOrEmpty()
        adapter.items = state.payload
    }

    private fun initRecycler() {
        val viewPool = PrefetchRecycledViewPool(activity as Activity, 60)
        viewPool.start()

        feed_list.init(adapter)
        feed_list.setItemViewCacheSize(10)

        feed_list.setRecycledViewPool(viewPool)
        feed_list.enableCustomGapworker = true

        adapter.prefetchItems(viewPool)
    }

    private fun onOptionsClick(eventInfo: EventInfo<*>) {
        Completable.fromAction { get<RoomDatabase>().clearAllTables() }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}