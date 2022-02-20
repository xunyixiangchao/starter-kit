package com.lis.starter_kit.startup.manage

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*


/**
 *
 * Created by lis on 2022/2/20.
 */
object ExecutorManager {
    var cpuExecutor: ThreadPoolExecutor? = null
    var ioExecutor: Executor? = null
    var mainExecutor: Executor? = null

    //获得CPU核心数
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5))
    private val MAX_POOL_SIZE = CORE_POOL_SIZE
    private const val KEEP_ALIVE_TIME = 5L

    init {
        cpuExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS,
            LinkedBlockingDeque<Runnable>(),
            Executors.defaultThreadFactory()
        )
        cpuExecutor!!.allowCoreThreadTimeOut(true)
        ioExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())
        mainExecutor = object : Executor {
            var handler: Handler = Handler(Looper.getMainLooper())
            override fun execute(command: Runnable) {
                handler.post(command)
            }
        }
    }
}