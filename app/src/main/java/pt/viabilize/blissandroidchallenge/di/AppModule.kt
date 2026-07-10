package pt.viabilize.blissandroidchallenge.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.viabilize.blissandroidchallenge.repository.GitHubRepository
import pt.viabilize.blissandroidchallenge.repository.GitHubRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindGitHubRepository(
        gitHubRepositoryImpl: GitHubRepositoryImpl
    ) : GitHubRepository
}