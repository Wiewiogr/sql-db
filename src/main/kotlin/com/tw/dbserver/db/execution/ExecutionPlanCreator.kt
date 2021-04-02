package com.tw.dbserver.db.execution

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.logicalplan.nodes.RelNode
import com.tw.dbserver.db.storage.StorageManager

/***
 * Responsible for materialization from Logical plan (RelNode) to Physical plan (Operator)
 */
class ExecutionPlanCreator(private val storageManager: StorageManager,
                           private val catalog: Catalog) {
    fun materialize(selectNode: RelNode): Operator {
        return selectNode.toOperator(storageManager, catalog)
    }
}