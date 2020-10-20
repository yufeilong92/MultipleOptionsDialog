package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.example.myapplication.addresspicker.AddressPicker
import com.example.myapplication.addresspicker.ConvertUtils
import com.example.myapplication.addresspicker.LogUtils
import com.example.myapplication.addresspicker.OnLinkageListener
import com.example.myapplication.addresspicker.entity.City
import com.example.myapplication.addresspicker.entity.County
import com.example.myapplication.addresspicker.entity.Province
import com.example.myapplication.loopview.dialog.DateTimePickerDialog
import com.example.myapplication.loopview.dialog.RadioPickerDialog
import com.example.myapplication.loopview.dialog.TimePickerBuidlerDialog
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initEvent()
        initListener()
        initViewModel()
    }

    private fun initEvent() {

    }

    private fun initListener() {
        btn_show.setOnClickListener {
            val mCalendar = Calendar.getInstance()
            val year = mCalendar.get(Calendar.YEAR)
            val month = mCalendar.get(Calendar.MONTH) + 1
            val day = mCalendar.get(Calendar.DAY_OF_MONTH)
            val hour = mCalendar.get(Calendar.HOUR_OF_DAY)
            val min = mCalendar.get(Calendar.MINUTE)
            DateTimePickerDialog.buidler(this)
                .setStartTime(2016)
                .setEndTime(2200)
                .setIsLoop(false)
                .showLine(true)
                .setLineColor(Color.GREEN)
                .setIsShowHourMin(true)
                .setLablerColor(Color.GREEN)
                .setLinkage(true)
                .setOnTimePickerListener { y, m, d, h, minun ->
                    Toast.makeText(this, "$y,$m,$d,$h,$minun", Toast.LENGTH_SHORT).show();
                }
                .setSelectTime(year, month, day, hour, min)
                .show()
        }

        btn_time.setOnClickListener {
            val mCalendar = Calendar.getInstance()
            val hour = mCalendar.get(Calendar.HOUR_OF_DAY)
            val min = mCalendar.get(Calendar.MINUTE)
            TimePickerBuidlerDialog.buidler(this)
                .setLinkage(true)
                .setIsLoop(false)
                .showLine(true)
                .setLineColor(resources.getColor(R.color.colorAccent))
                .setLablerColor(Color.GREEN)
                .setSelectTime(hour, min)
                .setOnTimePickerListener { h, m ->
                    Toast.makeText(this, "$h:$m", Toast.LENGTH_SHORT).show();
                }
                .show()

        }
        var com = "10"
        btn_raido.setOnClickListener {

            val list = mutableListOf<String>()

            for (index in 0..40) {
                list.add("$index")
            }
            RadioPickerDialog.buidler(this)
                .setInitData(list)
                .setSelectItem(com)
                .setIsLoop(false)
                .setLineSpace(30.0f)
                .setOnRadioPickerListener { postion, item ->
                    com = item
                }
                .show()

        }
        btn_address.setOnClickListener {

            val task = AddressPickTask(this)
            task.setHideProvince(false)
            task.setHideCounty(false)
            task.setCallback(object : AddressPickTask.Callback {
                override fun onAddressPicked(province: Province?, city: City?, county: County?) {
                    if (county == null) {
                        showToast(province!!.areaName + city!!.areaName)
                    } else {
                        showToast(province!!.areaName + city!!.areaName + county.areaName)
                    }
                }

                override fun onAddressInitFailed() {
                    showToast("数据初始化失败")
                }

            })
            task.execute("贵州", "毕节", "纳雍")
        }
        btn_address1.setOnClickListener {
            try {
                val data = ArrayList<Province>()
                val json: String = ConvertUtils.toString(assets.open("city2.json"))
                data.addAll(JSON.parseArray(json, Province::class.java))
                val picker = AddressPicker(this, data)
                picker.setCanLoop(true)
                picker.setHideProvince(true)
                picker.setWheelModeEnable(false)
                picker.setSelectedItem("贵州", "贵阳", "花溪")
                picker.setOnLinkageListener(object : OnLinkageListener {
                    override fun onAddressPicked(province: Province, city: City, county: County) {
                        showToast("province : $province, city: $city, county: $county")
                    }
                })
                picker.show()
            } catch (e: Exception) {
                showToast(LogUtils.toStackTraceString(e))
            }

        }
        btn_address2.setOnClickListener {
            val task = AddressPickTask(this)
            task.setHideCounty(true)
            task.setCallback(object : AddressPickTask.Callback {
               override fun onAddressInitFailed() {
                    showToast("数据初始化失败")
                }

                override fun onAddressPicked(province: Province, city: City, county: County?) {
                    showToast(province.areaName.toString() + " " + city.areaName)
                }
            })
            task.execute("四川", "阿坝")
        }
    }

    private fun initViewModel() {

    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}