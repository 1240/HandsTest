package com.l24o.handstest.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.l24o.handstest.R
import com.l24o.handstest.base.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*

class WorldActivity : AppCompatActivity() {

    private val viewModel: WorldViewModel by viewModels()
    private var adapter: BaseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        observe()
    }

    private fun initViews() {
        adapter = BaseAdapter()
        recycler_view.adapter = adapter
        recycler_view.itemAnimator = object : DefaultItemAnimator() {
            override fun onAddFinished(item: RecyclerView.ViewHolder?) {
                super.onAddFinished(item)
                recycler_view.smoothScrollToPosition(adapter?.itemCount?.dec() ?: 0)
            }
        }
        create_btn.setOnClickListener { viewModel.onCreateLifeClick() }
    }

    private fun observe() {
        viewModel.getWorld().observe(this, Observer {
            adapter?.submitList(it)
        })
    }
}
