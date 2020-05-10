package com.vmedia.feature.publisherdetails.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.*
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.core.navigation.navigator.publisherdetails.PublisherDetailsNavigator
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsIntent
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsIntent.LoadData
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsState
import kotlinx.android.synthetic.main.publisherdetails_card_description.*
import kotlinx.android.synthetic.main.publisherdetails_card_publisher.*
import kotlinx.android.synthetic.main.publisherdetails_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class PublisherDetailsFragment :
    MviFragment<PublisherDetailsIntent, PublisherDetailsState, Nothing>(
        layoutResource = R.layout.publisherdetails_layout,
        initialIntent = LoadData
    ) {

    private val navigator: PublisherDetailsNavigator
        get() = activity as PublisherDetailsNavigator

    override fun provideViewModel() = getViewModel<PublisherDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publisherdetails_root.addSystemBottomPadding()
        publisherdetails_toolbar.addSystemTopPadding()

        publisherdetails_back.setOnClickListener(::goBack)

        publisherdetails_icon.setOnClickListener {
            navigator.navigateToGallery(currentState!!.payload.iconImage)
        }

        publisherdetails_description_image.setOnClickListener {
            navigator.navigateToGallery(currentState!!.payload.bigImage)
        }

        publisherdetails_externallink.setOnClickListener {
            navigator.navigateToUrl(currentState!!.payload.shortUrl)
        }
    }

    override fun render(state: PublisherDetailsState) {
        val publisher = state.payload
        val hasLargeImage = !publisher.bigImage.isBlank()

        publisherdetails_loading.isVisible = state.isLoading

        assetdetails_name.diffedValue = publisher.name
        publisherdetails_identifier.diffedValue = publisher.id.toString()
        publisherdetails_rating.diffedValue = publisher.averageRating.cropToString()

        publisherdetails_description_text.text = publisher.description.toSpan()
        publisherdetails_description_image.isVisible = hasLargeImage

        publisherdetails_icon.loadCircleImage(publisher.iconImage)

        if (hasLargeImage) {
            publisherdetails_largeimage.loadImage(publisher.bigImage)
            publisherdetails_description_image.loadImage(publisher.bigImage)
        }
    }

}