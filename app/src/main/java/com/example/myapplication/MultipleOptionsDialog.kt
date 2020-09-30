package com.example.myapplication


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_multiple_options.*

/**
 * @Author : YFL  is Creating a porject in Dell
 * @Package : cn.ruiye.worker.view.dialog
 * @Email : yufeilong92@163.com
 * @Time :2020/9/30 17:42
 * @Purpose :通用多选对话框
 */
abstract class MultipleOptionsDialog(var mContext: Context, var mData: MutableList<MultipleOptionAdapter.SelectRlv>?) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics


    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }


    private var mAdapter:MultipleOptionAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_multiple_options)
        initView()
        initListener()
    }

    private fun initListener() {
        btn_dialog_multiple_life.setOnClickListener {
            dismiss()
        }
        btn_dialog_multiple_right.setOnClickListener {
            onSelectMutableListData(mData)
        }
    }


    private fun initView() {
        mAdapter = MultipleOptionAdapter(mContext, mData!!)
        val g = GridLayoutManager(mContext, 1)
        rlv_dialog_multiple_content.layoutManager = g
        rlv_dialog_multiple_content.adapter = mAdapter
        mAdapter?.setRecyclerListener(object : MultipleOptionAdapter.RecyclerItemListener {
            override fun itemClickListener(position: Int) {
                mData!![position].check = !mData!![position].check
                mAdapter?.notifyItemChanged(position)
            }
        })
    }

    abstract fun onSelectMutableListData(mdata: MutableList<MultipleOptionAdapter.SelectRlv>?)

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

    fun refreshData(data: MutableList<MultipleOptionAdapter.SelectRlv>?) {
        mData=data
        mAdapter?.notifyDataSetChanged()

    }
}