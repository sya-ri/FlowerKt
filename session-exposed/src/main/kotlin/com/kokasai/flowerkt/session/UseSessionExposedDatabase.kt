package com.kokasai.flowerkt.session

import com.kokasai.flowerkt.database.UseExposedDatabase
import com.kokasai.flowerkt.module.OnLaunch
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration

interface UseSessionExposedDatabase : UseExposedDatabase, OnLaunch {
    /**
     * セッションを保存するデータベース
     */
    val sessionTable
        get() = SessionTable("session", Duration.ofDays(30))

    override fun onLaunch() {
        databaseProvider.connect()
        transaction {
            create(sessionTable)
        }
    }
}