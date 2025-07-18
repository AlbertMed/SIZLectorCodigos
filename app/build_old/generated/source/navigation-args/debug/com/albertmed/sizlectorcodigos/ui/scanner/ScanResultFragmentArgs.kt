package com.albertmed.sizlectorcodigos.ui.scanner

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ScanResultFragmentArgs(
  public val scannedData: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("scannedData", this.scannedData)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("scannedData", this.scannedData)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ScanResultFragmentArgs {
      bundle.setClassLoader(ScanResultFragmentArgs::class.java.classLoader)
      val __scannedData : String?
      if (bundle.containsKey("scannedData")) {
        __scannedData = bundle.getString("scannedData")
        if (__scannedData == null) {
          throw IllegalArgumentException("Argument \"scannedData\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"scannedData\" is missing and does not have an android:defaultValue")
      }
      return ScanResultFragmentArgs(__scannedData)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ScanResultFragmentArgs {
      val __scannedData : String?
      if (savedStateHandle.contains("scannedData")) {
        __scannedData = savedStateHandle["scannedData"]
        if (__scannedData == null) {
          throw IllegalArgumentException("Argument \"scannedData\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"scannedData\" is missing and does not have an android:defaultValue")
      }
      return ScanResultFragmentArgs(__scannedData)
    }
  }
}
