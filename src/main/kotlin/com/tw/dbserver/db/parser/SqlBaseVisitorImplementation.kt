package com.tw.dbserver.db.parser

import SqlBaseVisitor
import SqlParser
import com.tw.dbserver.db.SelectQuery

class SqlBaseVisitorImplementation : SqlBaseVisitor<Any>() {

    override fun visitStatement(ctx: SqlParser.StatementContext): Any {
        return visit(ctx.selectStatement())
    }

    override fun visitSelectStatement(ctx: SqlParser.SelectStatementContext): Any {
        val resultColumns = ctx.resultColumn().map { visit(it) as String }
        val from = visit(ctx.from()) as String
        return SelectQuery(
                resultColumns,
                from
        )
    }

    override fun visitAnyName(ctx: SqlParser.AnyNameContext): Any {
        if (ctx.anyName() != null) {
            return visit(ctx.anyName())
        }
        return ctx.IDENTIFIER().symbol.text
    }

    override fun visitFrom(ctx: SqlParser.FromContext): Any {
        return visit(ctx.anyName())
    }

    override fun visitResultColumn(ctx: SqlParser.ResultColumnContext): Any {
        if (ctx.ASTERISK() != null) {
            return ctx.ASTERISK().symbol.text
        }

        return visit(ctx.anyName())
    }
}