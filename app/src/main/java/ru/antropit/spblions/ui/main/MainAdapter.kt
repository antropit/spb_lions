package ru.antropit.spblions.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import ru.antropit.spblions.R
import ru.antropit.spblions.api.model.Entity
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private val listener: (Entity) -> Unit): ListAdapter<Entity, MainAdapter.MainHolder>(
    DIFF_CALLBACK), Filterable {

    companion object {
        var fullList: List<Entity>? = null
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Entity>() {
            override fun areItemsTheSame(p0: Entity, p1: Entity): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Entity, p1: Entity): Boolean {
                return p0 == p1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: Entity) {
            view.name.text = item.name
            view.description.text = item.description

            Glide.with(view.context)
                .load(item.photo)
                .placeholder(R.drawable.placeholder)
                .into(view.photo)

            view.setOnClickListener { listener.invoke(item) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filteredList: List<Entity>
                if(charSequence.isEmpty()) {
                    filteredList = fullList!!.toMutableList()
                } else {
                    val keyword = charSequence.toString().toLowerCase(Locale.getDefault()).trim()
                    filteredList = fullList!!.filter { it.name.toLowerCase(Locale.getDefault()).contains(keyword) }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                submitList(filterResults.values as ArrayList<Entity>)
                notifyDataSetChanged()
            }
        }
    }

}