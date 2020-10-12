package com.example.myapplication


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_multiple_options.*

/**
 * @Author : YFL  is Creating a porject in Dell
 * @Package : cn.ruiye.worker.view.dialog
 * @Email : yufeilong92@163.com
 * @Time :2020/9/30 17:42
 * @Purpose :通用多选对话框
 */

/***
 * @param mContext  上下文
 * @param mData  通用工具类
 * @param mSelectType  选择类型 Single 单选 multiple 多选
 * @param mIsGravityButtom  视图位置 是否在底部 false 居中
 * @param mIsFilter 是否筛选多选，false 原数据 true返回选中数据
 */
abstract class MultipleOptionsDialog(
    var mContext: Context,
    var mData: MutableList<SelectRlv>?,
    var mSelectType: SelectType?,
    var mIsGravityButtom: Boolean,
    var mIsFilter: Boolean
) : AlertDialog(mContext, R.style.mydialog) {


    private var metrics: DisplayMetrics = context.resources.displayMetrics

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    //适配器
    private var mAdapter: MultipleOptionAdapter? = null


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
            when (mSelectType) {
                SelectType.MULTIPLE -> {
                    if (mData.isNullOrEmpty()) {
                        onSelectMutableListData(null)
                        return@setOnClickListener
                    }
                    if (mIsFilter) {
                        val filter = mData?.filter { it.check } as MutableList
                        onSelectMutableListData(filter)
                        return@setOnClickListener
                    }
                    onSelectMutableListData(mData)
                }
                else -> {
                    if (mData.isNullOrEmpty()) {
                        onSelectSingleData(null)
                        return@setOnClickListener
                    }
                    val list = mData?.filter { it.check }
                    if (list.isNullOrEmpty()) {
                        onSelectSingleData(null)
                        return@setOnClickListener
                    }
                    onSelectSingleData(list[0])

                }
            }
        }
    }


    private fun initView() {
        mAdapter = MultipleOptionAdapter(mContext, mData!!)
        val g = GridLayoutManager(mContext, 1)
        rlv_dialog_multiple_content.layoutManager = g
        rlv_dialog_multiple_content.adapter = mAdapter
        mAdapter?.setRecyclerListener(object : MultipleOptionAdapter.RecyclerItemListener {
            override fun itemClickListener(position: Int) {
                when (mSelectType) {
                    SelectType.SINGLE -> {
                        clearSelect()
                        mData!![position].check = !mData!![position].check
                        mAdapter?.notifyDataSetChanged()
                    }
                    SelectType.MULTIPLE -> {
                        mData!![position].check = !mData!![position].check
                        mAdapter?.notifyItemChanged(position)
                    }
                    else -> {
                        clearSelect()
                        mData!![position].check = !mData!![position].check
                        mAdapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    /***
     * @param noSelect 不选择图片id
     * @param select 选中的图片id
     */
    fun setSelectIcon(@DrawableRes noSelect: Int, @DrawableRes select: Int) {
       mAdapter?.let {
           it.setSelectIcom(noSelect, select)
       }
    }

    fun showIcon(show: Boolean) {
        mAdapter?.let {
            it.setShowIcon(show)
        }
    }

    fun setSelectColor(@ColorInt noSelect: Int, @ColorInt select: Int) {
        mAdapter?.let {
            it.setSelectColor(noSelect, select)
        }

    }
    fun onNotiftyData(){
        mAdapter?.notifyDataSetChanged()
    }

    private fun clearSelect() {
        if (mData.isNullOrEmpty()) {
            return
        }
        mData?.let {
            for (child in it) {
                child.check = false
            }
        }
    }

    abstract fun onSelectMutableListData(mdata: MutableList<SelectRlv>?)
    abstract fun onSelectSingleData(mdata: SelectRlv?)

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        if (mIsGravityButtom) {
            window!!.setGravity(Gravity.BOTTOM)
        } else {
            window?.setGravity(Gravity.CENTER)
        }
    }

    fun refreshData(data: MutableList<SelectRlv>?) {
        mData = data
        mAdapter?.notifyDataSetChanged()
    }

    enum class SelectType {
        SINGLE, MULTIPLE
    }
}