package com.besimplify.android.stackoverflowuser.extensions

import java.text.SimpleDateFormat
import java.util.Date

fun Date.format(): String = SimpleDateFormat.getDateTimeInstance().format(this)
