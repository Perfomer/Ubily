package com.vmedia.feature.feed

import androidx.fragment.app.Fragment
import com.vmedia.core.common.util.ObservableListMapper
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.core.data.obj.EventInfo.*
import com.vmedia.core.data.obj.EventInfo.EventListInfo.*
import com.vmedia.feature.feed.data.FeedRepositoryImpl
import com.vmedia.feature.feed.domain.FeedInteractor
import com.vmedia.feature.feed.domain.FeedRepository
import com.vmedia.feature.feed.domain.mapper.*
import com.vmedia.feature.feed.presentation.FeedFragment
import com.vmedia.feature.feed.presentation.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal typealias _EventMapper = ObservableListMapper<Event, EventInfo<*>>
internal typealias _SaleMapper = _EventInfoMapper<EventSale>
internal typealias _DownloadMapper = _EventInfoMapper<EventFreeDownload>
internal typealias _AssetMapper = _EventInfoMapper<EventAsset>
internal typealias _ReviewMapper = _EventInfoMapper<EventReview>
internal typealias _RevenueMapper = _EventInfoMapper<EventRevenue>
internal typealias _PayoutMapper = _EventInfoMapper<EventPayout>

private typealias _EventInfoMapper<T> = ObservableMapper<Event, T>

const val BEAN_FRAGMENT_FEED = "FeedFragment"

val feedModule = module {
    single<Fragment>(named(BEAN_FRAGMENT_FEED)) { FeedFragment() }
    viewModel { FeedViewModel(get()) }
    single { FeedInteractor(get(), get<EventMapper>()) }
    single<FeedRepository> { FeedRepositoryImpl(get()) }

    single {
        EventMapper(
            saleMapper = get<SaleMapper>(),
            downloadMapper = get<DownloadMapper>(),
            assetMapper = get<AssetMapper>(),
            reviewMapper = get<ReviewMapper>(),
            revenueMapper = get<RevenueMapper>(),
            payoutMapper = get<PayoutMapper>()
        )
    }

    single { AssetMapper(get()) }
    single { DownloadMapper(get()) }
    single { PayoutMapper(get()) }
    single { RevenueMapper(get()) }
    single { ReviewMapper(get()) }
    single { SaleMapper(get()) }
}