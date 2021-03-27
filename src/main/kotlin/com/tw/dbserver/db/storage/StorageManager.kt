package com.tw.dbserver.db.storage

import org.lmdbjava.ByteBufferProxy.PROXY_SAFE
import org.lmdbjava.Dbi
import org.lmdbjava.DbiFlags.MDB_CREATE
import org.lmdbjava.Env
import org.lmdbjava.Env.create
import org.lmdbjava.KeyRange
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteBuffer.allocateDirect
import java.nio.charset.StandardCharsets.UTF_8

typealias KeyValueRecord = Pair<ByteBuffer, ByteBuffer>

class StorageManager {

    private val dbName = "my DB"

    private val env: Env<ByteBuffer>
    private val db: Dbi<ByteBuffer>

    init {
        val path = File("/Users/tomasz.wiewiora/db-server/db-files")
        path.mkdir()
        env = create(PROXY_SAFE)
                .setMapSize(10485760)
                .setMaxDbs(100)
                .open(path)
        db = env.openDbi(dbName, MDB_CREATE)
        putKeyValue(1, 2, 12)
        putKeyValue(3, 4, 34)
        putKeyValue(5, 6, 56)
        putKeyValue(7, 8, 78)
        putKeyValue(9, 10, 910)
    }

    fun scan(table: String) = sequence<KeyValueRecord> {
        env.txnRead().use { txn ->
            db.iterate(txn, KeyRange.all()).use { ci ->
                for (kv in ci) {
                    yield(Pair(kv.key(), kv.`val`()))
                }
            }
        }
    }

    fun insert(table: String, key: ByteBuffer, value: ByteBuffer) {
        db.put(key, value)
    }

    private fun putKeyValue(key: Int, value: Int, value2: Int) {
        val keyBuffer: ByteBuffer = allocateDirect(4)
        val valBuffer: ByteBuffer = allocateDirect(8)
        keyBuffer.putInt(key).flip()
        valBuffer.putInt(value).putInt(value2).flip()

        println(valBuffer.remaining())
        db.put(keyBuffer, valBuffer)
    }
}