package com.tw.dbserver.db.catalog

import com.tw.dbserver.db.serialization.BooleanSerializer
import com.tw.dbserver.db.serialization.FloatSerializer
import com.tw.dbserver.db.serialization.IntegerSerializer
import com.tw.dbserver.db.serialization.Serializer

class Catalog {

    private val tables = mutableMapOf<String, TableInfo>()
    fun getTableInfo(tableName: String): TableInfo? {
        return tables[tableName]
    }

    fun putTableInfo(tableName: String, tableInfo: TableInfo) {
        tables[tableName] = tableInfo
    }
}

data class TableInfo(val tableName: String,
                     val keyColumn: Column,
                     val columns: List<Column>)

data class Column(val name: String, val type: ColumnType)

enum class ColumnType(val serializer: Serializer<out Any>) {
    INTEGER(IntegerSerializer()),
    BOOLEAN(BooleanSerializer()),
    FLOAT(FloatSerializer()),
}