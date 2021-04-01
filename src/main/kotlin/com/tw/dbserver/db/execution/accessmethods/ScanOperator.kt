package com.tw.dbserver.db.execution.accessmethods

import com.tw.dbserver.db.catalog.TableInfo
import com.tw.dbserver.db.execution.Operator
import com.tw.dbserver.db.execution.Row
import com.tw.dbserver.db.storage.KeyValueRecord

class ScanOperator(private val data: Sequence<KeyValueRecord>,
                   private val tableInfo: TableInfo) : Operator {

    override fun iterator(): Iterator<Row> {
        return sequence {
            val columns = tableInfo.columns
            val keyColumn = tableInfo.keyColumn
            val numberOfColumns = columns.size + 1

            data.forEach { (key, value) ->
                val row = Row(numberOfColumns) {
                    if (it == 0) {
                        val read = keyColumn.type.serializer.read(key)
                        read
                    } else {
                        columns[it - 1].type.serializer.read(value)
                    }
                }

                yield(row)
            }
        }.iterator()
    }
}