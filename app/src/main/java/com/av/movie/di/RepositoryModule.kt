package com.av.movie.di

import com.av.movie.data.repository.ActorRepositoryImp
import com.av.movie.data.repository.MovieRepositoryImp
import com.av.movie.domain.repository.ActorRepository
import com.av.movie.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        repository: MovieRepositoryImp
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindActorRepository(
        repository: ActorRepositoryImp
    ): ActorRepository
}
