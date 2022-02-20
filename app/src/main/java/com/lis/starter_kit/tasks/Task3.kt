package com.lis.starter_kit.tasks

import android.content.Context
import android.util.Log
import com.lis.starter_kit.startup.AndroidStartup
import com.lis.starter_kit.startup.Startup
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by lis on 2022/2/13.
 */
class Task3 : AndroidStartup<Void?>() {
    companion object {
        var depends: ArrayList<Class<out Startup<*>?>> = ArrayList<Class<out Startup<*>?>>()

        init {
            depends.add(Task1::class.java)
        }
    }

    override fun create(context: Context): Void? {
        Log.i("Task3", "create: Task3")
        return null
    }

    override fun dependencies(): List<Class<out Startup<*>?>> {
        return depends
    }
}