package com.lis.starter_kit.startup.manage

import com.lis.starter_kit.startup.Result
import com.lis.starter_kit.startup.Startup
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by lis on 2022/2/17.
 */
class StartupCacheManager private constructor() {
    companion object {
        @Volatile
        private var mInstance: StartupCacheManager? = null
        fun getInstance(): StartupCacheManager {
            return mInstance ?: synchronized(this) {
                mInstance ?: StartupCacheManager().also {
                    mInstance = it
                }
            }
        }
    }

    //使用Result防止value为null，ConcurrentHashMap的key和value都不能为null
    private val mInitializedComponents = ConcurrentHashMap<Class<out Startup<*>?>, Result<*>>()
    fun saveInitializedComponent(zClass: Class<out Startup<*>?>, result: Result<*>) {
        mInitializedComponents[zClass] = result
    }

    fun hadInitialized(zClass: Class<out Startup<*>?>?) {
        mInitializedComponents.contains(zClass)
    }

    fun <T> obtainInitializedResult(zClass: Class<out Startup<T>?>): Result<*>? {
        return mInitializedComponents[zClass]
    }

    fun remove(zClass: Class<out Startup<*>?>) {
        mInitializedComponents.remove(zClass)
    }

    fun clear() {
        mInitializedComponents.clear()
    }
}