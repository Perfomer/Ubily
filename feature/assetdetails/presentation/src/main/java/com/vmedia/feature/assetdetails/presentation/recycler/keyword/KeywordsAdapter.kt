package com.vmedia.feature.assetdetails.presentation.recycler.keyword

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import com.vmedia.feature.assetdetails.presentation.R

internal class KeywordsAdapter(
    private val onClick: (keywordId: Long) -> Unit
) : BaseAdapter<KeywordViewHolder>() {

    var items by diffedListBy(KeywordModel::id)

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].id

    override fun onLayoutRequested(viewType: Int) = R.layout.assetdetails_item_keyword

    override fun onCreateViewHolder(view: View, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(view, ::onClick)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(items[position])
    }


    private fun onClick(position: Int) {
        onClick.invoke(items[position].id)
    }

}