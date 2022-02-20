package com.lis.starter_kit.startup.run

import android.content.Context
import android.os.Process
import com.lis.starter_kit.startup.Result
import com.lis.starter_kit.startup.Startup
import com.lis.starter_kit.startup.manage.StartupCacheManager
import com.lis.starter_kit.startup.manage.StartupManager

/**
 *
 * Created by lis on 2022/2/20.
 */
class StartupRunnable:Runnable {
    var startupManager:StartupManager?=null
    var startup:Startup<*>?=null
    var context:Context

    constructor(startupManager: StartupManager?, startup: Startup<*>?, context: Context) {
        this.startupManager = startupManager
        this.startup = startup
        this.context = context
    }

    override fun run() {
        startup?.getThreadPriority()?.let { Process.setThreadPriority(it) }
        startup?.toWait()
        val o = startup?.create(context!!)
        startup?.javaClass?.let {
            StartupCacheManager.getInstance()
                .saveInitializedComponent(it, Result(o))
        }
        startupManager?.notifyChildren(startup)
    }
}