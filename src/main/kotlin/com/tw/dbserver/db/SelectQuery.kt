package com.tw.dbserver.db

data class SelectQuery(
        val projectionsSet: List<String>,
        val from: String
)