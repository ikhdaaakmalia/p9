package com.ikhdaamel.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiDetail : AlamatNavigasi{
    override val route = "Detail Dosen"
    const val NIDN = "nidn"
    val routesWithArg = "$route/ {$NIDN}"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route = "Detail Mata Kuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"

}
