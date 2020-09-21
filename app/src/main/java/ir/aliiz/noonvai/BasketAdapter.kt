package ir.aliiz.noonvai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.basket_item.view.*

class BasketAdapter(
    private val plus: (BasketBread) -> Unit,
    private val minus: (BasketBread) -> Unit
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    var items: List<BasketBread> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.basket_item, parent, false
        )).apply {
            itemView.buttonBasketItemMinus.setOnClickListener {
                minus(items[adapterPosition])
            }
            itemView.buttonBasketItemPlus.setOnClickListener {
                plus(items[adapterPosition])
            }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {
            textBasketItemTitle.text = item.title
            textBasketItemCount.text = item.count.toString()
        }
    }

    class BasketViewHolder(view: View): RecyclerView.ViewHolder(view)
}