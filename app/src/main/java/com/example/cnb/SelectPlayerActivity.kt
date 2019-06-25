package com.example.cnb

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_player.*
import kotlinx.android.synthetic.main.activity_spectator.toolbar

class SelectPlayerActivity : AppCompatActivity() {

    private val gameConnection: GameConnection = GameConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        gameConnection.onPlayersUpdate { players ->

            runOnUiThread {
                playerListView.adapter = PlayerViewAdapter(this, players)
            }

        }



    }

    private class PlayerViewAdapter(context: Context, players: Array<Player>): BaseAdapter() {

        private val _context: Context = context
        private val _players = players

        override fun getItem(position: Int): Any {
            return ""
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return _players.size
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(_context)
            val playerViewRow = layoutInflater.inflate(R.layout.player_selection_view, viewGroup, false)

            val playerNameText = playerViewRow.findViewById<TextView>(R.id.playerName)
            playerNameText.text = "player"

            val playerTeamText = playerViewRow.findViewById<TextView>(R.id.teamName)
            playerTeamText.text = "team"

            return playerViewRow
        }

    }
}
