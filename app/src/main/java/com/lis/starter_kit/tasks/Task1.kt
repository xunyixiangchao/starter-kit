package com.lis.starter_kit.tasks

import android.content.Context
import android.util.Log
import com.lis.starter_kit.startup.AndroidStartup
import com.lis.starter_kit.startup.Startup

/**
 *任务1
 * Created by lis on 2022/2/13.
 */
class Task1 : AndroidStartup<Void?>() {
    override fun create(context: Context): Void? {
        Log.i("Task1", "create: Task1")
        return null
    }

    override fun dependencies(): List<Class<out Startup<*>?>?>? {
        return super.dependencies()
    }
}