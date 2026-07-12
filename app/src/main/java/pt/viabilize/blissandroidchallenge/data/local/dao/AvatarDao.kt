package pt.viabilize.blissandroidchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pt.viabilize.blissandroidchallenge.data.local.model.AvatarEntity

@Dao
interface AvatarDao {

    @Query("SELECT * FROM avatar")
    fun getAll(): List<AvatarEntity>

    @Query("SELECT * FROM avatar WHERE login = :username")
    fun findByName(username: String): AvatarEntity?

    @Insert
    fun insertAll(vararg avatarEntities: AvatarEntity)

    @Delete
    fun delete(avatar: AvatarEntity)
}