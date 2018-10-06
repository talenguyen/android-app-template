package com.besimplify.android.stackoverflowuser.extensions

import java.text.SimpleDateFormat
import java.util.Date

fun Date.formatDate(): String = SimpleDateFormat.getDateInstance().format(this)
fun Date.formatTime(): String = SimpleDateFormat.getTimeInstance().format(this)
