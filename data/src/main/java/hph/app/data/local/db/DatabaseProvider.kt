package hph.app.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun init(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "schedule_db"
        ).build()
    }

    fun getDatabase(): AppDatabase {
        return database ?: throw IllegalStateException("Database not initialized! Call init() first")
    }
}

