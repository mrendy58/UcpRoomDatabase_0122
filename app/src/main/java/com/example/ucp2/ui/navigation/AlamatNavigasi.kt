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


}