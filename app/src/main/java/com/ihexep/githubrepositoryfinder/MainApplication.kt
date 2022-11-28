package com.ihexep.githubrepositoryfinder

import android.app.Application
import com.ihexep.githubrepositoryfinder.di.DataModule
import com.ihexep.githubrepositoryfinder.di.DomainModule
import com.ihexep.githubrepositoryfinder.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(DataModule, DomainModule, PresentationModule)
        }
    }
}