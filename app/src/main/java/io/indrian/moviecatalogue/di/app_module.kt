package io.indrian.moviecatalogue.di

import androidx.lifecycle.SavedStateHandle
import io.indrian.moviecatalogue.data.mapper.DetailTVShowMapper
import io.indrian.moviecatalogue.data.mapper.MovieDetailMapper
import io.indrian.moviecatalogue.data.mapper.MovieMapper
import io.indrian.moviecatalogue.data.mapper.TVShowMapper
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import io.indrian.moviecatalogue.ui.movie.MovieVM
import io.indrian.moviecatalogue.ui.moviedetail.MovieDetailVM
import io.indrian.moviecatalogue.ui.movieinfo.MovieInfoVM
import io.indrian.moviecatalogue.ui.settings.SettingsVM
import io.indrian.moviecatalogue.ui.tvshow.TVShowVM
import io.indrian.moviecatalogue.ui.tvshowdetail.TVShowDetailVM
import io.indrian.moviecatalogue.ui.tvshowinfo.TVShowInfoVM
import io.indrian.moviecatalogue.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BASIC

    val authInterceptor = Interceptor { chain ->

        val newUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constant.API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .build()

    single {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        retrofit
    }
}

val serviceModule = module {

    single { get<Retrofit>().create(MovieService::class.java) }
    single { get<Retrofit>().create(TVShowService::class.java) }
}

val mapperModule = module {

    single { MovieMapper() }
    single { TVShowMapper() }
    single { DetailTVShowMapper() }
    single { MovieDetailMapper() }
}

val repoModule = module {

    single {

        Repository (
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

val appModule = module {

    viewModel { (handle: SavedStateHandle) -> MovieVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> TVShowVM(handle, get()) }
    viewModel { SettingsVM() }
    viewModel { (handle: SavedStateHandle) -> TVShowDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieInfoVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> TVShowInfoVM(handle, get()) }
}
