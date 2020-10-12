package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backpacker.yflLibrary.view.dialog.MultipleOptionsBuildeDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mArray: MutableList<SelectRlv>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clearData()
        val dialog = object :MultipleOptionsDialog(this, mArray!!, SelectType.MULTIPLE, true, true) {
                override fun onSelectMutableListData(mdata: MutableList<SelectRlv>?) {
                    Toast.makeText(this@MainActivity, "${mdata.toString()}", Toast.LENGTH_SHORT)
                        .show();
                }

                override fun onSelectSingleData(mdata: SelectRlv?) {
                    Toast.makeText(this@MainActivity, "${mdata.toString()}", Toast.LENGTH_SHORT)
                        .show();
                }
            }
        tv_hello.setOnClickListener {
            val data = mutableListOf<SelectRlv>()
            for (index in 0..50) {
                val vo = SelectRlv("标题$index", "$index", false)
                data.add(vo)
            }
            clearData()
            addData(data)
            MultipleOptionsBuildeDialog.builde(this@MainActivity)
                .setData(mArray!!)
                .setGravityButtom(true)
                .setIsFilter(false)
                .setSelectColor(R.mipmap.ic_gm_select_s,R.mipmap.ic_gm_select_n)
                .setSelectColor(Color.RED,Color.GRAY)
                .setSelectType(MultipleOptionsBuildeDialog.SelectType.SINGLE)
                .setShowIcon(false)
                .setTvGravity(Gravity.CENTER)
                .setSingleDataListener {
                    Toast.makeText(this@MainActivity, "${it.toString()}", Toast.LENGTH_SHORT)
                        .show();
                }
                .setMultipleDataListener {
                    Toast.makeText(this@MainActivity, "${it.toString()}", Toast.LENGTH_SHORT)
                        .show();
                }
                .show()
//            dialog.show()
//            dialog.refreshData(mArray!!)
//            dialog.setSelectIcon(R.mipmap.ic_gm_select_s, R.mipmap.ic_gm_select_n)
//            dialog.showIcon(true)
//            dialog.setSelectColor(Color.GRAY, Color.GREEN)
//            dialog.onNotiftyData()
        }


    }

    private fun clearData() {
        if (mArray == null) {
            mArray = ArrayList()
        } else {
            mArray!!.clear()
        }
    }

    /***
     * @param list 数据
     * @return
     */
    private fun addData(list: MutableList<SelectRlv>?) {
        if (list == null || list.isEmpty()) {
            return
        }
        if (mArray == null) {
            clearData()
        }
        mArray!!.addAll(list)
    }

}