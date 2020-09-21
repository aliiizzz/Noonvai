package ir.aliiz.noonvai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.basket_item_full.view.*

class BasketsAdapter : RecyclerView.Adapter<BasketsAdapter.BasketsViewHolder>() {
    var items: List<BasketWithItems> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketsViewHolder =
        BasketsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.basket_item_full, parent, false
            )
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BasketsViewHolder, position: Int) {
        with(holder.itemView) {
            val item = items[position]
            textBasketFullTitle.text = item.items.map { "${it.count} ${it.title}" }.joinToString(",")
            textBasketFullPrice.text = item.items.map { it.count * it.price }.sum().toString()
        }
    }

    class BasketsViewHolder(view: View): RecyclerView.ViewHolder(view)
}