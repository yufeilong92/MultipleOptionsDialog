package com.example.myapplication


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.MultipleOptionsBuildeDialog.SelectType.*
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
class MultipleOptionsBuildeDialog(
    var mContext: Context
) : AlertDialog(mContext, R.style.mydialog) {
    private lateinit var mData: MutableList<MultipleOptionAdapter.SelectRlv>
    private lateinit var mSelectType: SelectType
    private var mIsGravityButtom: Boolean = false
    private var mIsFilter: Boolean = false
    private var mIsShow: Boolean = true
    private var mSelectIcon: Int = 0
    private var mSelectColor: Int = 0
    private var mNoSelectIcon: Int = 0
    private var mNoSelectColor: Int = 0
    private var mTvGravity: Int = -1

    private lateinit var onMultipeDataListener: (data: MutableList<MultipleOptionAdapter.SelectRlv>?) -> Unit
    private lateinit var onSingleDataListener: (data: MultipleOptionAdapter.SelectRlv?) -> Unit

    private var metrics: DisplayMetrics = context.resources.displayMetrics

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    companion object {
        fun builde(mContext: Context): MultipleBuidle {
            return MultipleBuidle(mContext)

        }
    }

    class MultipleBuidle(cm: Context) {
        var mMultipleOptionsDialog = MultipleOptionsBuildeDialog(mContext = cm)
        fun setData(@Nullable data: MutableList<MultipleOptionAdapter.SelectRlv>): MultipleBuidle {
            mMultipleOptionsDialog.mData = data
            return this
        }

        fun setSelectType(select: SelectType): MultipleBuidle {
            mMultipleOptionsDialog.mSelectType = select
            return this
        }

        fun setGravityButtom(isGravity: Boolean): MultipleBuidle {
            mMultipleOptionsDialog.mIsGravityButtom = isGravity
            return this
        }

        fun setIsFilter(isFilter: Boolean): MultipleBuidle {
            mMultipleOptionsDialog.mIsFilter = isFilter
            return this
        }

        fun setShowIcon(show: Boolean): MultipleBuidle {
            mMultipleOptionsDialog.mIsShow = show
            return this
        }

        fun setSelectIcon(@DrawableRes select: Int, @DrawableRes noSelect: Int): MultipleBuidle {
            mMultipleOptionsDialog.mSelectIcon = select
            mMultipleOptionsDialog.mNoSelectIcon = noSelect
            return this
        }

        fun setTvGravity(gravity: Int):MultipleBuidle{
            mMultipleOptionsDialog.mTvGravity=gravity
            return this
        }
        fun setSelectColor(@ColorInt select: Int, @ColorInt noSelect: Int): MultipleBuidle {
            mMultipleOptionsDialog.mSelectColor = select
            mMultipleOptionsDialog.mNoSelectColor = noSelect
            return this
        }

        fun setSingleDataListener(data: (data: MultipleOptionAdapter.SelectRlv?) -> Unit): MultipleBuidle {
            mMultipleOptionsDialog.onSingleDataListener = data
            return this
        }

        fun setMultipleDataListener(data: (data: MutableList<MultipleOptionAdapter.SelectRlv>?) -> Unit): MultipleBuidle {
            mMultipleOptionsDialog.onMultipeDataListener = data
            return this
        }

        fun show() {
            mMultipleOptionsDialog.show()
        }

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
                MULTIPLE -> {
                    if (mData.isNullOrEmpty()) {
                        if (::onMultipeDataListener.isInitialized) {
                            onMultipeDataListener.invoke(null)
                        }
                        return@setOnClickListener
                    }
                    if (mIsFilter) {
                        val filter = mData?.filter { it.check } as MutableList
                        if (::onMultipeDataListener.isInitialized) {
                            onMultipeDataListener.invoke(filter)
                        }
                        return@setOnClickListener
                    }
                    if (::onMultipeDataListener.isInitialized) {
                        onMultipeDataListener.invoke(mData)
                    }
                }
                else -> {
                    if (mData.isNullOrEmpty()) {
                        if (::onSingleDataListener.isInitialized) {
                            onSingleDataListener.invoke(null)
                        }
                        return@setOnClickListener
                    }
                    val list = mData.filter { it.check }
                    if (list.isNullOrEmpty()) {
                        if (::onSingleDataListener.isInitialized) {
                            onSingleDataListener.invoke(null)
                        }
                        return@setOnClickListener
                    }
                    if (::onSingleDataListener.isInitialized) {
                        onSingleDataListener.invoke(list[0])
                    }

                }
            }
        }
    }


    private fun initView() {
        mAdapter = MultipleOptionAdapter(mContext, mData)
        val g = GridLayoutManager(mContext, 1)
        rlv_dialog_multiple_content.layoutManager = g
        rlv_dialog_multiple_content.adapter = mAdapter
        if (mSelectColor != 0 && mNoSelectColor != 0)
            mAdapter?.setSelectColor(mNoSelectColor, mSelectColor)
        if (mNoSelectIcon != 0 && mSelectIcon != 0)
            mAdapter?.setSelectIcom(mNoSelectIcon, mSelectIcon)
        mAdapter?.setShowIcon(mIsShow)
        if (mTvGravity!=-1)
            mAdapter?.setTextGravity(mTvGravity)
        mAdapter?.setRecyclerListener(object : MultipleOptionAdapter.RecyclerItemListener {
            override fun itemClickListener(position: Int) {
                when (mSelectType) {
                    SINGLE -> {
                        clearSelect()
                        mData[position].check = !mData[position].check
                        mAdapter?.notifyDataSetChanged()
                    }
                    MULTIPLE -> {
                        mData[position].check = !mData[position].check
                        mAdapter?.notifyItemChanged(position)
                    }
                    else -> {
                        clearSelect()
                        mData[position].check = !mData[position].check
                        mAdapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }


    private fun clearSelect() {
        if (mData.isNullOrEmpty()) {
            return
        }
        mData.let {
            for (child in it) {
                child.check = false
            }
        }
    }


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


    enum class SelectType {
        SINGLE, MULTIPLE
    }
}