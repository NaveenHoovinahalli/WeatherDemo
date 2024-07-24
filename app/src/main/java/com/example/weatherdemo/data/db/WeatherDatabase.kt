package com.example.weatherdemo.data.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherdemo.model.WeatherResponse

@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
