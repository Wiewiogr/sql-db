package com.tw.dbserver.db.execution.operators

import com.tw.dbserver.db.catalog.TableInfo
import com.tw.dbserver.db.storage.KeyValueRecord
import java.util.function.BiConsumer

class InsertOperator(private val data: Sequence<KeyValueRecord>,
                     private val tableInfo: TableInfo,
                     private val writer: BiConsumer<String, KeyValueRecord>) : Operator {
    override fun iterator(): Iterator<Row> {
        data.forEach {
            writer.accept(tableInfo.tableName, it)
        }
        return listOf<Row>().iterator()
    }
}