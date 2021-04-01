package com.tw.dbserver.db.parser

import SqlBaseVisitor
import SqlParser
import com.tw.dbserver.db.parser.nodes.InsertNode
import com.tw.dbserver.db.parser.nodes.SelectNode

class SqlBaseVisitorImplementation : SqlBaseVisitor<Any>() {

    override fun visitInsertStatement(ctx: SqlParser.InsertStatementContext): Any {
        val table = visit(ctx.table()) as String
        val values = ctx.value().map { visit(it) }.toTypedArray()

        return InsertNode(
                table,
                listOf(values)
        )
    }

    override fun visitSelectStatement(ctx: SqlParser.SelectStatementContext): Any {
        val resultColumns = ctx.resultColumn().map { visit(it) as String }
        val from = visit(ctx.from()) as String
        return SelectNode(
                resultColumns,
                from
        )
    }

    override fun visitValue(ctx: SqlParser.ValueContext): Any {
        if (ctx.BOOLEAN_LITERAL() != null) return ctx.BOOLEAN_LITERAL().symbol.text.toBoolean()
        if (ctx.STRING_LITERAL() != null) return ctx.STRING_LITERAL().symbol.text.drop(1).dropLast(1)
        if (ctx.NUMERIC_LITERAL() != null) return ctx.NUMERIC_LITERAL().symbol.text.toFloat()
        return Any()
    }

    override fun visitTable(ctx: SqlParser.TableContext): Any {
        return visit(ctx.anyName())
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

    override fun visitAnyName(ctx: SqlParser.AnyNameContext): Any {
        if (ctx.anyName() != null) {
            return visit(ctx.anyName())
        }
        return ctx.IDENTIFIER().symbol.text
    }
}