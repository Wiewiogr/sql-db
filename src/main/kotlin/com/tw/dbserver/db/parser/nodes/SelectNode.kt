package com.tw.dbserver.db.parser.nodes

data class SelectNode(
        val projectionsSet: List<String>,
        val from: String
)