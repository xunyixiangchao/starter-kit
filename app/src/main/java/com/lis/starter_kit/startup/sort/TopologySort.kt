package com.lis.starter_kit.startup.sort

import com.lis.starter_kit.startup.Startup
import com.lis.starter_kit.startup.StartupSortStore
import java.util.*

/**
 * 拓扑排序
 * Created by lis on 2022/2/13.
 */
object TopologySort {
    fun sort(startupList: List<Startup<*>>): StartupSortStore {
        /**
         * 入度表
         */
        val inDegreeMap: MutableMap<Class<out Startup<*>?>, Int> = HashMap()

        /**
         * 0入度队列
         */
        val zeroDeque: Deque<Class<out Startup<*>?>> = ArrayDeque()
        val startupMap: MutableMap<Class<out Startup<*>?>, Startup<*>> = HashMap()

        /**
         * 任务依赖表
         */
        val startupChildrenMap: MutableMap<Class<out Startup<*>?>?, MutableList<Class<out Startup<*>?>>> =
            HashMap()
        for (startup in startupList) {
            startupMap[startup.javaClass] = startup
            //记录每个任务的入度数（依赖的任务数）
            val dependenciesCount = startup.getDependenciesCount()
            inDegreeMap[startup.javaClass] = dependenciesCount
            //入度为0的任务
            if (dependenciesCount == 0) {
                zeroDeque.offer(startup.javaClass)
            } else {
                //遍历本任务的依赖（父）任务列表
                for (parent in startup.dependencies()!!) {
                    var children = startupChildrenMap[parent]
                    if (children == null) {
                        children = ArrayList()
                        //记录这个父任务的所有子任务
                        startupChildrenMap[parent] = children
                    }
                    children.add(startup.javaClass)
                }
            }
        }
        val result: MutableList<Startup<*>> = ArrayList()
        //处理入度为0的任务
        while (!zeroDeque.isEmpty()) {
            val poll = zeroDeque.poll()
            val startup = startupMap[poll]!!
            result.add(startup)
            /**
             * 删除后再找出现在0入度的顶点
             */
            if (startupChildrenMap.containsKey(poll)) {
                val childStartup: List<Class<out Startup<*>?>> = startupChildrenMap[poll]!!
                for (childCls in childStartup) {
                    val num = inDegreeMap[childCls]
                    inDegreeMap[childCls] = num!! - 1
                    if (num - 1 == 0) {
                        zeroDeque.offer(childCls)
                    }
                }
            }
        }
        return StartupSortStore(result, startupMap, startupChildrenMap)
    }
}