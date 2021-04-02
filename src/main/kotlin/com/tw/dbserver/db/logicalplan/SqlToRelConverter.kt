package com.tw.dbserver.db.logicalplan

import com.tw.dbserver.db.catalog.Catalog
import com.tw.dbserver.db.logicalplan.nodes.RelNode
import com.tw.dbserver.db.parser.ast.SqlStatement

/***
 * Responsible for conversion from AST (SqlNode) to Logical plan (RelNode)
 */
class SqlToRelConverter(private val catalog: Catalog) {

    fun convert(rootSqlNode: SqlStatement): RelNode {
        return rootSqlNode.toRelNode()
    }
}