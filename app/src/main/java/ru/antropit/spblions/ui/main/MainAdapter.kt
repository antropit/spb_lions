package ru.antropit.spblions.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import ru.antropit.spblions.R
import ru.antropit.spblions.api.model.Entity
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(private val listener: (Entity) -> Unit): RecyclerView.Adapter<MainAdapter.MainViewHolder>(), Filterable {
    companion object {
        var dataList: MutableList<Entity> = ArrayList()
        var fullList: List<Entity>? = null
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class MainViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
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

    fun updateList(list: List<Entity>) {
        val diffUtilCallback = DiffUtilCallback(dataList, list)
        val diffResult =  DiffUtil.calculateDiff(diffUtilCallback)

        dataList.clear()
        dataList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun submitList(list: ArrayList<Entity>) {
        fullList = ArrayList(list)
        updateList(list)
    }

    inner class DiffUtilCallback(private val oldList: List<Entity>, private val newList: List<Entity>): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemPosition == newItemPosition
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filteredList: List<Entity>
                if(charSequence.isEmpty()) {
                    filteredList = fullList!!.toMutableList()
                } else {
                    val keywords = charSequence.toString().toLowerCase(Locale.getDefault()).trim().split(" ")
                    filteredList = fullList!!.filter (
                        fun(it: Entity) : Boolean {
                            for (key in keywords)
                                if (!it.name.toLowerCase(Locale.getDefault()).contains(key)) return false
                            return true
                        }).toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                this@MainAdapter.updateList(filterResults.values as ArrayList<Entity>)
            }
        }
    }

}