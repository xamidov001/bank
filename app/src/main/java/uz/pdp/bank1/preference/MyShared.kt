package uz.pdp.currencyapp.preference

import android.content.Context
import android.content.SharedPreferences

object MyShared {

    val myShader = MyShared
    var sharedPreferences : SharedPreferences? = null
    var editor : SharedPreferences.Editor? = null

    fun getInstance(context: Context): MyShared {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("file", Context.MODE_PRIVATE)
        }

        return myShader
    }

    fun getList(str: String): Int = sharedPreferences?.getInt(str, 0)!!

    fun setList(text: Int, str: String) {
        editor = sharedPreferences?.edit()
        editor?.putInt(str, text)?.commit()
    }

}