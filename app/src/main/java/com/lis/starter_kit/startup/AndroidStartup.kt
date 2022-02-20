package com.lis.starter_kit.startup

import android.os.Process
import com.lis.starter_kit.startup.manage.ExecutorManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

/**
 * Created by lis on 2022/2/13.
 */
abstract class AndroidStartup<T> : Startup<T> {
    val mWaitCountDown = CountDownLatch(getDependenciesCount())
    override fun dependencies(): List<Class<out Startup<*>?>?>? {
        return null
    }

    override fun getDependenciesCount(): Int {
        val dependencies = dependencies()
        return dependencies?.size ?: 0
    }

    override fun executor(): Executor {
        return ExecutorManager.ioExecutor!!
    }

    override fun toWait() {
        try {
            mWaitCountDown.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun toNotify() {
        mWaitCountDown.countDown()
    }

    override fun getThreadPriority(): Int {
        return Process.THREAD_PRIORITY_DEFAULT
    }
}