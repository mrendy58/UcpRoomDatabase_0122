package com.example.ucp2

import android.app.Application
import com.example.ucp2.dependenciesinjection.ContainerApp

class KrsApp : Application() {
    //fungsi untuk menyimpan instance ContainerApp
    lateinit var containerApp: ContainerApp
        private set // Menjaga encapsulasi agar hanya bisa di-set di dalam class ini.

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = ContainerApp(this)
        // Instance adalah object yang dibuat dari class
    }
}