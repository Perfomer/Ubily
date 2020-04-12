package com.vmedia.feature.feed.presentation

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.obj.EventInfo

interface FeedNavigator {

    fun navigateToEventDetails(eventInfo: EventInfo<*>)

    fun navigateToAsset(assetId: Long)

    fun navigateToStatistics(period: Period)

}