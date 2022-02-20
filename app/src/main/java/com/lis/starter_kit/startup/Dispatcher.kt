package com.lis.starter_kit.startup

import java.util.concurrent.Executor

/**
 *
 * Created by lis on 2022/2/20.
 */
interface Dispatcher {
    /**
     * 是否在主线程执行
     */
    fun callCreateOnMainThread():Boolean

    /**
     * 是否需要等待该任务执行完成
     */
    fun waitOnMainThread():Boolean

    fun toWait()

    fun toNotify()

    fun executor():Executor

    fun getThreadPriority():Int
}