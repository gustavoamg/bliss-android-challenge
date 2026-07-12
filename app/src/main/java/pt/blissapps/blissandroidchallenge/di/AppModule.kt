package pt.blissapps.blissandroidchallenge.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.blissapps.blissandroidchallenge.repository.GitHubRepository
import pt.blissapps.blissandroidchallenge.repository.GitHubRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindGitHubRepository(
        gitHubRepositoryImpl: GitHubRepositoryImpl
    ) : GitHubRepository
}