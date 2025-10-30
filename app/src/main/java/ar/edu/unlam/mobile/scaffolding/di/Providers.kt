package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Providers {
    @Binds
    abstract fun bindUserRepository(repo: UserDefaultRepository): UserRepository

    @Binds
    abstract fun bindTuitsRepository(repo: TuitsDefaultRepository): TuitsRepository
}
