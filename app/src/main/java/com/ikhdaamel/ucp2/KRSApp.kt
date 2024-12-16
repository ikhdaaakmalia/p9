package com.ikhdaamel.ucp2

import android.app.Application
import com.ikhdaamel.ucp2.data.dependenciesinjection.ContainerApp
import com.ikhdaamel.ucp2.data.dependenciesinjection.InterfaceContainerApp

class KRSApp: Application() {
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}