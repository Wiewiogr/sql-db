package com.tw.dbserver.db.planner

import com.tw.dbserver.db.ParsedQuery
import com.tw.dbserver.db.execution.FetchOperator
import com.tw.dbserver.db.execution.Operator

class QueryPlanner {
    fun plan(parsedQuery: ParsedQuery): Operator {
        return FetchOperator()
    }
}