package com.lianda.topstoryapp.depth.module

import com.lianda.topstoryapp.BuildConfig
import com.lianda.topstoryapp.depth.service.OkhttpClientFactory
import com.google.gson.Gson
import com.lianda.topstoryapp.data.preference.StoryPreference
import com.lianda.topstoryapp.data.remote.Api
import com.lianda.topstoryapp.data.remote.ApiClient
import com.lianda.topstoryapp.data.source.DataSource
import com.lianda.topstoryapp.data.source.RepositoryImpl
import com.lianda.topstoryapp.depth.service.RetrofitService
import com.lianda.topstoryapp.ui.viewmodel.StoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL = "base_url"

val serviceModule = module {
    single { return@single OkhttpClientFactory.create() }
    single(named(BASE_URL)) { BuildConfig.BASE_URL }
}

val utilityModule = module {
    single { Gson() }
}

val preferenceModule = module {
    single { StoryPreference(get()) }
}

val storyModule = module {
    single {
        RetrofitService.createReactiveService(
            ApiClient::class.java,
            get(),
            get(named(BASE_URL))
        )
    }
    single { Api(get()) }
    single<RepositoryImpl> { DataSource(get()) }
    viewModel { StoryViewModel(get()) }
}
