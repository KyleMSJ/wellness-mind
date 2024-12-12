package ifsp.project.welnessmind.domain.model.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ifsp.project.welnessmind.data.repository.AccountServiceImpl
import ifsp.project.welnessmind.domain.model.AccountService

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}