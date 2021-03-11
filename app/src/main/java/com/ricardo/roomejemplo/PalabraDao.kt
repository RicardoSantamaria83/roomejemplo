package com.ricardo.roomejemplo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PalabraDao {

    @Query("SELECT * FROM palabra_table ORDER BY palabra ASC")
    fun getAlphabetizedWords(): Flow<List<Palabra>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(palabra: Palabra)

    @Query("DELETE FROM palabra_table")
    suspend fun deleteAll()
}