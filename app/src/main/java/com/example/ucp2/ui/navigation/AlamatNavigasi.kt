package com.example.ucp2.ui.navigation

interface AlamatNavigasi{
    val route : String

    object DestinasiHome : AlamatNavigasi{
        override val route = "home"
    }

}