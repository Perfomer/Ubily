package com.vmedia.feature.assetdetails.presentation.recycler.subadapter.keyword

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import com.vmedia.feature.assetdetails.presentation.R

internal typealias OnKeywordClickListener = (keywordId: Long) -> Unit

internal class KeywordsAdapter(
    private val onKeywordClickListener: OnKeywordClickListener
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
        onKeywordClickListener.invoke(items[position].id)
    }

}
