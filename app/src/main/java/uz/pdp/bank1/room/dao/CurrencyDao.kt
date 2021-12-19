package uz.pdp.bank1.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Flowable
import uz.pdp.nbuapp.room.entity.Currency

@Dao
interface CurrencyDao {

    @Insert(onConflict = REPLACE)
    fun addCurrency(currency: Currency)

    @Query("select * from currency")
    fun getAll(): Flowable<List<Currency>>

    @Query("select * from currency")
    fun getAllCur(): List<Currency>

}