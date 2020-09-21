package ir.aliiz.noonvai

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodels = module {
    viewModel { BreadViewModel(get(), get()) }
    viewModel { BasketViewModel(get(), get(), get()) }
    factory { Dispatchers(kotlinx.coroutines.Dispatchers.IO) }
}

val repoModule = module {
    single<NoonvaRepo> {
        RoomNoonvaRepo(get())
    }

    single<BasketRepo> {
        RoomBasketRepo(get(), get())
    }
}

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), NoonvaDatabse::class.java, "noonva").build()
    }
    single {
        get<NoonvaDatabse>().noonvaDao()
    }
    single {
        get<NoonvaDatabse>().basketDao()
    }

    single {
        get<NoonvaDatabse>().getBasketDao()
    }
}