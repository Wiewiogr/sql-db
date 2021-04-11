package com.tw.dbserver.db.parser.ast

import com.tw.dbserver.db.logicalplan.nodes.RelNode
import com.tw.dbserver.db.logicalplan.nodes.RelSelectNode

data class SqlSelectNode(
        val resultColumns: List<SqlResultColumn>,
        val from: From
) : SqlStatement {
    override fun toRelNode(): RelNode {
        return RelSelectNode(
                resultColumns.map { getName(it.expression) },
                from.from
        )
    }
}

fun getName(expression: Expression): String {
    return when (expression) {
        is ColumnNameExpression -> expression.name
        is AllColumnsExpression -> "*"
        else -> throw Exception("Wrong expression type")
    }
}

interface Expression : SqlNode

data class SqlResultColumn(val expression: Expression,
                           val alias: String?) : SqlNode

data class ColumnNameExpression(val name: String) : Expression

class AllColumnsExpression : Expression

data class From(val from: String) : SqlNode
