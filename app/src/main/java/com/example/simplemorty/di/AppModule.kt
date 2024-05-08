package com.example.simplemorty.di

import androidx.room.Room
import com.example.simplemorty.data.database.character.CachedCharacterDao
import com.example.simplemorty.data.database.character.RemoteKeysDao
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.database.character.FavoriteCharacterDao
import com.example.simplemorty.data.database.character.MY_DATA_BASE
import com.example.simplemorty.data.database.episode.CachedEpisodeDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.data.network.api.location.LocationApi
import com.example.simplemorty.data.repository.EpisodesRepositoryImpl
import com.example.simplemorty.data.repository.character.CharactersRepositoryImpl
import com.example.simplemorty.data.repository.location.LocationsRepositoryImpl
import com.example.simplemorty.data.repository.character.CharacterPagingSource
import com.example.simplemorty.data.repository.character.CharacterRemoteMediator
import com.example.simplemorty.domain.repository.CharactersRepository
import com.example.simplemorty.domain.repository.EpisodesRepository
import com.example.simplemorty.domain.repository.LocationsRepository
import com.example.simplemorty.domain.useCase.character.GetAllCharactersUseCase
import com.example.simplemorty.domain.useCase.episode.GetAllEpisodesUseCase
import com.example.simplemorty.domain.useCase.location.GetAllLocationsUseCase
import com.example.simplemorty.domain.useCase.character.GetCharacterUseCase
import com.example.simplemorty.domain.useCase.character.GetCharactersListForInfo
import com.example.simplemorty.domain.useCase.character.IsCharacterInFavoritesUseCase
import com.example.simplemorty.domain.useCase.episode.GetEpisodeByIdUseCase
import com.example.simplemorty.domain.useCase.episode.GetEpisodesListForCharacterInfoUseCase
import com.example.simplemorty.domain.useCase.location.GetInfoLocationByIdUseCase
import com.example.simplemorty.presentation.screens.characters_list.CharactersViewModel
import com.example.simplemorty.presentation.screens.episodes_list.EpisodesViewModel
import com.example.simplemorty.presentation.screens.character_info.InfoCharacterViewModel
import com.example.simplemorty.presentation.screens.episode_info.InfoEpisodeViewModel
import com.example.simplemorty.presentation.screens.locations_list.LocationsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Cache
import okhttp3.Protocol
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun injectFeature() = loadFeature

fun provideCache(cacheDir: File): Cache {
    val cacheSize = 10 * 1024 * 1024 // 10 MB
    return Cache(cacheDir, cacheSize.toLong())
}


private val loadFeature by lazy {
    GlobalContext.loadKoinModules(appModule)
}
val appModule = module {

    viewModel<CharactersViewModel> {
        CharactersViewModel(
            getAllCharactersUseCase = get()
        )
    }
    viewModel<EpisodesViewModel> {
        EpisodesViewModel(
            getAllEpisodes = get()
        )
    }
    viewModel<LocationsViewModel> {
        LocationsViewModel(
            getInfoLocationByIdUseCase = get(),
            getAllLocationsUseCase = get()
        )
    }
    viewModel<InfoCharacterViewModel> {
        InfoCharacterViewModel(
            getCharacterUseCase = get(),
            isCharacterInFavoritesUseCase = get(),
            getEpisodesListForCharacterInfoUseCase = get()
        )
    }
    viewModel<InfoEpisodeViewModel> {
        InfoEpisodeViewModel(
            getEpisodeByIdUseCase = get(),
            getCharactersListForInfo = get()
        )
    }
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO) // Пример области корутин с использованием диспетчера Dispatchers.IO и SupervisorJob
    }

    single {
        provideCache(androidContext().cacheDir)
    }
    single {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val cache = provideCache(androidContext().cacheDir)
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .protocols(listOf(Protocol.HTTP_1_1))
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(CharacterApi::class.java)
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(LocationApi::class.java)
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(EpisodeApi::class.java)
    }


    single<RemoteKeysDao> {
        get<DataBase>()
            .remoteKeysDao
    }
    single<FavoriteCharacterDao> {
        get<DataBase>()
            .favoriteDao
    }

    single<CachedCharacterDao> {
        get<DataBase>()
            .cacheCharacterDao
    }
    single < CachedEpisodeDao>{
        get<DataBase>()
            .cachedEpisodeDao
    }

    single<DataBase> {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            MY_DATA_BASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<CharacterPagingSource> {
        CharacterPagingSource(
            characterApi = get()
        )
    }
    single<CharactersRepository> {
        CharactersRepositoryImpl(
            characterApi = get(),
            dataBase = get(),
            scope = get()
        )
    }
    single<CharacterRemoteMediator> {
        CharacterRemoteMediator(
            characterApi = get(),
            database = get()
        )
    }
    factory<GetCharacterUseCase> {
        GetCharacterUseCase(
            charactersRepository = get()
        )
    }
    factory<IsCharacterInFavoritesUseCase> {
        IsCharacterInFavoritesUseCase(
            charactersRepository = get()
        )
    }
    factory<GetAllCharactersUseCase> {
        GetAllCharactersUseCase(
            charactersRepository = get()
        )
    }
    factory<GetCharactersListForInfo> {
        GetCharactersListForInfo(
            charactersRepository = get()
        )
    }
    //Locations
    single<LocationsRepository> {
        LocationsRepositoryImpl(
            locationApi = get(),
            scope = get()
        )
    }
    factory<GetInfoLocationByIdUseCase> {
        GetInfoLocationByIdUseCase(
            locationsRepository = get()
        )
    }
    factory<GetAllLocationsUseCase> {
        GetAllLocationsUseCase(
            locationsRepository = get()
        )
    }
    //Episodes
    single<EpisodesRepository> {
        EpisodesRepositoryImpl(
            episodeApi = get(),
            dataBase = get(),
            scope = get()
        )
    }
    factory<GetEpisodeByIdUseCase> {
        GetEpisodeByIdUseCase(
            episodesRepository = get()
        )
    }
    factory<GetAllEpisodesUseCase> {
        GetAllEpisodesUseCase(
            episodesRepository = get()
        )
    }
    factory <GetEpisodesListForCharacterInfoUseCase>{
        GetEpisodesListForCharacterInfoUseCase(
            episodesRepository = get()
        )
    }
//    single<EpisodesDataBase> {
//        Room.databaseBuilder(
//            androidContext(),
//            EpisodesDataBase::class.java,
//            EPISODES_DATA_BASE
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//    single<EpisodeDao> {
//        get<EpisodesDataBase>()
//            .getEpisodeDao()
//    }
//    single<EpisodeRemoteKeyDao> {
//        get<EpisodesDataBase>()
//            .remoteKeysDao
//    }
//    single<CachedEpisodeDao> {
//        get<EpisodesDataBase>()
//            .cacheDao
//    }


//    single<EpisodePagingSource> {
//        EpisodePagingSource(
//            episodeApi = get()
//        )
//    }

//    single<EpisodeRemoteMediator> {
//        EpisodeRemoteMediator(
//            episodeApi = get(),
//            database = get()
//        )
//    }
}