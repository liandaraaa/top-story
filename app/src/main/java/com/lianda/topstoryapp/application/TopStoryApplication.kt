package com.lianda.topstoryapp.application

import android.app.Application
import com.lianda.topstoryapp.depth.koin.KoinContext
import com.lianda.topstoryapp.depth.module.preferenceModule
import com.lianda.topstoryapp.depth.module.serviceModule
import com.lianda.topstoryapp.depth.module.storyModule
import com.lianda.topstoryapp.depth.module.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TopStoryApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        KoinContext.initialize(applicationContext)

        startKoin {
            androidContext(this@TopStoryApplication)
            modules(
                listOf(
                    serviceModule,
                    utilityModule,
                    preferenceModule,
                    storyModule
                )
            )
        }

    }
}