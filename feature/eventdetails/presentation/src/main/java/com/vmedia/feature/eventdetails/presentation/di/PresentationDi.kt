package com.vmedia.feature.eventdetails.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.feature.eventdetails.api.BEAN_FRAGMENT_EVENTDETAILS
import com.vmedia.feature.eventdetails.presentation.EventDetailsFragment
import com.vmedia.feature.eventdetails.presentation.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureEventDetailsPresentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_EVENTDETAILS)) { (eventId: Long) ->
        EventDetailsFragment.newInstance(eventId)
    }

    viewModel { (eventId: Long) -> EventDetailsViewModel(eventId, get()) }
}