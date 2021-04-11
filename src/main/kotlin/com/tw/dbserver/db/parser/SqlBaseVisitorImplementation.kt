package com.tw.dbserver.db.parser

import SqlBaseVisitor
import SqlParser
import com.tw.dbserver.db.parser.ast.*

class SqlBaseVisitorImplementation : SqlBaseVisitor<SqlNode>() {

    override fun visitInsertStatement(ctx: SqlParser.InsertStatementContext): SqlNode {
        val table = visit(ctx.table()) as Table
        val values = ctx.value().map { visit(it) as ValueLiteral<Any> }.toTypedArray()

        return SqlInsertNode(
                table,
                listOf(values)
        )
    }

    override fun visitSelectStatement(ctx: SqlParser.SelectStatementContext): SqlNode {
        val resultColumns = ctx.resultColumn().map { visit(it) as SqlResultColumn }
        val from = visit(ctx.from()) as From
        return SqlSelectNode(
                resultColumns,
                from
        )
    }

    override fun visitValue(ctx: SqlParser.ValueContext): SqlNode {
        if (ctx.BOOLEAN_LITERAL() != null) return ctx.BOOLEAN_LITERAL().symbol.text.toBoolean().let { ValueLiteral(it) }
        if (ctx.STRING_LITERAL() != null) return ctx.STRING_LITERAL().symbol.text.drop(1).dropLast(1).let { ValueLiteral(it) }
        if (ctx.NUMERIC_LITERAL() != null) return ctx.NUMERIC_LITERAL().symbol.text.toFloat().let { ValueLiteral(it) }
        throw Exception("Unexpected value")
    }

    override fun visitTable(ctx: SqlParser.TableContext): SqlNode {
        return Table(parseIdentifier(ctx.identifier()))
    }

    override fun visitFrom(ctx: SqlParser.FromContext): SqlNode {
        return From(parseIdentifier(ctx.identifier()))
    }

    override fun visitResultColumn(ctx: SqlParser.ResultColumnContext): SqlNode {
        if (ctx.ASTERISK() != null) {
            return SqlResultColumn(AllColumnsExpression(), null)
        }

        return SqlResultColumn(visit(ctx.expression()) as Expression, null)
    }

    override fun visitColumnReference(ctx: SqlParser.ColumnReferenceContext): SqlNode {
        val identifier = ctx.identifier()
        return ColumnNameExpression(parseIdentifier(identifier))
    }

    override fun visitIdentifier(ctx: SqlParser.IdentifierContext): SqlNode {
        if (ctx.identifier() != null) {
            return visit(ctx.identifier())
        }

        return AnyValue(ctx.IDENTIFIER().text)
    }

    private fun parseIdentifier(identifier: SqlParser.IdentifierContext?) =
            visit(identifier).let { it as AnyValue }.value
}