package com.ikhdaamel.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route= "Home"
}

object DestinasiDetailDosen : AlamatNavigasi{
    override val route = "Detail Dosen"
    const val NIDN = "nidn"
    val routesWithArg = "$route/ {$NIDN}"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route = "Detail Mata Kuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiDetailMatKul: AlamatNavigasi{
    override val route = "Detail Mata Kuliah"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}
