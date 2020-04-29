package com.vmedia.core.common.util

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

object ArrayAdapters {

    fun createFromResources(
        context: Context,
        textArraysId: IntArray,
        @LayoutRes textViewResId: Int,
        @LayoutRes textDropdownResId: Int
    ): ArrayAdapter<String> {
        val strings = textArraysId.map(context::getString)

        return ArrayAdapter(context, textViewResId, 0, strings).apply {
            setDropDownViewResource(textDropdownResId)
        }
    }

}