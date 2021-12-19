package uz.pdp.bank1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.bank1.R
import uz.pdp.bank1.databinding.SliderItemBinding
import uz.pdp.nbuapp.room.entity.Currency

class SliderAdapter(var context: Context, var list: List<Currency>) : RecyclerView.Adapter<SliderAdapter.VH>() {

    inner class VH(var binding: SliderItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
//            imageSlide.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    override fun getItemCount(): Int = list.size


}