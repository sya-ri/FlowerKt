package com.kokasai.flowerkt

import com.kokasai.flowerkt.module.LaunchProcess
import com.kokasai.flowerkt.route.RouteBuilder
import io.ktor.application.Application
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.embeddedServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface FlowerKt : LaunchProcess {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger("FlowerKt")
    }

    /**
     * サーバーのポート番号です
     */
    val port: Int

    /**
     * Ktor の機能をインストールします
     */
    fun Application.installKtorFeature()

    /**
     * ルートビルダーでルートの登録をします
     */
    val routeBuilder: RouteBuilder

    /**
     * モジュールの設定をします
     */
    private fun Application.setupServerModule() {
        installKtorFeature()
        routing {
            routeBuilder.build(this)
        }
    }

    /**
     * サーバーのエンジン
     */
    val engine: ApplicationEngineFactory<ApplicationEngine, ApplicationEngine.Configuration>

    /**
     * サーバーを起動します
     */
    private fun startServer() {
        embeddedServer(engine, port) {
            setupServerModule()
        }.start()
    }

    /**
     * プロセスを開始します
     *
     * `main` 関数でこの関数を呼び出してください
     */
    override fun launch() {
        startServer()
    }
}
