package com.tw.dbserver.db.serialization

import java.nio.ByteBuffer

class BooleanSerializer : Serializer<Boolean> {
    override fun write(value: Boolean, byteBuffer: ByteBuffer) {
        byteBuffer.put(toByte(value))
    }

    override fun read(byteBuffer: ByteBuffer): Boolean {
        return toBoolean(byteBuffer.get())
    }

    private fun toByte(value: Boolean): Byte = if (value) 1 else 0

    private fun toBoolean(value: Byte): Boolean = value == 1.toByte()
}