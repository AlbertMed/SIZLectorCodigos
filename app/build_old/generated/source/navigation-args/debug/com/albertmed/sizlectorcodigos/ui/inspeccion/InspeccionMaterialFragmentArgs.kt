package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class InspeccionMaterialFragmentArgs(
  public val descripcionMaterial: String,
  public val proveedor: String,
  public val cantidadMaterial: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("descripcionMaterial", this.descripcionMaterial)
    result.putString("proveedor", this.proveedor)
    result.putString("cantidadMaterial", this.cantidadMaterial)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("descripcionMaterial", this.descripcionMaterial)
    result.set("proveedor", this.proveedor)
    result.set("cantidadMaterial", this.cantidadMaterial)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): InspeccionMaterialFragmentArgs {
      bundle.setClassLoader(InspeccionMaterialFragmentArgs::class.java.classLoader)
      val __descripcionMaterial : String?
      if (bundle.containsKey("descripcionMaterial")) {
        __descripcionMaterial = bundle.getString("descripcionMaterial")
        if (__descripcionMaterial == null) {
          throw IllegalArgumentException("Argument \"descripcionMaterial\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"descripcionMaterial\" is missing and does not have an android:defaultValue")
      }
      val __proveedor : String?
      if (bundle.containsKey("proveedor")) {
        __proveedor = bundle.getString("proveedor")
        if (__proveedor == null) {
          throw IllegalArgumentException("Argument \"proveedor\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"proveedor\" is missing and does not have an android:defaultValue")
      }
      val __cantidadMaterial : String?
      if (bundle.containsKey("cantidadMaterial")) {
        __cantidadMaterial = bundle.getString("cantidadMaterial")
        if (__cantidadMaterial == null) {
          throw IllegalArgumentException("Argument \"cantidadMaterial\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cantidadMaterial\" is missing and does not have an android:defaultValue")
      }
      return InspeccionMaterialFragmentArgs(__descripcionMaterial, __proveedor, __cantidadMaterial)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        InspeccionMaterialFragmentArgs {
      val __descripcionMaterial : String?
      if (savedStateHandle.contains("descripcionMaterial")) {
        __descripcionMaterial = savedStateHandle["descripcionMaterial"]
        if (__descripcionMaterial == null) {
          throw IllegalArgumentException("Argument \"descripcionMaterial\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"descripcionMaterial\" is missing and does not have an android:defaultValue")
      }
      val __proveedor : String?
      if (savedStateHandle.contains("proveedor")) {
        __proveedor = savedStateHandle["proveedor"]
        if (__proveedor == null) {
          throw IllegalArgumentException("Argument \"proveedor\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"proveedor\" is missing and does not have an android:defaultValue")
      }
      val __cantidadMaterial : String?
      if (savedStateHandle.contains("cantidadMaterial")) {
        __cantidadMaterial = savedStateHandle["cantidadMaterial"]
        if (__cantidadMaterial == null) {
          throw IllegalArgumentException("Argument \"cantidadMaterial\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"cantidadMaterial\" is missing and does not have an android:defaultValue")
      }
      return InspeccionMaterialFragmentArgs(__descripcionMaterial, __proveedor, __cantidadMaterial)
    }
  }
}
