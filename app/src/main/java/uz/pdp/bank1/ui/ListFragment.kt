package uz.pdp.bank1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.ibrahimsn.lib.SmoothBottomBar
import uz.pdp.bank1.R
import uz.pdp.bank1.adapters.RecAdapter
import uz.pdp.bank1.databinding.FragmentListBinding
import uz.pdp.currencyapp.preference.MyShared
import uz.pdp.nbuapp.room.database.DatabaseHelper

class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private lateinit var recAdapter: RecAdapter
    private var myShared = MyShared
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myShared = MyShared.getInstance(requireContext())
        databaseHelper = DatabaseHelper.getInstance(requireContext())

        recAdapter = RecAdapter(requireContext(), object : RecAdapter.OnMyClickListener{
            override fun onClick(position: Int) {
                myShared.setList(position, "int")
                val smoothBottomBar =
                    (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_nav) as SmoothBottomBar
                (activity as AppCompatActivity).findViewById<ViewPager2>(R.id.view_pager2).currentItem = 2
                smoothBottomBar.itemActiveIndex=2
            }

        })


        binding.apply {
            recycle.adapter = recAdapter

            databaseHelper.helper().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recAdapter.submitList(it)
                },{})

        }
    }

}