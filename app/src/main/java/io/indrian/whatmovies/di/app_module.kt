package io.indrian.whatmovies.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.indrian.whatmovies.data.db.AppDatabase
import io.indrian.whatmovies.data.mapper.*
import io.indrian.whatmovies.data.repositories.LocalRepository
import io.indrian.whatmovies.data.repositories.RemoteRepository
import io.indrian.whatmovies.data.repositories.Repository
import io.indrian.whatmovies.data.service.DiscoverService
import io.indrian.whatmovies.data.service.MovieService
import io.indrian.whatmovies.data.service.SearchService
import io.indrian.whatmovies.data.service.TVShowService
import io.indrian.whatmovies.ui.favoritemovie.FavoriteMovieVM
import io.indrian.whatmovies.ui.favoritetvshow.FavoriteTVShowVM
import io.indrian.whatmovies.ui.movie.MovieVM
import io.indrian.whatmovies.ui.moviedetail.MovieDetailVM
import io.indrian.whatmovies.ui.movieinfo.MovieInfoVM
import io.indrian.whatmovies.ui.searchmovie.SearchMovieVM
import io.indrian.whatmovies.ui.searchtvshow.SearchTVShowVM
import io.indrian.whatmovies.ui.settings.SettingsVM
import io.indrian.whatmovies.ui.tvshow.TVShowVM
import io.indrian.whatmovies.ui.tvshowdetail.TVShowDetailVM
import io.indrian.whatmovies.ui.tvshowinfo.TVShowInfoVM
import io.indrian.whatmovies.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object NullToEmptyStringAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

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

        val moshi = Moshi.Builder()
            .add(NullToEmptyStringAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        retrofit
    }
}

val serviceModule = module {

    single { get<Retrofit>().create(MovieService::class.java) }
    single { get<Retrofit>().create(TVShowService::class.java) }
    single { get<Retrofit>().create(SearchService::class.java) }
    single { get<Retrofit>().create(DiscoverService::class.java) }
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
    viewModel { SettingsVM(androidApplication()) }
    viewModel { (handle: SavedStateHandle) -> TVShowDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieDetailVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MovieInfoVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> TVShowInfoVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> FavoriteMovieVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> FavoriteTVShowVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> SearchMovieVM(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> SearchTVShowVM(handle, get()) }
}
