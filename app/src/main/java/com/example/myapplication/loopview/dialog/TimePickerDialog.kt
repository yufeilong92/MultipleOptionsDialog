package com.example.myapplication.loopview.dialog

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.loopview.loopviewInface.LoopView
import com.example.myapplication.loopview.loopviewInface.OnItemScrollListener
import kotlinx.android.synthetic.main.dialog_time_picker.*
import java.lang.NumberFormatException
import java.util.ArrayList

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication.loopview
 * @Email : yufeilong92@163.com
 * @Time :2020/10/12 16:09
 * @Purpose :对话框
 */
public class TimePickerDialog(var mContext: Context) : AlertDialog(mContext, R.style.mydialog) {
    private var metrics: DisplayMetrics = context.resources.displayMetrics

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    public lateinit var onSelectTImePickerNoHourMin: (year: String, month: String, day: String) -> Unit
    public lateinit var onSelectTImePickerHourMin: (year: String, month: String, day: String, hour: String, min: String) -> Unit

    private var mYearList: MutableList<String>? = null
    private var mMonthList: MutableList<String>? = null
    private var mDayList: MutableList<String>? = null
    private var mHourList: MutableList<String>? = null
    private var mMinuteList: MutableList<String>? = null

    //是否显示时分
    private var isShowHourMin = false

    //中间文字颜色
    private var mContentColor: Int = 0

    //其他颜色
    private var mOutContentColor: Int = 0

    //线颜色
    private var mLineColor: Int = 0

    //是否显示线
    private var isShowLine: Boolean = true

    //是否循环
    private var isLoop: Boolean = true

    //开始时间
    private var mStartYear: Int = 0

    //结束时间
    private var mEndYear: Int = 0

    //百分比
    private var mPercentage: Double = 0.40

    //是否联动
    private var mIsLinkAge: Boolean = false


    private var mSelectYear: Int = 0
    private var mSelectMonth: Int = 0
    private var mSelectDay: Int = 0
    private var mSelectHour: Int = 0
    private var mSelectMin: Int = 0

    companion object {
        fun buidler(mContext: Context): Builder {
            return Builder(mContext)
        }
    }

    class Builder(var mContext: Context) {
        val timePicker = TimePickerDialog(mContext)

        fun setStartTime(year: Int): Builder {
            timePicker.mStartYear = year
            return this
        }

        fun setEndTime(year: Int): Builder {
            timePicker.mEndYear = year
            return this
        }

        fun setSelectTime(year: Int, month: Int, day: Int, hour: Int, min: Int): Builder {
            timePicker.mSelectYear = year
            timePicker.mSelectMonth = month
            timePicker.mSelectDay = day
            timePicker.mSelectHour = hour
            timePicker.mSelectMin = min
            return this
        }

        fun setSelectTime(year: Int, month: Int, day: Int): Builder {
            timePicker.mSelectYear = year
            timePicker.mSelectMonth = month
            timePicker.mSelectDay = day
            timePicker.mSelectHour = 0
            timePicker.mSelectMin = 0
            timePicker.isShowHourMin = false
            return this
        }


        fun setIsShowHourMin(show: Boolean): Builder {
            timePicker.isShowHourMin = show
            return this
        }

        fun setSelectContentColor(@ColorInt color: Int): Builder {
            timePicker.mContentColor = color
            return this
        }

        fun setSelectOutColor(@ColorInt color: Int): Builder {
            timePicker.mOutContentColor = color
            return this
        }

        fun setLineColor(@ColorInt color: Int): Builder {
            timePicker.mLineColor = color
            return this
        }

        fun showLine(show: Boolean): Builder {
            timePicker.isShowLine = show
            return this
        }

        fun setViewPhoneHeightPercentage(mPercentage: Double): Builder {
            timePicker.mPercentage = mPercentage
            return this
        }

        fun setIsLoop(isLoop: Boolean): Builder {
            timePicker.isLoop = isLoop
            return this
        }

        fun isLinkage(isLinkage: Boolean): Builder {
            timePicker.mIsLinkAge = isLinkage
            return this
        }


        fun show() {
            timePicker.show()
        }

    }

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
        setContentColor()
        setOutContentColor()
        setLineColor()
        setShowline()
        setIsLoop()
    }

    private fun setContentColor() {
        if (mContentColor == 0) return
        loop_year.setCenterTextColor(mContentColor)
        loop_month.setCenterTextColor(mContentColor)
        loop_day.setCenterTextColor(mContentColor)
        loop_hour.setCenterTextColor(mContentColor)
        loop_min.setCenterTextColor(mContentColor)
    }

    private fun setOutContentColor() {
        if (mOutContentColor == 0) return
        loop_year.setOuterTextColor(mOutContentColor)
        loop_month.setOuterTextColor(mOutContentColor)
        loop_day.setOuterTextColor(mOutContentColor)
        loop_hour.setOuterTextColor(mOutContentColor)
        loop_min.setOuterTextColor(mOutContentColor)
    }

    private fun setShowline() {
        loop_year.setShowDividerLine(isShowLine)
        loop_month.setShowDividerLine(isShowLine)
        loop_day.setShowDividerLine(isShowLine)
        loop_hour.setShowDividerLine(isShowLine)
        loop_min.setShowDividerLine(isShowLine)
    }

    private fun setLineColor() {
        if (mLineColor == 0) return
        loop_year.setDividerColor(mContentColor)
        loop_month.setDividerColor(mContentColor)
        loop_day.setDividerColor(mContentColor)
        loop_hour.setDividerColor(mContentColor)
        loop_min.setDividerColor(mContentColor)
    }

    private fun setIsLoop() {
        loop_year.setLoop(isLoop)
        loop_month.setLoop(isLoop)
        loop_day.setLoop(isLoop)
        loop_hour.setLoop(isLoop)
        loop_min.setLoop(isLoop)
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
        val selectedItem = loop_year.selectedItem
        val selectedItem1 = loop_month.selectedItem
        val selectedItem2 = loop_day.selectedItem
        if (isShowHourMin) {
            if (mYearList.isNullOrEmpty() || mMonthList.isNullOrEmpty() || mDayList.isNullOrEmpty() || mHourList.isNullOrEmpty() || mMinuteList.isNullOrEmpty())
                return
            val selectedItem3 = loop_hour.selectedItem
            val selectedItem4 = loop_min.selectedItem
            if (::onSelectTImePickerHourMin.isInitialized) {

                onSelectTImePickerHourMin(
                    mYearList!![selectedItem],
                    mMonthList!![selectedItem1],
                    mDayList!![selectedItem2],
                    mHourList!![selectedItem3],
                    mMinuteList!![selectedItem4]
                )
                dismiss()
            }

        } else {
            if (mYearList.isNullOrEmpty() || mMonthList.isNullOrEmpty() || mDayList.isNullOrEmpty())
                return
            if (::onSelectTImePickerNoHourMin.isInitialized) {
                onSelectTImePickerNoHourMin(
                    mYearList!![selectedItem],
                    mMonthList!![selectedItem1],
                    mDayList!![selectedItem2]
                )
                dismiss()
            }
        }


    }


    private fun initViewModel() {

    }

    private fun gmSetViewData(type:Int,loopView:LoopView,start:Int,end:Int){
        when(type){
            0->{//年

            }
            1->{//月

            }
            2->{//日

            }
            3->{//时

            }
            4->{//分

            }
        }

    }

    private fun setYear() {
        if (mYearList.isNullOrEmpty())
            mYearList = ArrayList<String>()
        else
            mYearList?.clear()
        if (mStartYear > mEndYear) {
            throw NumberFormatException("开始时间$mStartYear 大于 结束$mEndYear")
        }
        for (i in mStartYear..mEndYear) {
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
                }else{


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
            tv_dialog_time_picker_time.text =
                "${mYearList!![selectedItem]}-${mMonthList!![selectedItem1]}-${mDayList!![selectedItem2]}  ${mHourList!![selectedItem3]}:${mMinuteList!![selectedItem4]}"

        } else {
            if (mYearList.isNullOrEmpty() || mMonthList.isNullOrEmpty() || mDayList.isNullOrEmpty())
                return
            tv_dialog_time_picker_time.text =
                "${mYearList!![selectedItem]}-${mMonthList!![selectedItem1]}-${mDayList!![selectedItem2]}"
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
        layoutParams.height = (height * mPercentage).toInt()
        rootviewtimepicek.layoutParams = layoutParams
    }


}