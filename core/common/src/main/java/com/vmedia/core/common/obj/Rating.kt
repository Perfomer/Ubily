package com.vmedia.core.common.obj

import androidx.annotation.IntDef

@IntDef(value = [1, 2, 3, 4, 5])
annotation class Rating

@IntDef(value = [0, 1, 2, 3, 4, 5])
annotation class HollowRating