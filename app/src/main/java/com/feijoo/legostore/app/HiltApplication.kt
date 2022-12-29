package com.feijoo.legostore.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(applicationContext)

    }

}