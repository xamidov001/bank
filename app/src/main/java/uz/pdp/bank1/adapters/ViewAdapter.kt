package uz.pdp.bank1.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.bank1.ui.CurFragment
import uz.pdp.bank1.ui.HomeFragment
import uz.pdp.bank1.ui.ListFragment

class ViewAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> ListFragment()
            2 -> CurFragment()
            else -> HomeFragment()
        }
    }
}