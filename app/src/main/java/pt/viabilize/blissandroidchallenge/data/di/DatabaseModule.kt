package pt.viabilize.blissandroidchallenge.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.viabilize.blissandroidchallenge.data.local.LocalDatabase
import pt.viabilize.blissandroidchallenge.data.local.dao.EmojiDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase (@ApplicationContext applicationContext: Context) : LocalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            LocalDatabase::class.java, "bliss-chalenge-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideEmojiDao (localDatabase: LocalDatabase) : EmojiDao {
        return localDatabase.emojiDao()
    }
}