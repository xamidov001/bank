package uz.pdp.nbuapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.pdp.bank1.room.dao.CurrencyDao
import uz.pdp.nbuapp.room.entity.Currency

@Database(entities = [Currency::class], version = 1)
abstract class DatabaseHelper: RoomDatabase() {

    abstract fun helper(): CurrencyDao

    companion object {

        var databaseHelper: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (databaseHelper == null ) {
                databaseHelper = Room.databaseBuilder(context, DatabaseHelper::class.java, "my_data")
                    .allowMainThreadQueries()
                    .build()
            }

            return databaseHelper!!
        }

    }
}