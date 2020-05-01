package com.vmedia.feature.assetdetails.presentation.recycler.keyword

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.view.recycler.base.ViewHolderOnClick
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import kotlinx.android.synthetic.main.assetdetails_item_keyword.*

internal class KeywordViewHolder(
    containerView: View,
    onClick: ViewHolderOnClick
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener {
            onClick.invokeWithPosition()
        }
    }

    fun bind(keyword: KeywordModel) {
        assetdetails_keyword_item_text.text = keyword.value
    }

}