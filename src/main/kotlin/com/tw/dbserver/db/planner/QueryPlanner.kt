package com.tw.dbserver.db.planner

import com.tw.dbserver.db.SelectQuery
import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.execution.Operator
import com.tw.dbserver.db.execution.accessmethods.ScanOperator
import com.tw.dbserver.db.storage.StorageManager

class QueryPlanner(private val storageManager: StorageManager,
                   private val catalog: Catalog) {
    fun plan(selectQuery: SelectQuery): Operator {
        println(selectQuery)

        return ScanOperator(
                storageManager.scan(selectQuery.from),
                catalog.getTableInfo(selectQuery.from)!!
        )
    }
}