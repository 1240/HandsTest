package com.l24o.handstest.presentation.adapter

import android.view.View
import com.l24o.handstest.R
import com.l24o.handstest.base.AdapterItemModel
import com.l24o.handstest.base.BaseViewHolder

class DeadAdapterItem(id: Int) : AdapterItemModel(R.layout.item_dead, id) {
    override fun viewHolder(view: View) = DeadViewHolder(view)
}

class DeadViewHolder(itemView: View) : BaseViewHolder<DeadAdapterItem>(itemView) {
    override fun bind(item: DeadAdapterItem) {
        // nothing in out case
    }
}