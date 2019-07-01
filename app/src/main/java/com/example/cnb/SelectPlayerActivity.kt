package com.example.cnb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_player.*
import kotlinx.android.synthetic.main.activity_select_player.loadingIndicator
import kotlinx.android.synthetic.main.activity_spectator.*
import kotlinx.android.synthetic.main.activity_spectator.toolbar

class SelectPlayerActivity : AppCompatActivity() {

    private val gameConnection: GameConnection = GameConnection
    private var _players: Array<Player>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        playerListView.setOnItemClickListener { adapterView, view, position, id ->
            val selectedPlayer = _players?.get(position)
            val intent = Intent()

            selectedPlayer?.let { player ->
                intent.putExtra("player-name", player.name)
                intent.putExtra("player-image", player.imageName)
                intent.putExtra("team-name", player.team)
                intent.putExtra("player-slot", player.slot)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        gameConnection.onPlayersUpdate { players ->

            _players = players

            runOnUiThread {
                loadingIndicator.visibility = View.INVISIBLE
                playerListView.adapter = PlayerViewAdapter(this, players)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameConnection.destroy()

    }

    private class PlayerViewAdapter(context: Context, players: Array<Player>) : BaseAdapter() {

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
            val player = _players.get(position)
            val layoutInflater = LayoutInflater.from(_context)
            val playerViewRow = layoutInflater.inflate(R.layout.player_selection_view, viewGroup, false)

            val playerNameText = playerViewRow.findViewById<TextView>(R.id.playerName)
            playerNameText.text = player.name

            val playerTeamText = playerViewRow.findViewById<TextView>(R.id.teamName)
            playerTeamText.text = player.team

            return playerViewRow
        }

    }
}
