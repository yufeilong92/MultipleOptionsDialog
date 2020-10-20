package com.example.myapplication.addresspicker;


import com.example.myapplication.addresspicker.entity.City;
import com.example.myapplication.addresspicker.entity.County;
import com.example.myapplication.addresspicker.entity.Province;

/**
 * @author matt
 * blog: addapp.cn
 */

public interface OnLinkageListener {
    /**
     * 选择地址
     *
     * @param province the province
     * @param city    the city
     * @param county   the county ，if {@code hideCounty} is true，this is null
     */
    void onAddressPicked(Province province, City city, County county);
}
