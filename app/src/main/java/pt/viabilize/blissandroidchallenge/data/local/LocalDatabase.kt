package pt.viabilize.blissandroidchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.viabilize.blissandroidchallenge.data.local.dao.AvatarDao
import pt.viabilize.blissandroidchallenge.data.local.dao.EmojiDao
import pt.viabilize.blissandroidchallenge.data.local.model.AvatarEntity
import pt.viabilize.blissandroidchallenge.data.local.model.EmojiEntity

@Database ( entities = [EmojiEntity::class, AvatarEntity::class ], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun emojiDao () : EmojiDao

    abstract fun avatarDao() : AvatarDao
}