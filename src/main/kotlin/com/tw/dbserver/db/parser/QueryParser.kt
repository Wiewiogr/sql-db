package com.tw.dbserver.db.parser

import com.tw.dbserver.db.SelectQuery

class QueryParser {

    fun parse(sqlQuery: String): SelectQuery {
        // SELECT * FROM table
        val queryParts = sqlQuery.split(" ")
        return SelectQuery(
                listOf("*"),
                queryParts[queryParts.size - 1]
        )
    }
}