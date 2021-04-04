package com.tw.dbserver.db.execution.operators

import com.tw.dbserver.db.catalog.TableInfo

class ProjectionOperator(private val tableInfo: TableInfo,
                         private val inputOperator: Operator,
                         private val resultColumns: List<String>) : Operator {

    private val projectedColumnIndexes: List<Int>

    init {
        val columns = listOf(listOf(tableInfo.keyColumn), tableInfo.columns).flatten().map{it.name}
        val indexes = mutableListOf<Int>()

        resultColumns.forEach { col ->
            val index = columns.indexOf(col)
            if (index >= 0) {
                indexes.add(index)
            } else {
                if (col == "*") {
                    IntRange(0, columns.size).forEach {
                        indexes.add(it)
                    }
                } else {
                    throw Exception("Column $col does not exist in input operator")
                }
            }
        }
        projectedColumnIndexes = indexes
    }

    override fun iterator(): Iterator<Row> {
        return sequence {
            inputOperator.forEach { inputRow ->
                val row = Row(projectedColumnIndexes.size) {
                    inputRow[projectedColumnIndexes[it]]
                }
                yield(row)
            }
        }.iterator()
    }
}