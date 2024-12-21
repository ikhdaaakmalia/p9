package com.ikhdaamel.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route= "Home"
}

object DestinasiHomeDosen : AlamatNavigasi{
    override val route = "Home_Dosen"
//    const val NIDN = "nidn"
//    val routesWithArg = "$route/{$NIDN}"
}

object DestinasiInsertDosen : AlamatNavigasi {
    override val route = "Insert_Dosen"
}

object DestinasiHomeMatKul : AlamatNavigasi{
    override val route = "Home_Matkul"
//    const val KODE = "kode"
//    val routesWithArg = "$route/{$KODE}"
}

object DestinasiInsertMataKuliah : AlamatNavigasi {
    override val route: String = " Input_Mata_Kuliah"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route = "Detail_Mata_Kuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiDetailMatKul: AlamatNavigasi{
    override val route = "Detail_Mata_Kuliah"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}
