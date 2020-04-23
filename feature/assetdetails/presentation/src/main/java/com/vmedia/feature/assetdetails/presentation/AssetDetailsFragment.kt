package com.vmedia.feature.assetdetails.presentation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.vmedia.core.common.mvi.MviFragment
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.*
import com.vmedia.core.navigation.navigator.splash.SplashNavigator
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.splash.presentation.mvi.AssetDetailsState
import kotlinx.android.synthetic.main.assetdetails_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

internal class AssetDetailsFragment : MviFragment<AssetDetailsIntent, AssetDetailsState, Nothing>(
    layoutResource = R.layout.assetdetails_fragment,
    initialIntent = AssetDetailsIntent.LoadData
) {

    private val navigator: SplashNavigator
        get() = activity as SplashNavigator

    private var errorSnackbar: Snackbar? = null

    private var assetId: Long by argument()

    override fun provideViewModel() = getViewModel<AssetDetailsViewModel> {
        parametersOf(assetId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assetdetails_root.addSystemBottomPadding()

        val function: (v: View) -> Unit = {
            assetdetails_description_viewmore.isVisible = false
            assetdetails_description_scrim.isVisible = false
            assetdetails_description_text.maxLines = Integer.MAX_VALUE
            assetdetails_description_keywords_title.isVisible = true
            assetdetails_description_keywords_list.isVisible = true
        }

        assetdetails_description_scrim.setOnClickListener(function)
        assetdetails_description_viewmore.setOnClickListener(function)

        assetdetails_description_text.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        errorSnackbar = null
    }

    override fun render(state: AssetDetailsState) {
        errorSnackbar?.isVisible = state.error != null
        assetdetails_loading.isVisible = state.isLoading

        with(state.payload) {
            assetdetails_name.diffedValue = name
            assetdetails_identifier.diffedValue = id.toString()
            assetdetails_category.diffedValue = categoryName
            assetdetails_price.diffedValue = "$$priceUsd"
            assetdetails_version.diffedValue = versionName
            assetdetails_status.diffedValue = getString(status.labelResource)
            assetdetails_size.diffedValue = String.format("%.2f", sizeMb)

            assetdetails_description_text.text = description.toSpan()

            assetdetails_icon.loadCircleImage(iconImage)
            assetdetails_largeimage.load(bigImage)
            assetdetails_description_image.load(bigImage)
        }
    }

    internal companion object {

        fun newInstance(assetId: Long) = AssetDetailsFragment().apply {
            this.assetId = assetId
        }

    }

}