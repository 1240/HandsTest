package com.l24o.handstest.presentation.adapter

import android.view.View
import com.l24o.handstest.R
import com.l24o.handstest.base.AdapterItemModel
import com.l24o.handstest.base.BaseViewHolder

class ResultAdapterItem(id: Int) : AdapterItemModel(R.layout.item_result, id) {
    override fun viewHolder(view: View) = ResultViewHolder(view)
}

class ResultViewHolder(itemView: View) : BaseViewHolder<ResultAdapterItem>(itemView) {
    override fun bind(item: ResultAdapterItem) {
        // nothing in out case
    }
}