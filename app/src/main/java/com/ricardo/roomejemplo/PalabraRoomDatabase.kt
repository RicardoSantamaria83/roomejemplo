package com.ricardo.roomejemplo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Palabra::class), version = 1, exportSchema = false)
public abstract class PalabraRoomDatabase : RoomDatabase(){
    abstract fun palabraDao(): PalabraDao

    private class PalabraDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.palabraDao())
                }
            }
        }

        suspend fun populateDatabase(palabraDao: PalabraDao) {
            // Delete all content here.
            palabraDao.deleteAll()

            // Add sample words.
            var palabra = Palabra("Hello")
            palabraDao.insert(palabra)
            palabra = Palabra("World!")
            palabraDao.insert(palabra)

            // TODO: Add your own words!
            palabra = Palabra("Ejemplo")
            palabraDao.insert(palabra)

        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PalabraRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PalabraRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PalabraRoomDatabase::class.java,
                    "palabras_database"
                ).addCallback(PalabraDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}