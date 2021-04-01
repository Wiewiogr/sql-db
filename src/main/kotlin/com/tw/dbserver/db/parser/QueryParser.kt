package com.tw.dbserver.db.parser

import SqlLexer
import SqlParser
import com.tw.dbserver.db.parser.nodes.SelectNode
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class QueryParser {

    fun parse(sqlQuery: String): SelectNode {
        val lexer = SqlLexer(CharStreams.fromString(sqlQuery))
        val tokens = CommonTokenStream(lexer)
        val parser = SqlParser(tokens)
        val expression = parser.statement()
        val query = SqlBaseVisitorImplementation().visit(expression)
        println(query)
        return query as SelectNode
    }
}

fun main() {
    println(QueryParser().parse("SELECT col1, col2 FROM table"))
    println(QueryParser().parse("INSERT INTO table VALUES(1, false, 'string')"))
}
