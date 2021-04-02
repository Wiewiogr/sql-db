package com.tw.dbserver.db.parser.ast

import com.tw.dbserver.db.execution.Row
import com.tw.dbserver.db.logicalplan.nodes.RelNode

data class SqlInsertNode(
        private val table: Table,
        private val data: List<Row>
) : SqlStatement {
    override fun toRelNode(): RelNode {
        TODO("Not yet implemented")
    }
}

data class Table(private val table: String) : SqlNode