package com.lis.starter_kit.startup.manage

import android.content.Context
import android.os.Looper
import com.lis.starter_kit.startup.AndroidStartup
import com.lis.starter_kit.startup.Result
import com.lis.starter_kit.startup.Startup
import com.lis.starter_kit.startup.StartupSortStore
import com.lis.starter_kit.startup.run.StartupRunnable
import com.lis.starter_kit.startup.sort.TopologySort.sort
import java.util.*

/**
 * Created by lis on 2022/2/17.
 */
class StartupManager(
    private val context: Context?,
    private val startupList: List<AndroidStartup<*>>
) {
    private var startupSortStore: StartupSortStore? = null
    fun start(): StartupManager {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw RuntimeException("请在主线程中调用！")
        }
        startupSortStore = sort(startupList)
        for (startup in startupSortStore!!.result) {
            val startupRunnable = StartupRunnable(this, startup, context!!)
            if (startup.callCreateOnMainThread()) {
                startupRunnable.run()
            } else {
                startup.executor().execute(startupRunnable)
            }
        }
        return this
    }

    fun notifyChildren(startup: Startup<*>?) {
        //获得已经完成的当前任务的所有子任务
        if (startupSortStore?.startupChildrenMap?.containsKey(startup?.javaClass) == true) {
            val childStartupCls = startupSortStore!!.startupChildrenMap[startup?.javaClass]
            childStartupCls?.forEach { cls ->
                run {
                    //通知子任务 startup父任务已完成
                    val childStartup = startupSortStore!!.startupMap.get(cls)
                    childStartup?.toNotify()
                }
            }
        }
    }

    class Builder {
        private val startupList: MutableList<AndroidStartup<*>> = ArrayList()
        fun addStartup(startup: AndroidStartup<*>): Builder {
            startupList.add(startup)
            return this
        }

        fun addAllStartup(startups: List<AndroidStartup<*>>?): Builder {
            startupList.addAll(startups!!)
            return this
        }

        fun build(context: Context?): StartupManager {
            return StartupManager(context, startupList)
        }
    }
}