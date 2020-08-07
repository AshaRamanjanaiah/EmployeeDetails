package com.employeedetailsapp

import android.app.Application
import timber.log.Timber

class EmployeeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}