package com.besimplify.android.stackoverflowuser.extensions

import java.text.SimpleDateFormat
import java.util.Date

fun Date.format() = SimpleDateFormat.getDateTimeInstance().format(this)
