package com.vmedia.core.common.util

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes

object ArrayAdapters {

    fun createFromResources(
        context: Context,
        @ArrayRes textArraysId: IntArray,
        @LayoutRes textViewResId: Int
    ): ArrayAdapter<CharSequence?> {
        val strings = textArraysId.map(context::getString)

        return ArrayAdapter(context, textViewResId, 0, strings)
    }

}