package com.tw.dbserver.db

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.catalog.Column
import com.tw.dbserver.db.catalog.ColumnType
import com.tw.dbserver.db.catalog.TableInfo
import com.tw.dbserver.db.execution.Row
import com.tw.dbserver.db.parser.QueryParser
import com.tw.dbserver.db.planner.QueryPlanner
import com.tw.dbserver.db.storage.StorageManager

class QueryOrchestrator(private val queryParser: QueryParser,
                        private val queryPlanner: QueryPlanner) {

    fun run(query: String): List<Row> {
        val parsedQuery = queryParser.parse(query)
        val operator = queryPlanner.plan(parsedQuery)
        return operator.toList()
    }
}

fun main() {
    val storageManager = StorageManager()
    val catalog = Catalog()
    val tableInfo = TableInfo(
            "table",
            Column("key", ColumnType.INTEGER),
            listOf(
                    Column("col1", ColumnType.INTEGER),
                    Column("col2", ColumnType.INTEGER)
            )
    )
    catalog.putTableInfo("table", tableInfo)
    val queryOrchestrator = QueryOrchestrator(
            QueryParser(),
            QueryPlanner(
                    storageManager,
                    catalog
            )
    )
    val result = queryOrchestrator.run("SELECT * FROM table")

    result.forEach {row ->
        row.forEach { value -> print("$value, ") }
        println()
    }
}
