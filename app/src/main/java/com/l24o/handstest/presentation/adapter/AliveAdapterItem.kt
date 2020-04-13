package com.l24o.handstest.presentation.adapter

import android.view.View
import com.l24o.handstest.R
import com.l24o.handstest.base.AdapterItemModel
import com.l24o.handstest.base.BaseViewHolder

class AliveAdapterItem(id: Int) : AdapterItemModel(R.layout.item_alive, id) {
    override fun viewHolder(view: View) = AliveViewHolder(view)
}

class AliveViewHolder(itemView: View) : BaseViewHolder<AliveAdapterItem>(itemView) {
    override fun bind(item: AliveAdapterItem) {
        // nothing in out case
    }
}