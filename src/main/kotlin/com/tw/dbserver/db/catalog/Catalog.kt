package com.tw.dbserver.db.catalog

import com.tw.dbserver.db.serialization.*

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

enum class ColumnType(val serializer: Serializer<in Any>,
                      val deserializer: Deserializer<out Any>) {
    INTEGER(IntegerSerializer() as Serializer<Any>, IntegerDeserializer()),
    BOOLEAN(BooleanSerializer() as Serializer<Any>, BooleanDeserializer()),
    FLOAT(FloatSerializer() as Serializer<Any>, FloatDeserializer()),
}