package pt.blissapps.blissandroidchallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("AVATAR")
data class AvatarEntity(
    @PrimaryKey @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "LOGIN") val login: String,
    @ColumnInfo(name = "AVATAR_URL") val avatarUrl: String
)
