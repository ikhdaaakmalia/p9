package com.ikhdaamel.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "Home"
}

object DestinasiHomeDosen : AlamatNavigasi {
    override val route = "Home_Dosen"
}

object DestinasiInsertDosen : AlamatNavigasi {
    override val route = "Insert_Dosen"
}

object DestinasiHomeMatKul : AlamatNavigasi {
    override val route = "Home_Matkul"
}

object DestinasiInsertMataKuliah : AlamatNavigasi {
    override val route = "Insert_Mata_Kuliah" // Menghilangkan spasi
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "Update_Mata_Kuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiDetailMatKul : AlamatNavigasi {
    override val route = "Detail_Mata_Kuliah"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}
