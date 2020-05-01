package com.vmedia.core.common.android.util

import android.os.Build
import android.util.SparseArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.annotation.RequiresApi

operator fun <E> SparseArray<E>.set(key: Int, value: E) = put(key, value)

operator fun SparseIntArray.set(key: Int, value: Int) = put(key, value)

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
operator fun SparseLongArray.set(key: Int, value: Long) = put(key, value)