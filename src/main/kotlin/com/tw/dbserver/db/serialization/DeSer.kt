package com.tw.dbserver.db.serialization

import java.nio.ByteBuffer

interface Serializer<T> {
    fun write(value: T, byteBuffer: ByteBuffer)
    fun recordSize(value: T): Int
}

interface Deserializer<T> {
    fun read(byteBuffer: ByteBuffer): T
}