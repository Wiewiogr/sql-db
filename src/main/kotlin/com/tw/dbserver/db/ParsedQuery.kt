package com.tw.dbserver.db

data class ParsedQuery(
        private val projectionsSet: List<String>,
        private val from: String
)