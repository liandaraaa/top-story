package com.lianda.topstoryapp.depth.module

import com.lianda.topstoryapp.BuildConfig
import com.lianda.topstoryapp.depth.service.OkhttpClientFactory
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL = "base_url"

val serviceModule = module {
    single { return@single OkhttpClientFactory.create()}
    single(named(BASE_URL)){BuildConfig.BASE_URL}
}

val rxModule = module{
    factory { CompositeDisposable() }
}

val utilityModule = module{
    single { Gson() }
}
