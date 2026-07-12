package pt.blissapps.blissandroidchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.blissapps.blissandroidchallenge.data.local.dao.AvatarDao
import pt.blissapps.blissandroidchallenge.data.local.dao.EmojiDao
import pt.blissapps.blissandroidchallenge.data.local.model.AvatarEntity
import pt.blissapps.blissandroidchallenge.data.local.model.EmojiEntity

@Database ( entities = [EmojiEntity::class, AvatarEntity::class ], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun emojiDao () : EmojiDao

    abstract fun avatarDao() : AvatarDao
}