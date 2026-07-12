package pt.blissapps.blissandroidchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pt.blissapps.blissandroidchallenge.data.local.model.EmojiEntity

@Dao
interface EmojiDao {

    @Query("SELECT * FROM emoji")
    fun getAll(): List<EmojiEntity>

    @Query("SELECT * FROM emoji WHERE name = :name")
    fun findByName(name: String): EmojiEntity

    @Insert
    fun insertAll(vararg emojiEntities: EmojiEntity)

    @Delete
    fun delete(user: EmojiEntity)

    @Query("DELETE FROM emoji WHERE name = :name")
    fun deleteByName(name: Int)
}