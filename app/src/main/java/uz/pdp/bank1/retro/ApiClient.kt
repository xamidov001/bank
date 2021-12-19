package uz.pdp.nbuapp.retro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.pdp.bank1.retro.ApiImpl

object ApiClient {

    const val BASE_URL = "https://nbu.uz/"

    fun getRetro():Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val appService = getRetro().create(ApiImpl::class.java)

}