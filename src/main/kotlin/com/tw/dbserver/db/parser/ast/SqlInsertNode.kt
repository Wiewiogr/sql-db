package com.tw.dbserver.db.parser.ast

import com.tw.dbserver.db.logicalplan.nodes.RelInsertNode
import com.tw.dbserver.db.logicalplan.nodes.RelNode

data class SqlInsertNode(
        private val table: Table,
        private val data: List<Array<ValueLiteral<Any>>>
) : SqlStatement {
    override fun toRelNode(): RelNode {
        return RelInsertNode(
                table.table,
                data.map { row -> row.map { it.value }.toTypedArray() }
        )
    }
}

data class Table(val table: String) : SqlNode