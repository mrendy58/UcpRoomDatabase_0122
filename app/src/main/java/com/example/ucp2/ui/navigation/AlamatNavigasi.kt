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
        const val NIDN = "nidn"
        val routesWithArg = "$route/{$NIDN}"
    }

    object DestinasiUpdate : AlamatNavigasi{
        override val route = "update"
        const val NIDN = "nidn"
        val routesWithArg = "$route/{$NIDN}"
    }

    object DestinasiListDosen : AlamatNavigasi{
        override val route = "listDosen"
    }

    object DestinasiListMk : AlamatNavigasi{
        override val route = "listMataKuliah"
    }
}