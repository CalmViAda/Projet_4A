package com.example.projet_4a.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.context.startKoin

class Application4A :Application()
{
    override fun onCreate()
    {
        super.onCreate()
        //star Koin!!
        startKoin{
            //Android Context
            androidContext(this@Application4A)
            //modules
            modules(presentationModule, domainModule, dataModule)
        }
    }
}