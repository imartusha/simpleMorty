package com.example.simplemorty.di

import androidx.room.Room
import com.example.simplemorty.data.database.character.CachedCharacterDao
import com.example.simplemorty.data.database.character.CharacterDao
import com.example.simplemorty.data.database.character.CharacterRemoteKeyDao
import com.example.simplemorty.data.database.character.CharactersDataBase
import com.example.simplemorty.data.database.character.MY_DATA_BASE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.data.network.api.location.LocationApi
import com.example.simplemorty.data.repository.EpisodesRepositoryImpl
import com.example.simplemorty.data.repository.character.CharactersRepositoryImpl
import com.example.simplemorty.data.repository.LocationsRepositoryImpl
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
import com.example.simplemorty.domain.useCase.episode.GetInfoEpisodeByIdUseCase
import com.example.simplemorty.domain.useCase.location.GetInfoLocationByIdUseCase
import com.example.simplemorty.presentation.screens.characters_list.CharactersViewModel
import com.example.simplemorty.presentation.screens.episodes_list.EpisodesViewModel
import com.example.simplemorty.presentation.screens.character_info.InfoCharacterViewModel
import com.example.simplemorty.presentation.screens.episode_info.InfoEpisodeViewModel
import com.example.simplemorty.presentation.screens.locations_list.LocationsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun injectFeature() = loadFeature

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
            getInfoEpisodeByIdUseCase = get(),
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
            getCharacterUseCase = get()
        )
    }
    viewModel<InfoEpisodeViewModel> {
        InfoEpisodeViewModel(
            getInfoEpisodeByIdUseCase = get(),
            getCharactersListForInfo = get()
        )
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
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

    single<CharacterDao> {
        get<CharactersDataBase>()
            .getCharacterDao()
    }
    single<CharacterRemoteKeyDao> {
        get<CharactersDataBase>()
            .remoteKeysDao
    }
    single<CachedCharacterDao> {
        get<CharactersDataBase>()
            .cacheDao
    }

    single<CharactersDataBase> {
        Room.databaseBuilder(
            androidContext(),
            CharactersDataBase::class.java,
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
            characterDao = get(),
            charactersDataBase = get()
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
            locationApi = get()
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
            charactersDataBase = get()
        )
    }
    factory<GetInfoEpisodeByIdUseCase> {
        GetInfoEpisodeByIdUseCase(
            episodesRepository = get()
        )
    }
    factory<GetAllEpisodesUseCase> {
        GetAllEpisodesUseCase(
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