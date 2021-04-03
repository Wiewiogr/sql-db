package com.tw.dbserver.db.logicalplan.nodes

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.execution.operators.InsertOperator
import com.tw.dbserver.db.execution.operators.Operator
import com.tw.dbserver.db.execution.operators.Row
import com.tw.dbserver.db.execution.operators.SerializeFromValueOperator
import com.tw.dbserver.db.storage.StorageManager

data class RelInsertNode(
        private val table: String,
        private val data: List<Row>
) : RelNode {
    override fun toOperator(storageManager: StorageManager, catalog: Catalog): Operator {
        val tableInfo = catalog.getTableInfo(table)!!
        val serializeFromValueOperator = SerializeFromValueOperator(
                data,
                tableInfo
        )

        return InsertOperator(
                serializeFromValueOperator,
                tableInfo,
                storageManager::insert
        )
    }
}