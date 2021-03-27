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

typealias KeyValueRecord = Pair<ByteBuffer, ByteBuffer>

class StorageManager {

    private val env: Env<ByteBuffer>
    private val dbs = mutableMapOf<String, Dbi<ByteBuffer>>()

    init {
        val path = File("/Users/tomasz.wiewiora/db-server/db-files")
        path.mkdir()
        env = create(PROXY_SAFE)
                .setMapSize(10485760)
                .setMaxDbs(100)
                .open(path)

        val db = getDbi("table")
        putKeyValue(db, 1, 2, 12)
        putKeyValue(db, 3, 4, 34)
        putKeyValue(db, 5, 6, 56)
        putKeyValue(db, 7, 8, 78)
        putKeyValue(db, 9, 10, 910)
    }

    fun scan(table: String) = sequence<KeyValueRecord> {
        val db = getDbi(table)
        env.txnRead().use { txn ->
            db.iterate(txn, KeyRange.all()).use { ci ->
                for (kv in ci) {
                    yield(Pair(kv.key(), kv.`val`()))
                }
            }
        }
    }

    private fun getDbi(table: String): Dbi<ByteBuffer> {
        return dbs.computeIfAbsent(table) {
            env.openDbi(it, MDB_CREATE)!!
        }
    }

    private fun putKeyValue(db: Dbi<ByteBuffer>, key: Int, value: Int, value2: Int) {
        val keyBuffer: ByteBuffer = allocateDirect(4)
        val valBuffer: ByteBuffer = allocateDirect(8)
        keyBuffer.putInt(key).flip()
        valBuffer.putInt(value).putInt(value2).flip()

        println(valBuffer.remaining())
        db.put(keyBuffer, valBuffer)
    }
}