package com.example.ucp2.ui.navigation

interface AlamatNavigasi{
    val route : String

    object DestinasiHome : AlamatNavigasi{
        override val route = "home"
    }
    object DestinasiDsnInsert : AlamatNavigasi{
        override val route = "insertdosen"
    }

    object DestinasiMkInsert : AlamatNavigasi{
        override val route = "insertmk"
    }

    object DestinasiMkDetail : AlamatNavigasi{
        override val route = "detail"
        const val kode = "nidn"
        val routesWithArg = "$route/{$kode}"
    }

    object DestinasiUpdate : AlamatNavigasi{
        override val route = "update"
        const val kode = "nidn"
        val routesWithArg = "$route/{$kode}"
    }

    object DestinasiListDosen : AlamatNavigasi{
        override val route = "listDosen"
    }

    object DestinasiListMk : AlamatNavigasi{
        override val route = "listMataKuliah"
    }
}