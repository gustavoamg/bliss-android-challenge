package pt.viabilize.blissandroidchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.viabilize.blissandroidchallenge.data.local.dao.EmojiDao
import pt.viabilize.blissandroidchallenge.data.local.model.EmojiEntity

@Database ( entities = [EmojiEntity::class ], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun emojiDao () : EmojiDao
}