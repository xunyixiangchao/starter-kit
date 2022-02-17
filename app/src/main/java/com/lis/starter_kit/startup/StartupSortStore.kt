package com.lis.starter_kit.startup

/**
 * Created by lis on 2022/2/13.
 */
class StartupSortStore(//所有的任务
    var result: List<Startup<*>>,
    var startupMap: Map<Class<out Startup<*>>, Startup<*>>, //当前任务的子任务
    var startupChildrenMap: MutableMap<Class<out Startup<*>?>?, MutableList<Class<out Startup<*>?>>>
)