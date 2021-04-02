package com.tw.dbserver.db.logicalplan.nodes

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.execution.Operator
import com.tw.dbserver.db.storage.StorageManager

interface RelNode {
    fun toOperator(storageManager: StorageManager, catalog: Catalog): Operator
}