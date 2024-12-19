package com.ikhdaamel.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route= "Home"
}

object DestinasiHomeDosen : AlamatNavigasi{
    override val route = "Home Dosen"
    const val NIDN = "nidn"
    val routesWithArg = "$route/ {$NIDN}"
}

object DestinasiInsertDosen : AlamatNavigasi {
    override val route = "Insert Dosen"
}

object DestinasiInsertMataKuliah : AlamatNavigasi {
    override val route: String = " Input Mata Kuliah"
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
