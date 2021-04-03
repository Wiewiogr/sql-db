package com.tw.dbserver.db.execution.operators

import com.tw.dbserver.db.storage.KeyValueRecord

// Operates on rows
interface Operator : Sequence<Row>

// Operate on bytes
interface RawDataOperator : Sequence<KeyValueRecord>

typealias Row = Array<Any>