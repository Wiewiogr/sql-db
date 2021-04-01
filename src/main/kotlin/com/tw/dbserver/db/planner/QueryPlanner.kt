package com.tw.dbserver.db.planner

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.execution.Operator
import com.tw.dbserver.db.execution.accessmethods.ScanOperator
import com.tw.dbserver.db.parser.nodes.SelectNode
import com.tw.dbserver.db.storage.StorageManager

class QueryPlanner(private val storageManager: StorageManager,
                   private val catalog: Catalog) {
    fun plan(selectNode: SelectNode): Operator {
        return ScanOperator(
                storageManager.scan(selectNode.from),
                catalog.getTableInfo(selectNode.from)!!
        )
    }
}