package com.tw.dbserver.db.execution.operators

import com.tw.dbserver.db.catalog.TableInfo
import com.tw.dbserver.db.storage.KeyValueRecord
import java.nio.ByteBuffer

class SerializeFromValueOperator(
        private val data: List<Row>,
        private val tableInfo: TableInfo
) : RawDataOperator {
    override fun iterator(): Iterator<KeyValueRecord> {
        return sequence<KeyValueRecord> {
            data.forEach { row ->
                val keySize = getKeySize(row)
                val keyBuffer: ByteBuffer = ByteBuffer.allocateDirect(keySize)

                val valueSize = valueSize(row)
                val valBuffer: ByteBuffer = ByteBuffer.allocateDirect(valueSize)

                tableInfo.keyColumn.type.serializer.write(row[0], keyBuffer)
                tableInfo.columns.forEachIndexed { i, col ->
                    col.type.serializer.write(row[i + 1], valBuffer)
                }

                yield(KeyValueRecord(
                        keyBuffer.flip(),
                        valBuffer.flip()
                ))
            }
        }.iterator()
    }

    private fun valueSize(row: Row) = tableInfo.columns.mapIndexed { i, col ->
        col.type.serializer.recordSize(row[i + 1])
    }.sum()

    private fun getKeySize(row: Row) =
            tableInfo.keyColumn.type.serializer.recordSize(row[0])
}