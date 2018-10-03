package com.besimplify.android.stackoverflowuser.util

import okio.Okio
import java.io.File

fun Any.resourcesFile(fileName: String): File {
  val classLoader = javaClass.classLoader
  val resource = classLoader.getResource(fileName)
  return File(resource.path)
}

fun File.asBufferedSources() = Okio.buffer(Okio.source(this))
