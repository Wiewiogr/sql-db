package com.tw.dbserver.db.parser.ast

import com.tw.dbserver.db.logicalplan.nodes.RelNode

interface SqlNode

interface SqlStatement : SqlNode {
    fun toRelNode() : RelNode
}