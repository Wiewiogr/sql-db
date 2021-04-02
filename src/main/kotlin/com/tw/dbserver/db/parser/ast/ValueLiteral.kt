package com.tw.dbserver.db.parser.ast

data class ValueLiteral<T>(val value: T) : SqlNode

data class AnyValue(val value: String) : SqlNode