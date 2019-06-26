package com.example.cnb

import android.util.Log
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object GameConnection {

    // private val socket = IO.socket("http://10.1.1.25:3002/game")
    private val socket = IO.socket("http://cnb.finx-rocks.com/game")
    private var gameState: GameState? = null
    private var playersState: Array<Player> = arrayOf()
    private const val TAG = "GAME_CONNECTION"
    private var gameStateCallbacks: Array<(gameState: GameState) -> Unit> = arrayOf()
    private var playerListCallbacks: Array<(players: Array<Player>) -> Unit> = arrayOf()

    init {
        socket.on(Socket.EVENT_CONNECT) {
            Log.d(TAG, "Connected")
        }.on(Socket.EVENT_CONNECT_ERROR) {
            Log.d(TAG, "Error connecting :(")
            it.forEach {
                Log.d(TAG, it.toString())
            }
        }.on(Socket.EVENT_ERROR) {
            Log.d(TAG, "Error :(")
        }.on("CONNECTION_ESTABLISHED") {
            Log.d(TAG, "CONNECTED TO GAME!")
        }.on("GAME_VIEW") {
            Log.d(TAG, "GAME VIEW!")
            Log.d(TAG, it[0].toString())
            val state: GameState = Gson().fromJson(it[0].toString(), GameState::class.java)
            Log.d(TAG, state.toString())
            gameState = state
            notifyGameStateUpdate(state)
        }.on("PLAYERS_UPDATE") {
            Log.d(TAG, "PLAYERS UPDATE")
            Log.d(TAG, it[0].toString())
            val playersList: Array<Player> = Gson().fromJson(it[0].toString(), Array<Player>::class.java)
            playersState = playersList
            Log.d(TAG, playersList.toString())
            notifyPlayersUpdate(playersState)
        }

        socket.connect()

        val getGameViewPayload = JSONObject()
        getGameViewPayload.put("type", "GET_GAME_VIEW")
        socket.emit("GET_GAME_VIEW", getGameViewPayload)

    }

    private fun notifyGameStateUpdate(gameState: GameState) {
        gameStateCallbacks.forEach { callback ->
            callback(gameState)
        }
    }

    private fun notifyPlayersUpdate(players: Array<Player>) {
        playerListCallbacks.forEach { callback ->
            callback(players)
        }
    }

    fun onGameStateUpdate(callback: (gameState: GameState) -> Unit) {
        gameStateCallbacks += callback
        gameState?.let { gameState -> callback(gameState) }
    }

    fun onPlayersUpdate(callback: (players: Array<Player>) -> Unit) {
        playerListCallbacks += callback
        playersState?.let { players -> callback(playersState) }
    }

    fun destroy() {
        socket.disconnect()
    }
}