package com.tw.dbserver.db.execution

interface Operator : Sequence<Row>

typealias Row = Array<Any>