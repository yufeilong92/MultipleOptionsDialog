package com.example.myapplication

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2020/10/1 22:19
 * @Purpose :
 */
class A private constructor() {
    private lateinit var name: String
    private lateinit var job: String

    companion object {
        fun  builde():ABuilder{
            return  ABuilder()
        }
    }


    class ABuilder {
        val mA = A()
        fun setName(name: String): ABuilder {
            mA.name = name
            return this
        }

        fun setJob(job: String): ABuilder {
            mA.job = job
            return this

        }
        fun create():A{
            return mA
        }
    }

    fun show():String{
        return "name:$name,job:$job"
    }
}