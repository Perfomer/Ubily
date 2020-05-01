package com.vmedia.feature.eventdetails.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.*
import com.vmedia.core.common.pure.util.FORMAT_DDMMYYYY
import com.vmedia.core.common.pure.util.format
import com.vmedia.core.navigation.navigator.eventdetails.EventDetailsNavigator
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsIntent
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsIntent.LoadEventDetails
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsState
import kotlinx.android.synthetic.main.eventdetails_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

internal class EventDetailsFragment : MviFragment<EventDetailsIntent, EventDetailsState, Nothing>(
    layoutResource = R.layout.eventdetails_fragment,
    initialIntent = LoadEventDetails
) {

    private val navigator: EventDetailsNavigator
        get() = activity as EventDetailsNavigator

    private var adapter: EventDetailsAdapter? = null

    private var eventId: Long by argument()


    override fun provideViewModel() = getViewModel<EventDetailsViewModel> {
        parametersOf(eventId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        general_toolbar.attachToActivity()
        general_toolbar.title = getString(R.string.eventdetails_title, eventId)

        general_appbar.addSystemTopPadding()
        eventdetails_scrollroot.addSystemBottomPadding()

        adapter = EventDetailsAdapter(
            parent = eventdetails_content,
            onAssetClick = navigator::navigateToAsset,
            onRevenueClick = navigator::navigateToStatistics
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun render(state: EventDetailsState) {
        val event = state.payload
        val type = event.type


        eventdetails_loading.isVisible = state.isLoading

        eventdetails_icon.setImageResource(type.iconResource)
        eventdetails_title.diffedValue = getString(type.titleResource)
        eventdetails_date.diffedValue = event.date.format(FORMAT_DDMMYYYY)
        eventdetails_description.setEventDescription(event)

        adapter!!.bind(event)
    }

    internal companion object {

        fun newInstance(eventId: Long) = EventDetailsFragment().apply {
            this.eventId = eventId
        }

    }

}