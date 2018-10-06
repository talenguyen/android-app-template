package com.besimplify.android.stackoverflowuser.extensions

import android.content.Context

fun Context.dip(size: Int): Int = (resources.displayMetrics.density * size).toInt()
