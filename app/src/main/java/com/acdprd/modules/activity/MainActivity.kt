package com.acdprd.modules.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.acdprd.modules.R
import com.acdprd.modules.adapter.TestAdapter
import com.acdprd.modules.model.ModelF
import com.acdprd.modules.model.ModelS
import com.acdprd.modules.model.ToolbarButton
import com.acdprd.modules.model.action.Action
import com.acdprd.modules.model.action.ContentActions
import com.acdprd.modules.view.toolbar.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setViews()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setLeftButton(ToolbarButton.BACK){
            toast("BACK")
        }
    }

    private fun setViews() {
        rv = findViewById(R.id.recyclerView)

        val adapter = TestAdapter()

        rv.adapter = adapter

        val data = mutableListOf(
            ModelF("first f"),
            ModelF("second f"),
            ModelS("first s")
        )
        adapter.setItems(data)
    }

    private fun toast(text:String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}