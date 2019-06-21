package com.example.cnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_spectator.*
import kotlinx.android.synthetic.main.team_view.view.*
import org.json.JSONObject

class Spectator : AppCompatActivity() {

    private val TAG = "SOCKET"
    // private val socket = IO.socket("http://10.1.1.25:3002/game")
    private val socket = IO.socket("http://cnb.finx-rocks.com/game")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spectator)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        player1View.winnerIcon.visibility = View.INVISIBLE
        player1View.winnerText.text = ""

        player2View.winnerIcon.visibility = View.INVISIBLE
        player2View.winnerText.text = ""


        Log.d(TAG, "About to connect...")

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
            // var data = it[0]
            val state: GameState = Gson().fromJson(it[0].toString(), GameState::class.java)
            Log.d(TAG, state.toString())

            runOnUiThread {

                loadingIndicator.visibility = View.INVISIBLE
                state.player1?.let { player ->
                    player1View.teamName.text = player.name
                    player1View.playerName.text = player.avatar?.name

                    if (state.status == "FINISHED") {
                        player1View.usedPowerup.text = player.powerUp

                    } else {
                        when {
                            player.powerUp == null -> player1View.usedPowerup.text = ""
                            player.powerUp != "NONE" -> player1View.usedPowerup.text = "YES"
                            else -> player1View.usedPowerup.text = "NO"
                        }
                    }
                }

                state.player2?.let { player ->
                    player2View.teamName.text = player.name
                    player2View.playerName.text = player.avatar?.name

                    if (state.status == "FINISHED") {
                        player2View.usedPowerup.text = player.powerUp

                    } else {
                        when {
                            player.powerUp == null -> player2View.usedPowerup.text = ""
                            player.powerUp != "NONE" -> player2View.usedPowerup.text = "YES"
                            else -> player2View.usedPowerup.text = "NO"
                        }
                    }
                }

                player1View.winnerIcon.visibility = View.INVISIBLE
                player1View.winnerText.text = ""

                player2View.winnerIcon.visibility = View.INVISIBLE
                player2View.winnerText.text = ""

                state.result?.let {

                    when {
                        state.result?.draw == true -> {
                            player1View.winnerIcon.visibility = View.VISIBLE
                            player1View.winnerText.text = "Draw"
                            player2View.winnerIcon.visibility = View.VISIBLE
                            player2View.winnerText.text = "Draw"
                        }
                        state.result.winner == "player1" -> {
                            player1View.winnerIcon.visibility = View.VISIBLE
                            player1View.winnerText.text = "Winner"
                        }
                        state.result.winner == "player2" -> {
                            player2View.winnerIcon.visibility = View.VISIBLE
                            player2View.winnerText.text = "Winner"
                        }
                    }
                }
            }


        }

        socket.connect()

        val getGameViewPayload: JSONObject = JSONObject()
        getGameViewPayload.put("type", "GET_GAME_VIEW")


        socket.emit("GET_GAME_VIEW", getGameViewPayload)

    }
}
