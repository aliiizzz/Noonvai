package ir.aliiz.noonvai

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(
                viewmodels,
                roomModule,
                repoModule
            ))
        }
    }
}