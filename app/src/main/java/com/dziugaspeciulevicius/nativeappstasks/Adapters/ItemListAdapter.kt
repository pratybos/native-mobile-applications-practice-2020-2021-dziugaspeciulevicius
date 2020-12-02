package com.dziugaspeciulevicius.nativeappstasks.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dziugaspeciulevicius.nativeappstasks.Models.Item
import com.dziugaspeciulevicius.nativeappstasks.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*

class ItemListAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(ItemDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false), interaction
    )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Item>) {
        submitList(data.toMutableList())
    }

    inner class ItemViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
        }

        fun bind(item: Item) = with(itemView) {
            item_title.text = item.name
            item_price.text = item.price.toString()
            //image with picaso (needs a dependency put into gradle)
            Picasso.get().load(item.imageURL).into(item_image);
            // TODO: Bind the data with View
        }
    }

    interface Interaction {

    }

    private class ItemDC : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean = oldItem.price == newItem.price

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ) = oldItem == newItem
    }
}