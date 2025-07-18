package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import androidx.navigation.NavDirections
import com.albertmed.sizlectorcodigos.R
import kotlin.Int
import kotlin.String

public class NuevaInspeccionFragmentDirections private constructor() {
  private data class ActionNuevaInspeccionFragmentToMaterialesInspeccionFragment(
    public val codigoEscaneado: String,
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_nuevaInspeccionFragment_to_materialesInspeccionFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("codigoEscaneado", this.codigoEscaneado)
        return result
      }
  }

  public companion object {
    public fun actionNuevaInspeccionFragmentToMaterialesInspeccionFragment(codigoEscaneado: String):
        NavDirections = ActionNuevaInspeccionFragmentToMaterialesInspeccionFragment(codigoEscaneado)
  }
}
