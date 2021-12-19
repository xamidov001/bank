package uz.pdp.bank1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.Observable
import uz.pdp.bank1.R
import uz.pdp.bank1.adapters.SpinnerAdapter
import uz.pdp.bank1.databinding.FragmentCurBinding
import uz.pdp.currencyapp.preference.MyShared
import uz.pdp.nbuapp.room.database.DatabaseHelper
import uz.pdp.nbuapp.room.entity.Currency
import java.util.concurrent.TimeUnit

class CurFragment : Fragment(R.layout.fragment_cur) {

    private val binding by viewBinding(FragmentCurBinding::bind)
    private var myShared = MyShared
    private lateinit var adapter: SpinnerAdapter
    private var spin_ = -1
    private lateinit var listSpin: ArrayList<Currency>
    private lateinit var databaseHelper: DatabaseHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myShared = MyShared.getInstance(requireContext())
        databaseHelper = DatabaseHelper.getInstance(requireContext())

        binding.apply {

            val list1 = myShared.getList("id")
            spin_ = list1.toInt()
            listSpin = ArrayList(databaseHelper.helper().getAllCur())

            adapter = SpinnerAdapter(requireContext(), listSpin)
            spinLine.adapter = adapter
            if (spin_>-1) {
                spinLine.setSelection(spin_)
            }
            spin1.adapter = adapter
            loadText(binding.editTxt.text.toString())

            card.setOnClickListener {
                loadText(binding.editTxt.text.toString())
            }

            spinLine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loadText(editTxt.text.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

            spin1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loadText(editTxt.text.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

            createObservable()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe{
                    loadText(it)
                }

            change.setOnClickListener {
                val n = spin1.selectedItemPosition
                if (n < 24) {
                    spin1.setSelection(spinLine.selectedItemPosition)
                    spinLine.setSelection(n)
                    loadText(binding.editTxt.text.toString())
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val list1 = myShared.getList("int")
        spin_ = list1.toInt()
        if (spin_>-1) {
            binding.spinLine.setSelection(spin_)
        }
    }

    private fun createObservable(): Observable<String> {
        return Observable.create{ emitter ->
            binding.editTxt.addTextChangedListener {
                emitter.onNext(it.toString())
            }
        }
    }

    private fun loadText(edit: String) {
        binding.apply {
            var sum = 1.0
            if (edit.isNotEmpty())
                sum = edit.toDouble()
            val selectedItemPosition = spinLine.selectedItemPosition
            val selectedItemPosition1 = spin1.selectedItemPosition
            val curruncy = listSpin[selectedItemPosition]
            if (listSpin.size-1 == selectedItemPosition1) {
                if (curruncy.nbu_buy_price.isEmpty()) {
                    var price = curruncy.cb_price.toDouble()
                    price *= sum
                    var narx = price.toString()
                    if (narx.length>narx.indexOf(".")+3)
                        narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                    sotibOlishUzs.text = narx
                    sotishUzs.text = narx
                } else {
                    var price = curruncy.nbu_buy_price.toDouble()
                    price *= sum
                    var narx = price.toString()
                    if (narx.length>narx.indexOfLast{it=='.'}+3)
                        narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                    sotibOlishUzs.text = narx
                    var price1 = curruncy.nbu_cell_price.toDouble()
                    price1 *= sum
                    var narx1 = price1.toString()
                    if (narx1.length>narx1.indexOfLast{it=='.'}+3)
                        narx1 = narx1.substring(0, narx1.indexOfLast{it=='.'}+3)
                    sotishUzs.text = narx1
                }
            } else {
                val curruncy1 = listSpin[selectedItemPosition1]
                if (curruncy1.nbu_buy_price.isEmpty()) {
                    if (curruncy.nbu_buy_price.isEmpty()) {
                        val price = curruncy.cb_price.toDouble()
                        val price1 = curruncy1.cb_price.toDouble()
                        var narx = (price * sum / price1).toString()
                        if (narx.length>narx.indexOfLast{it=='.'}+3)
                            narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                        sotibOlishUzs.text = narx
                        var narx1 = (price * sum / price1).toString()
                        if (narx1.length>narx1.indexOfLast{it=='.'}+3)
                            narx1 = narx1.substring(0, narx1.indexOfLast{it=='.'}+3)
                        sotishUzs.text = narx1
                    } else {
                        val priceBuy = curruncy.nbu_buy_price.toDouble()
                        val priceCell = curruncy.nbu_cell_price.toDouble()
                        val price1 = curruncy1.cb_price.toDouble()
                        var narx = (priceBuy*sum/price1).toString()
                        if (narx.length>narx.indexOfLast{it=='.'}+3)
                            narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                        sotibOlishUzs.text = narx
                        var narx1 = (priceCell*sum/price1).toString()
                        if (narx1.length>narx1.indexOfLast{it=='.'}+3)
                            narx1 = narx1.substring(0, narx1.indexOfLast{it=='.'}+3)
                        sotishUzs.text = narx1
                    }
                } else {
                    if (curruncy.nbu_buy_price.isEmpty()) {
                        val priceBuy = curruncy1.nbu_buy_price.toDouble()
                        val priceCell = curruncy1.nbu_cell_price.toDouble()
                        val price1 = curruncy.cb_price.toDouble()
                        var narx1 = (price1*sum/priceBuy).toString()
                        if (narx1.length>narx1.indexOfLast{it=='.'}+3)
                            narx1 = narx1.substring(0, narx1.indexOfLast{it=='.'}+3)
                        sotibOlishUzs.text = narx1
                        var narx = (price1*sum/priceCell).toString()
                        if (narx.length>narx.indexOfLast{it=='.'}+3)
                            narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                        sotishUzs.text = narx
                    } else {
                        val priceBuy = curruncy.nbu_buy_price.toDouble()
                        val priceCell = curruncy.nbu_cell_price.toDouble()
                        val priceBuy1 = curruncy1.nbu_buy_price.toDouble()
                        val priceCell1 = curruncy1.nbu_cell_price.toDouble()
                        var narx = (priceBuy*sum/priceBuy1).toString()
                        if (narx.length>narx.indexOfLast{it=='.'}+3)
                            narx = narx.substring(0, narx.indexOfLast{it=='.'}+3)
                        sotibOlishUzs.text = narx
                        var narx1 = (priceCell*sum/priceCell1).toString()
                        if (narx1.length>narx1.indexOfLast{it=='.'}+3)
                            narx1 = narx1.substring(0, narx1.indexOfLast{it=='.'}+3)
                        sotishUzs.text = narx1
                    }
                }
            }
        }

    }
}