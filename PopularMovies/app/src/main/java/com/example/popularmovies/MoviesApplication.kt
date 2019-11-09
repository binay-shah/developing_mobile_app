package com.example.popularmovies

import android.app.Application
import timber.log.Timber

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}