package ir.aliiz.noonvai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bread_item.view.*

class BreadAdapter(
    val plusClicked: (Bread) -> Unit,
    val minusClicked: (Bread) -> Unit
) : RecyclerView.Adapter<BreadAdapter.BreadViewHolder>() {
    var items: List<Bread> = listOf()
    var basket: List<BasketBread> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder =
        BreadViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.bread_item, parent, false
        )).apply {
            itemView.buttonBreadItemPlus.setOnClickListener {
                plusClicked(items[adapterPosition])
            }

            itemView.buttonBreadItemMinus.setOnClickListener {
                minusClicked(items[adapterPosition])
            }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.textBreadItemTitle.text = item.title

        val count = basket.firstOrNull { it.id == item.id }?.count ?: 0
        holder.itemView.textBreadItemCount.text =
            count.toString()
        holder.itemView.buttonBreadItemMinus.isEnabled = count > 0
    }

    class BreadViewHolder(view: View): RecyclerView.ViewHolder(view)
}