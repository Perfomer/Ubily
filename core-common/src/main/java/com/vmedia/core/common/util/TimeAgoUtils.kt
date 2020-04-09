package com.vmedia.core.common.util

import android.content.Context
import com.vmedia.core.common.R
import java.util.*

/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private const val SECOND_MILLIS = 1000L
private const val MINUTE_MILLIS = 60L * SECOND_MILLIS
private const val HOUR_MILLIS = 60L * MINUTE_MILLIS
private const val DAY_MILLIS = 24L * HOUR_MILLIS
private const val WEEK_MILLIS = 7L * DAY_MILLIS
private const val MONTH_MILLIS = 4L * WEEK_MILLIS
private const val YEAR_MILLIS = 12L * MONTH_MILLIS

fun Context.getTimeAgo(date: Date): String {
    return getTimeAgo(date.time)
}

fun Context.getTimeAgo(time: Long): String {
    val diff = System.currentTimeMillis() - time

    return when {
        diff < 0 -> {
            getString(R.string.timeago_future)
        }
        diff < MINUTE_MILLIS -> {
            getString(R.string.timeago_now)
        }
        diff < 2 * MINUTE_MILLIS -> {
            getString(R.string.timeago_minute)
        }
        diff < 50 * MINUTE_MILLIS -> {
            getString(R.string.timeago_minutes, diff / MINUTE_MILLIS)
        }
        diff < 90 * MINUTE_MILLIS -> {
            getString(R.string.timeago_hour)
        }
        diff < DAY_MILLIS -> {
            getString(R.string.timeago_hours, diff / HOUR_MILLIS)
        }
        diff < 2 * DAY_MILLIS -> {
            getString(R.string.timeago_day)
        }
        diff < WEEK_MILLIS -> {
            getString(R.string.timeago_days, diff / DAY_MILLIS)
        }
        diff < 2 * WEEK_MILLIS -> {
            getString(R.string.timeago_week)
        }
        diff < MONTH_MILLIS -> {
            getString(R.string.timeago_weeks, diff / WEEK_MILLIS)
        }
        diff < 2 * MONTH_MILLIS -> {
            getString(R.string.timeago_month)
        }
        diff < YEAR_MILLIS -> {
            getString(R.string.timeago_months, diff / MONTH_MILLIS)
        }
        diff < 2 * YEAR_MILLIS -> {
            getString(R.string.timeago_year)
        }
        else -> {
            getString(R.string.timeago_years, diff / YEAR_MILLIS)
        }
    }
}