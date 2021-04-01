package com.tw.dbserver.db.parser.nodes

import com.tw.dbserver.db.execution.Row

data class InsertNode(
        private val table: String,
        private val data: List<Row>
)