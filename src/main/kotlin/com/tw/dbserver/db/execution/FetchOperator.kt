package com.tw.dbserver.db.execution

class FetchOperator : Operator {

    override fun iterator(): Iterator<String> {
        return sequence {
            yield("a")
            yield("b")
        }.iterator()
    }
}