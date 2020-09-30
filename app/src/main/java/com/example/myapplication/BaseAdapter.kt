package com.example.myapplication

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class BaseAdapter(var mContext: Context, var infoList: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.item_test, infoList) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
    }

}

