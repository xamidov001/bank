package uz.pdp.bank1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.bank1.R
import uz.pdp.bank1.adapters.ViewAdapter
import uz.pdp.bank1.databinding.FragmentHome2Binding
import uz.pdp.bank1.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var viewAdapter: ViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewAdapter = ViewAdapter(requireActivity())

        binding.apply {

            viewPager2.adapter = viewAdapter

            viewPager2.isUserInputEnabled = false

            bottomNav.setOnItemSelectedListener {
                viewPager2.currentItem = it
            }

            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

                override fun onPageSelected(position: Int) {
                    bottomNav.itemActiveIndex = position
                }
            })
        }
    }
}