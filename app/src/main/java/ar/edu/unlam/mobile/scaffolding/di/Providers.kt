package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Providers {
    @Binds
    @Singleton
    abstract fun bindUserRepository(repo: UserDefaultRepository): UserRepository

    @Binds
    @Singleton
    abstract fun bindTuitsRepository(repo: TuitsDefaultRepository): TuitsRepository
}
