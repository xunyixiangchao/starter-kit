package com.lis.starter_kit.startup.manage

import android.content.Context
import android.os.Looper
import com.lis.starter_kit.startup.AndroidStartup
import com.lis.starter_kit.startup.Result
import com.lis.starter_kit.startup.StartupSortStore
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
            val o = startup.create(context!!)!!
            StartupCacheManager.getInstance()
                .saveInitializedComponent(startup.javaClass, Result(o))
        }
        return this
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