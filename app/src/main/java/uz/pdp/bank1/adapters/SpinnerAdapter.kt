package uz.pdp.bank1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import uz.pdp.bank1.databinding.ItemSpinnerBinding
import uz.pdp.nbuapp.room.entity.Currency

class SpinnerAdapter(var context: Context, var list: List<Currency>): BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Currency = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding : ItemSpinnerBinding

        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            binding = ItemSpinnerBinding.bind(convertView)
        }

        Glide.with(context).load(list[position].imgUrl).into(binding.flagImg)
        binding.txt.text = list[position].id

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding : ItemSpinnerBinding

        if (convertView == null) {
            binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            binding = ItemSpinnerBinding.bind(convertView)
        }

        Glide.with(context).load(list[position].imgUrl).into(binding.flagImg)
        binding.txt.text = list[position].id

        return binding.root
    }
}