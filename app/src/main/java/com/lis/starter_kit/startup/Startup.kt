package com.lis.starter_kit.startup

import android.content.Context

/**
 *
 * Created by lis on 2022/2/13.
 */
interface Startup<T> {
    fun create(context: Context):T

    fun dependencies(): List<Class<out Startup<*>?>?>?

    fun getDependenciesCount():Int

























}