package io.indrian.moviecatalogue.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import io.indrian.moviecatalogue.data.db.AppDatabase
import io.indrian.moviecatalogue.data.mapper.*
import io.indrian.moviecatalogue.data.repositories.LocalRepository
import io.indrian.moviecatalogue.data.repositories.RemoteRepository
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import io.indrian.moviecatalogue.ui.favoritemovie.FavoriteMovieVM
import io.indrian.moviecatalogue.ui.favoritetvshow.FavoriteTVShowVM
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
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val dbModule = module {

    single {

        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, Constant.DB_NAME
        ).build()
    }

    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().tvShowDao() }
    single { get<AppDatabase>().favoriteDao() }
}

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
    single { TVShowDetailMapper() }
    single { MovieDetailMapper() }
    single { FavoriteMovieMapper() }
    single { FavoriteTVShowMapper() }
}

val repoModule = module {

    single {

        LocalRepository(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single {
        RemoteRepository(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single {

        Repository (androidApplication(), get(), get())
    }
}

val appModule = module {

    viewModel { MovieVM(get()) }
    viewModel { TVShowVM(get()) }
    viewModel { SettingsVM() }
    viewModel { (handle: SavedStateHandle) -> TVShowDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieInfoVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> TVShowInfoVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> FavoriteMovieVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> FavoriteTVShowVM(handle, get()) }
}
