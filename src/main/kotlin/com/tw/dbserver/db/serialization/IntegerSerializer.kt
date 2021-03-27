package com.tw.dbserver.db.serialization

import java.nio.ByteBuffer

class IntegerSerializer : Serializer<Int> {
    override fun write(value: Int, byteBuffer: ByteBuffer) {
        byteBuffer.putInt(value)
    }

    override fun read(byteBuffer: ByteBuffer): Int {
        return byteBuffer.getInt()
    }
}

