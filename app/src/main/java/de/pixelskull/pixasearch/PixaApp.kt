package de.pixelskull.pixasearch

import android.app.Application

class PixaApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: PixaApp? = null

        fun applicationContext() : PixaApp {
            return if (instance == null) {
                instance = PixaApp()
                instance as PixaApp
            } else {
                instance as PixaApp
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext()
    }
}