package com.lis.starter_kit.startup

/**
 * Created by lis on 2022/2/13.
 */
abstract class AndroidStartup<T> : Startup<T> {

    override fun dependencies(): List<Class<out Startup<*>?>?>? {
        return null
    }
    override fun getDependenciesCount(): Int {
        val dependencies = dependencies()
        return dependencies?.size ?: 0
    }
}