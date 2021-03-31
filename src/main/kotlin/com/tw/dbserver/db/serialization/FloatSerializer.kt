package com.tw.dbserver.db.serialization

import java.nio.ByteBuffer

class FloatSerializer : Serializer<Float> {
    override fun write(value: Float, byteBuffer: ByteBuffer) {
        byteBuffer.putFloat(value)
    }

    override fun read(byteBuffer: ByteBuffer): Float {
        return byteBuffer.getFloat()
    }
}