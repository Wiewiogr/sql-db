package com.tw.dbserver.db

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.catalog.Column
import com.tw.dbserver.db.catalog.ColumnType
import com.tw.dbserver.db.catalog.TableInfo
import com.tw.dbserver.db.execution.ExecutionPlanCreator
import com.tw.dbserver.db.execution.operators.Row
import com.tw.dbserver.db.logicalplan.SqlToRelConverter
import com.tw.dbserver.db.parser.QueryParser
import com.tw.dbserver.db.parser.ast.SqlStatement
import com.tw.dbserver.db.storage.StorageManager

class QueryOrchestrator(private val queryParser: QueryParser,
                        private val executionPlanCreator: ExecutionPlanCreator,
                        private val sqlToRelConverter: SqlToRelConverter) {

    fun run(query: String): List<Row> {
        val parsedQuery = queryParser.parse(query)
        val logicalPlan = sqlToRelConverter.convert(parsedQuery as SqlStatement) // TODO: zmienić
        val operator = executionPlanCreator.materialize(logicalPlan)
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
                    Column("colInteger", ColumnType.INTEGER),
                    Column("colBoolean", ColumnType.BOOLEAN),
                    Column("colFloat", ColumnType.FLOAT),

                    )
    )
    catalog.putTableInfo("table", tableInfo)
    val queryOrchestrator = QueryOrchestrator(
            QueryParser(),
            ExecutionPlanCreator(
                    storageManager,
                    catalog
            ),
            SqlToRelConverter(catalog)
    )
    queryOrchestrator.run("INSERT INTO table VALUES(1, 12, false, 10.0123)").forEach { println(it) }
    queryOrchestrator.run("INSERT INTO table VALUES (13, 142, true, 213.86)").forEach { println(it) }
    val result = queryOrchestrator.run("SELECT * FROM table")

    result.forEach { row ->
        row.forEach { value -> print("$value, ") }
        println()
    }
}
