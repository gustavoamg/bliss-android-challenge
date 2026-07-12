package pt.blissapps.blissandroidchallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "EMOJI")
data class EmojiEntity (
    @PrimaryKey @ColumnInfo(name = "NAME") val name: String,
    @ColumnInfo(name = "URL") val url: String
)