package pa.chan.main_page.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pa.chan.main_page.data.MainRepositoryImpl
import pa.chan.main_page.domain.MainRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class MainPageModule {
    @Binds
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}