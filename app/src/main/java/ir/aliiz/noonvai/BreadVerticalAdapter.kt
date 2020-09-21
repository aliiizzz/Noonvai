package ir.aliiz.noonvai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bread_item_vertical.view.*

class BreadVerticalAdapter(val edit: (Bread) -> Unit, val delete: (Bread) -> Unit) : RecyclerView.Adapter<BreadVerticalAdapter.BreadViewHolder>() {
    var items: List<Bread> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder =
        BreadViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.bread_item_vertical, parent, false
        )).apply {
            itemView.buttonBreadFullEdit.setOnClickListener {
                edit(items[adapterPosition])
            }

            itemView.buttonBreadFullDelete.setOnClickListener {
                delete(items[adapterPosition])
            }
        }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView) {
            textBreadFullTitle.text = item.title
        }
    }

    class BreadViewHolder(view: View): RecyclerView.ViewHolder(view)
}