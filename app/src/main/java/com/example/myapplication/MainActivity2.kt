package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.loopview.dialog.TimePickerDialog
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_show.setOnClickListener {
            val mCalendar = Calendar.getInstance()
            val year = mCalendar.get(Calendar.YEAR)
            val month = mCalendar.get(Calendar.MONTH) + 1
            val day = mCalendar.get(Calendar.DAY_OF_MONTH)
            val hour=mCalendar.get(Calendar.HOUR_OF_DAY)
            val min=mCalendar.get(Calendar.MINUTE)
            TimePickerDialog.buidler(this)
                .setStartTime(2016)
                .setEndTime(2200)
                .setIsLoop(false)
                .setIsShowHourMin(true)
                .setLablerColor(Color.GREEN)
                .setLineColor(Color.GREEN)
                .setLinkage(true)
                .setOnTimePickerListener {y,m,d,h,minun->
                    Toast.makeText(this, "$y,$m,$d,$h,$minun", Toast.LENGTH_SHORT).show();
                }
                .setSelectTime(year, month, day,hour,min)
                .show()
        }
    }
}