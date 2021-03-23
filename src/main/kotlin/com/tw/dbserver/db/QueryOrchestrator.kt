package com.tw.dbserver.db

import com.tw.dbserver.db.parser.QueryParser
import com.tw.dbserver.db.planner.QueryPlanner

class QueryOrchestrator(private val queryParser: QueryParser,
                        private val queryPlanner: QueryPlanner) {

    fun run(query: String): List<String> {
        val parsedQuery = queryParser.parse(query)
        val operator = queryPlanner.plan(parsedQuery)
        return operator.toList()
    }
}

fun main() {
    val queryOrchestrator = QueryOrchestrator(QueryParser(), QueryPlanner())
    println(queryOrchestrator.run("SELECT * FROM table"))
}
