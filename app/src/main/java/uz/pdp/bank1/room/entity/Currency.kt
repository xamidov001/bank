package uz.pdp.nbuapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(

    @PrimaryKey
    var id: String,
    var cb_price: String,
    var nbu_buy_price: String,
    var nbu_cell_price: String,
    var imgUrl: String

)