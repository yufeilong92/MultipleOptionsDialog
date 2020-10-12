package com.example.myapplication.MultipleOption

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2020/10/9 9:13
 * @Purpose :
 */
data class SelectRlv (
    var name: String = "",
    var id: String = "",
    var check: Boolean
) {
    override fun toString(): String {
        return "SelectRlv(name='$name', id='$id', check=$check)"
    }
}