package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import androidx.navigation.NavDirections
import com.albertmed.sizlectorcodigos.R
import kotlin.Int
import kotlin.String

public class MaterialesInspeccionFragmentDirections private constructor() {
  private data class ActionMaterialesInspeccionFragmentToInspeccionMaterialFragment(
    public val descripcionMaterial: String,
    public val proveedor: String,
    public val cantidadMaterial: String,
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_materialesInspeccionFragment_to_inspeccionMaterialFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("descripcionMaterial", this.descripcionMaterial)
        result.putString("proveedor", this.proveedor)
        result.putString("cantidadMaterial", this.cantidadMaterial)
        return result
      }
  }

  public companion object {
    public fun actionMaterialesInspeccionFragmentToInspeccionMaterialFragment(
      descripcionMaterial: String,
      proveedor: String,
      cantidadMaterial: String,
    ): NavDirections =
        ActionMaterialesInspeccionFragmentToInspeccionMaterialFragment(descripcionMaterial,
        proveedor, cantidadMaterial)
  }
}
