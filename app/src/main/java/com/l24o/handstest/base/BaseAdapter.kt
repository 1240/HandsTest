package com.l24o.handstest.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class BaseAdapter : ListAdapter<AdapterItemModel, BaseViewHolder<Any>>(DIFF_CALLBACK) {
    private val typesMap = mutableMapOf<Int, ViewHolderCreator<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        val viewHolderCreator = requireNotNull(typesMap[viewType])
        val itemView =
            LayoutInflater.from(parent.context).inflate(viewHolderCreator.layoutRes, parent, false)
        return viewHolderCreator.create(itemView) as BaseViewHolder<Any>
    }

    override fun getItemViewType(position: Int) = getItem(position).getViewType()

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun submitList(items: List<AdapterItemModel>?) {
        super.submitList(items)
        items?.let {
            typesMap.putAll(items.map { it.getViewType() to it.viewHolderCreator(it.layoutRes) })
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdapterItemModel>() {

            override fun areItemsTheSame(
                oldItem: AdapterItemModel,
                newItem: AdapterItemModel
            ): Boolean {
                return newItem.areItemsTheSame(oldItem)
            }

            override fun areContentsTheSame(
                oldItem: AdapterItemModel,
                newItem: AdapterItemModel
            ): Boolean {
                return newItem.areContentsTheSame(oldItem)
            }
        }
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

abstract class AdapterItemModel(
    val layoutRes: Int,
    val id: Int
) {
    abstract fun viewHolder(view: View): BaseViewHolder<*>

    fun getViewType() = this::class.java.hashCode()

    fun viewHolderCreator(layoutRes: Int): ViewHolderCreator<*> {
        return ViewHolderCreator(layoutRes) { viewHolder(it) }
    }

    fun areItemsTheSame(
        item: AdapterItemModel
    ): Boolean {
        return id == item.id
    }

    fun areContentsTheSame(
        item: AdapterItemModel
    ): Boolean {
        return this == item
    }
}

open class ViewHolderCreator<out T : BaseViewHolder<*>>(
    val layoutRes: Int,
    open val create: (view: View) -> T
)