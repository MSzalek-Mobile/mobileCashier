package com.mszalek.hajsolicznik.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.daimajia.swipe.util.Attributes
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.GameManager
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val gameManager = GameManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set data
        val mDataSet = gameManager.players
        val mAdapter = RecyclerViewAdapter(this, mDataSet, buyInClick, playerClick)
        mAdapter.mode = Attributes.Mode.Single
        recyclerView.adapter = mAdapter
    }

    val buyInClick: (View, Player) -> Unit = { view, player ->
        val intent = Intent(this, BuyInActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view.findViewById(R.id.swipe), getString(R.string.transition_buyin))
        startActivity(intent, options.toBundle())
    }

    val playerClick: (View, Player) -> Unit = { view, player ->
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.EXTRA_KEYS.PLAYER, player)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation( this,
                Pair.create(view.findViewById(R.id.row_player_balance), getString(R.string.transition_player_row)),
                Pair.create(view.findViewById(R.id.swipe), getString(R.string.transition_player_back)),
                Pair.create(toolbar as View, "toolbar"),
                Pair.create(findViewById(android.R.id.navigationBarBackground), Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME),
                //Pair.create(view.findViewById(R.id.tvName), getString(R.string.transition_player_name)),
                //Pair.create(view.findViewById(R.id.tvBalance), getString(R.string.transition_player_balance)),
                Pair.create(fab as View, getString(R.string.transition_player_fab)))
        startActivity(intent, options.toBundle())
    }

}
