package com.tw.dbserver.db.serialization

import java.nio.ByteBuffer

class FloatSerializer : Serializer<Float> {
    override fun write(value: Float, byteBuffer: ByteBuffer) {
        byteBuffer.putFloat(value)
    }

    override fun recordSize(value: Float): Int = 4
}

class FloatDeserializer : Deserializer<Float> {
    override fun read(byteBuffer: ByteBuffer): Float {
        return byteBuffer.getFloat()
    }
}