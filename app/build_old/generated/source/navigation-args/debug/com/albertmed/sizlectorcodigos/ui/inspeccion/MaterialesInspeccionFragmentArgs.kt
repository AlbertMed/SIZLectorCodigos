package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MaterialesInspeccionFragmentArgs(
  public val codigoEscaneado: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("codigoEscaneado", this.codigoEscaneado)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("codigoEscaneado", this.codigoEscaneado)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MaterialesInspeccionFragmentArgs {
      bundle.setClassLoader(MaterialesInspeccionFragmentArgs::class.java.classLoader)
      val __codigoEscaneado : String?
      if (bundle.containsKey("codigoEscaneado")) {
        __codigoEscaneado = bundle.getString("codigoEscaneado")
        if (__codigoEscaneado == null) {
          throw IllegalArgumentException("Argument \"codigoEscaneado\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"codigoEscaneado\" is missing and does not have an android:defaultValue")
      }
      return MaterialesInspeccionFragmentArgs(__codigoEscaneado)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        MaterialesInspeccionFragmentArgs {
      val __codigoEscaneado : String?
      if (savedStateHandle.contains("codigoEscaneado")) {
        __codigoEscaneado = savedStateHandle["codigoEscaneado"]
        if (__codigoEscaneado == null) {
          throw IllegalArgumentException("Argument \"codigoEscaneado\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"codigoEscaneado\" is missing and does not have an android:defaultValue")
      }
      return MaterialesInspeccionFragmentArgs(__codigoEscaneado)
    }
  }
}
