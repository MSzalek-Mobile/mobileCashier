package com.mszalek.hajsolicznik.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_player_balance.*

/**
 * Needs to be created with intent Player
 */
class PlayerActivity : AppCompatActivity() {

    object EXTRA_KEYS{
        val PLAYER = "extraPlayerKey"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar)

        val extraPlayer = intent.extras?.getSerializable(EXTRA_KEYS.PLAYER)
        if (extraPlayer is Player) {
            tvName.text = extraPlayer.name
            tvBalance.text = extraPlayer.getBalance().toString()
        }
    }
}
