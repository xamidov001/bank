package uz.pdp.bank1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.bank1.databinding.ItemBinding
import uz.pdp.nbuapp.room.entity.Currency

class RecAdapter(var context: Context, var listener: OnMyClickListener): ListAdapter<Currency, RecAdapter.VH>(MyDiffUtil()) {

    interface OnMyClickListener {

        fun onClick(position: Int)
    }

    class MyDiffUtil: DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

    }

    inner class VH(var binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {

            txt.text = item.id
            val st = "https://nbu.uz/local/templates/nbu/images/flags/${item.id}.png"
            Glide.with(context).load(st).into(flagImg)
            if (item.nbu_buy_price.isEmpty()) {
                sotibOlishUzs.text = item.cb_price
                sotishUzs.text = item.cb_price
            } else {
                sotibOlishUzs.text = item.nbu_buy_price
                sotishUzs.text = item.nbu_cell_price
            }

            calculator.setOnClickListener {
                listener.onClick(position)
            }

        }
    }
}