package com.albertmed.sizlectorcodigos.ui.scanner

import android.os.Bundle
import androidx.navigation.NavDirections
import com.albertmed.sizlectorcodigos.R
import kotlin.Int
import kotlin.String

public class ScannerFragmentDirections private constructor() {
  private data class ActionScannerFragmentToScanResultFragment(
    public val scannedData: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_scannerFragment_to_scanResultFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("scannedData", this.scannedData)
        return result
      }
  }

  public companion object {
    public fun actionScannerFragmentToScanResultFragment(scannedData: String): NavDirections =
        ActionScannerFragmentToScanResultFragment(scannedData)
  }
}
