package com.tw.dbserver.db.parser.ast

import com.tw.dbserver.db.logicalplan.nodes.RelNode
import com.tw.dbserver.db.logicalplan.nodes.RelSelectNode

data class SqlSelectNode(
        val projectionsSet: List<String>,
        val from: From
) : SqlStatement {
    override fun toRelNode(): RelNode {
        return RelSelectNode(
                projectionsSet,
                from.from
        )
    }
}

data class From(val from: String) : SqlNode
