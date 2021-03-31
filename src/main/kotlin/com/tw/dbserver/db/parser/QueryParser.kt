package com.tw.dbserver.db.parser

import SqlLexer
import SqlParser
import com.tw.dbserver.db.SelectQuery
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class QueryParser {

    fun parse(sqlQuery: String): SelectQuery {
        val lexer = SqlLexer(CharStreams.fromString(sqlQuery))
        val tokens = CommonTokenStream(lexer)
        val parser = SqlParser(tokens)
        val expression = parser.statement()

        return SqlBaseVisitorImplementation().visit(expression) as SelectQuery
    }
}

fun main() {
    println(QueryParser().parse("SELECT col1, col2 FROM table"))
}
