package com.tw.dbserver.db.storage

import org.lmdbjava.ByteBufferProxy.PROXY_SAFE
import org.lmdbjava.Dbi
import org.lmdbjava.DbiFlags.MDB_CREATE
import org.lmdbjava.Env
import org.lmdbjava.Env.create
import org.lmdbjava.KeyRange
import java.io.File
import java.nio.ByteBuffer

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

    fun insert(table: String, keyValueRecord: KeyValueRecord) {
        val db = getDbi(table)
        db.put(keyValueRecord.first, keyValueRecord.second)
    }

    private fun getDbi(table: String): Dbi<ByteBuffer> {
        return dbs.computeIfAbsent(table) {
            env.openDbi(it, MDB_CREATE)!!
        }
    }
}