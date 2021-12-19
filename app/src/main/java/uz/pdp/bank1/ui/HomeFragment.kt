package uz.pdp.bank1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.bank1.R
import uz.pdp.bank1.adapters.SliderAdapter
import uz.pdp.bank1.classes.CurrencyRetro
import uz.pdp.bank1.databinding.FragmentHome2Binding
import uz.pdp.bank1.network.NetworkHelper
import uz.pdp.nbuapp.retro.ApiClient
import uz.pdp.nbuapp.room.database.DatabaseHelper
import uz.pdp.nbuapp.room.entity.Currency
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.fragment_home2) {

    private val binding by viewBinding(FragmentHome2Binding::bind)
    private lateinit var myDatabase: DatabaseHelper
    private lateinit var list: List<Currency>
    lateinit var sliderAdapter: SliderAdapter


    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDatabase = DatabaseHelper.getInstance(requireContext())

        binding.apply {

            if (isHaveNet()) {
                ApiClient.appService.getData().enqueue(object : Callback<List<CurrencyRetro>> {
                    override fun onResponse(call: Call<List<CurrencyRetro>>, response: Response<List<CurrencyRetro>>) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            body?.forEachIndexed { i, currencyRetro ->
                                val st = "https://nbu.uz/local/templates/nbu/images/flags/${body[i].code}.png"
                                val currency = Currency(id = body[i].code, cb_price = body[i].cb_price, nbu_buy_price = body[i].nbu_buy_price, nbu_cell_price = body[i].nbu_cell_price, imgUrl = st)
                                myDatabase.helper().addCurrency(currency)
                            }
                            myDatabase.helper().addCurrency(Currency("UZB", "","","","https://flagcdn.com/w160/uz.png"))

                        }
                    }

                    override fun onFailure(call: Call<List<CurrencyRetro>>, t: Throwable) {

                    }

                })
            } else {

            }

            myDatabase.helper().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list = ArrayList(it)
                    setViewPager()
                },{})

        }
    }

    private fun setViewPager() {
        binding.apply {
            sliderAdapter = SliderAdapter(requireContext(), list)
            viewpager2.adapter = sliderAdapter
            viewpager2.clipToPadding = false
            viewpager2.clipChildren = false
            viewpager2.offscreenPageLimit = 3
            viewpager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(20))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.75f+r*0.15f
            }
            viewpager2.setPageTransformer(compositePageTransformer)
            TabLayoutMediator(tablayout, viewpager2
            ) { tab, position ->
                tab.text = list[position].id
            }.attach()
        }
    }

    fun isHaveNet(): Boolean {
        val networkHelper = NetworkHelper(requireContext())

        return networkHelper.isNetworkConnect()
    }
}