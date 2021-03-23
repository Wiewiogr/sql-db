package com.tw.dbserver.db.parser

import com.tw.dbserver.db.ParsedQuery

class QueryParser {

    fun parse(sqlQuery: String): ParsedQuery {
        // SELECT * FROM table
        val queryParts = sqlQuery.split(" ")
        return ParsedQuery(
                listOf("*"),
                queryParts[queryParts.size - 1]
        )
    }
}