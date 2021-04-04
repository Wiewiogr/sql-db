package com.tw.dbserver.db.logicalplan.nodes

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.execution.operators.Operator
import com.tw.dbserver.db.execution.operators.ProjectionOperator
import com.tw.dbserver.db.execution.operators.ScanOperator
import com.tw.dbserver.db.storage.StorageManager

data class RelSelectNode(
        val projectionsSet: List<String>,
        val from: String
) : RelNode {
    override fun toOperator(storageManager: StorageManager, catalog: Catalog): Operator {
        val scanOperator = ScanOperator(
                storageManager.scan(from),
                catalog.getTableInfo(from)!!
        )

        return if (projectionsSet.size == 1 && projectionsSet.contains("*")) {
            scanOperator
        } else {
            ProjectionOperator(
                    catalog.getTableInfo(from)!!,
                    scanOperator,
                    projectionsSet
            )
        }
    }
}