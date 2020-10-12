package com.example.myapplication.loopview.dialog

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.loopview.loopviewInface.LoopView
import com.example.myapplication.loopview.loopviewInface.OnItemScrollListener
import kotlinx.android.synthetic.main.dialog_multiple_options.*
import kotlinx.android.synthetic.main.dialog_time_picker.*
import java.util.ArrayList

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication.loopview
 * @Email : yufeilong92@163.com
 * @Time :2020/10/12 16:09
 * @Purpose :对话框
 */
class TimePickerDialog(var mContext: Context) : AlertDialog(mContext, R.style.mydialog) {
    private var metrics: DisplayMetrics = context.resources.displayMetrics

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    private var mYearList: MutableList<String>? = null
    private var mMonthList: MutableList<String>? = null
    private var mDayList: MutableList<String>? = null
    private var mHourList: MutableList<String>? = null
    private var mMinuteList: MutableList<String>? = null

    //是否显示时分
    private var isShowHourMin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_time_picker)
        setSizeMode()
        initEvent()
        initListener()
        initViewModel()
    }

    private fun initEvent() {
        tv_dialog_time_picker_time.text = "请选择"
        setYear()
        setMonth()
        setDay()
        setHOur()
        setMIn()
    }

    private fun initListener() {
        tv_dialog_time_picker_cancle.setOnClickListener {
            dismiss()
        }
        tv_dialog_time_picker_sure.setOnClickListener {
            sure()
        }
    }

    private fun sure() {


    }

    private fun initViewModel() {

    }

    private fun setYear() {
        if (mYearList.isNullOrEmpty())
            mYearList = ArrayList<String>()
        else
            mYearList?.clear()
        for (i in 1992..2020) {
            mYearList?.add("$i")
        }
        loop_year.setItems(mYearList)
        loop_year.setInitPosition(0)
        loop_year.setOnItemScrollListener(object : OnItemScrollListener {
            override fun onItemScrollStateChanged(
                loopView: LoopView?,
                currentPassItem: Int,
                oldScrollState: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
                if (scrollState == LoopView.SCROLL_STATE_IDLE) {
                    bindViewData()
                }
            }

            override fun onItemScrolling(
                loopView: LoopView?,
                currentPassItem: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
            }

        })
    }

    private fun bindViewData() {
        val selectedItem = loop_year.selectedItem
        val selectedItem1 = loop_month.selectedItem
        val selectedItem2 = loop_day.selectedItem
        if (isShowHourMin) {
            if (mYearList.isNullOrEmpty() || mMonthList.isNullOrEmpty() || mDayList.isNullOrEmpty() || mHourList.isNullOrEmpty() || mMinuteList.isNullOrEmpty())
                return
            val selectedItem3 = loop_hour.selectedItem
            val selectedItem4 = loop_min.selectedItem
            tv_dialog_time_picker_time.text =  "${mYearList!![selectedItem]}-${mMonthList!![selectedItem1]}-${mDayList!![selectedItem2]}  ${mHourList!![selectedItem3]}:${mMinuteList!![selectedItem4]}"

        } else {
            if (mYearList.isNullOrEmpty() || mMonthList.isNullOrEmpty() || mDayList.isNullOrEmpty())
                return
            tv_dialog_time_picker_time.text =  "${mYearList!![selectedItem]}-${mMonthList!![selectedItem1]}-${mDayList!![selectedItem2]}"


        }
    }


    private fun setMonth() {
        if (mMonthList.isNullOrEmpty()) {
            mMonthList = ArrayList<String>()
        } else
            mMonthList?.clear()
        for (i in 1..12) {
            mMonthList?.add("$i")
        }
        loop_month.setItems(mMonthList)
        loop_month.setInitPosition(0)
        loop_month.setOnItemScrollListener(object : OnItemScrollListener {
            override fun onItemScrollStateChanged(
                loopView: LoopView?,
                currentPassItem: Int,
                oldScrollState: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
                if (scrollState == LoopView.SCROLL_STATE_IDLE) {
//                    bindViewData()
                }
            }

            override fun onItemScrolling(
                loopView: LoopView?,
                currentPassItem: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
            }

        })
    }

    private fun setDay() {
        if (mDayList.isNullOrEmpty()) {
            mDayList = ArrayList<String>()
        } else
            mDayList?.clear()
        for (i in 1..31) {
            mDayList?.add("$i")
        }
        loop_day.setItems(mDayList)
        loop_day.setInitPosition(0)
        loop_day.setOnItemScrollListener(object : OnItemScrollListener {
            override fun onItemScrollStateChanged(
                loopView: LoopView?,
                currentPassItem: Int,
                oldScrollState: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
                if (scrollState == LoopView.SCROLL_STATE_IDLE) {
//                    bindViewData()
                }
            }

            override fun onItemScrolling(
                loopView: LoopView?,
                currentPassItem: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
            }

        })
    }

    private fun setHOur() {
        if (mHourList.isNullOrEmpty()) {

            mHourList = ArrayList<String>()
        } else {
            mHourList?.clear()
        }
        for (i in 0..23) {
            mHourList?.add("$i")
        }
        loop_hour.setItems(mHourList)
        loop_hour.setInitPosition(0)
        loop_hour.setOnItemScrollListener(object : OnItemScrollListener {
            override fun onItemScrollStateChanged(
                loopView: LoopView?,
                currentPassItem: Int,
                oldScrollState: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
                if (scrollState == LoopView.SCROLL_STATE_IDLE) {
//                    bindViewData()
                }
            }

            override fun onItemScrolling(
                loopView: LoopView?,
                currentPassItem: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
            }

        })
    }

    private fun setMIn() {
        if (mMinuteList.isNullOrEmpty()) {
            mMinuteList = ArrayList<String>()
        } else
            mMinuteList?.clear()
        for (i in 0..59) {
            mMinuteList?.add("$i")
        }
        loop_min.setItems(mMinuteList)
        loop_min.setInitPosition(0)
        loop_min.setOnItemScrollListener(object : OnItemScrollListener {
            override fun onItemScrollStateChanged(
                loopView: LoopView?,
                currentPassItem: Int,
                oldScrollState: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
                if (scrollState == LoopView.SCROLL_STATE_IDLE) {
//                    bindViewData()
                }
            }

            override fun onItemScrolling(
                loopView: LoopView?,
                currentPassItem: Int,
                scrollState: Int,
                totalScrollY: Int
            ) {
            }

        })
    }

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.BOTTOM)
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay
        var height: Int
        if (Build.VERSION.SDK_INT < 17) {
            height = defaultDisplay.height
        } else {
            val size = Point()
            defaultDisplay.getRealSize(size)
            height = size.y
        }

        val layoutParams = rootviewtimepicek.layoutParams
        layoutParams.height = (height * 0.4).toInt()
        rootviewtimepicek.layoutParams = layoutParams
    }
}