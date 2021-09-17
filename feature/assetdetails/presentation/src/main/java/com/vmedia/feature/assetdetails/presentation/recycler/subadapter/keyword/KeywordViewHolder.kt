package com.vmedia.feature.assetdetails.presentation.recycler.subadapter.keyword

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClickListener
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import kotlinx.android.synthetic.main.assetdetails_item_keyword.*

internal class KeywordViewHolder(
    containerView: View,
    onClick: ViewHolderOnClickListener
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
