package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mArray: MutableList<MultipleOptionAdapter.SelectRlv>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clearData()
        val dialog = object : MultipleOptionsDialog(this, mArray!!,SelectType.MULTIPLE) {
            override fun onSelectMutableListData(mdata: MutableList<MultipleOptionAdapter.SelectRlv>?) {
             Toast.makeText(this@MainActivity, "${mdata.toString()}", Toast.LENGTH_SHORT).show();
            }

            override fun onSelectSingleData(mdata: MultipleOptionAdapter.SelectRlv?) {
                Toast.makeText(this@MainActivity, "${mdata.toString()}", Toast.LENGTH_SHORT).show();
            }
        }
        tv_hello.setOnClickListener {
            val data = mutableListOf<MultipleOptionAdapter.SelectRlv>()
            for (index in 0..50) {
                val vo = MultipleOptionAdapter.SelectRlv("标题$index", "$index", false)
                data.add(vo)
            }
            clearData()
            addData(data)
            dialog.show()
            dialog.refreshData(mArray!!)
        }
    }

    private fun clearData() {
        if (mArray == null) {
            mArray = ArrayList()
        } else {
            mArray!!.clear()
        }
    }

    private fun addData(list: MutableList<MultipleOptionAdapter.SelectRlv>?) {
        if (list == null || list.isEmpty()) {
            return
        }
        if (mArray == null) {
            clearData()
        }
        mArray!!.addAll(list)
    }

}