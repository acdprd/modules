package com.acdprd.modules.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.acdprd.modules.*
import com.acdprd.modules.adapter.TestAdapter
import com.acdprd.modules.model.ListItem
import com.acdprd.modules.model.ModelF
import com.acdprd.modules.model.ModelS

class MainActivity : AppCompatActivity(){
    private lateinit var rv:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViews()
    }

    private fun setViews() {
        rv = findViewById(R.id.recyclerView)

        val adapter = TestAdapter()

        rv.adapter = adapter

        val data = mutableListOf<ListItem>(
            ModelF("first f"),
            ModelF("second f"),
            ModelS("first s")
        )
        adapter.setItems(data)
    }
}