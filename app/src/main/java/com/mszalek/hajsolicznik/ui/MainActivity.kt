package com.mszalek.hajsolicznik.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.BuyIn
import com.mszalek.hajsolicznik.data.models.GameManager
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val gameManager = GameManager.singleton.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { openNewPlayerDialog(fab) }
        setUpRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.REQUEST_CODES.NEW_PLAYER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val player = data?.getSerializableExtra(Constants.RESPONSE_EXTRA_KEYS.NEW_PLAYER) as Player
                    handleNewPlayer(player)
                }
            }
            Constants.REQUEST_CODES.BUY_IN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val player = data?.getSerializableExtra(Constants.RESPONSE_EXTRA_KEYS.BUY_INED_PLAYER) as Player
                    val buyIn = data?.getSerializableExtra(Constants.RESPONSE_EXTRA_KEYS.BUY_IN) as BuyIn
                    handlePlayerBuyIn(player, buyIn)
                }
            }
            Constants.REQUEST_CODES.SINGLE_CHECKOUT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val player = data?.getSerializableExtra(Constants.RESPONSE_EXTRA_KEYS.CHECKOUTED_PLAYER) as Player
                    handleSingleCheckout(player)
                }
            }
            Constants.REQUEST_CODES.GROUP_CHECKOUT -> {
                if (resultCode == Activity.RESULT_OK) {
                    gameManager.players.sortByDescending(Player::getBalance)
                    recyclerView.adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.checkout -> {
                goToGroupCheckout()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun goToGroupCheckout() {
        val intent = Intent(this, GroupCheckoutActivity::class.java)
        startActivityForResult(intent, Constants.REQUEST_CODES.GROUP_CHECKOUT)
    }

    private fun handleSingleCheckout(player: Player) {
        gameManager.getPlayer(player.id).checkout = player.checkout
        recyclerView.adapter.notifyItemChanged((recyclerView.adapter as RecyclerViewAdapter).expandedPosition)

    }

    private fun handlePlayerBuyIn(player: Player, buyIn: BuyIn) {
        gameManager.getPlayer(player.id).buyIn(buyIn)
        recyclerView.adapter.notifyItemChanged((recyclerView.adapter as RecyclerViewAdapter).expandedPosition)
    }

    private fun handleNewPlayer(player: Player) {
        gameManager.addPlayer(player)
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
    }

    private fun openNewPlayerDialog(fab: FloatingActionButton) {
        val intent = Intent(this, NewPlayerActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, fab, getString(R.string.transition_new_player))
        startActivityForResult(intent, Constants.REQUEST_CODES.NEW_PLAYER, options.toBundle())
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set data
        val mDataSet = gameManager.players
        val mAdapter = RecyclerViewAdapter(this, mDataSet, buyInClick, singleCheckoutClick)
        recyclerView.adapter = mAdapter
    }

    val buyInClick: (View, Player) -> Unit = { view, player ->
        val intent = Intent(this, BuyInActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view, getString(R.string.transition_buyin))
        intent.putExtra(Constants.INTENT_EXTRAS_KEYS.PLAYER, player)
        startActivityForResult(intent, Constants.REQUEST_CODES.BUY_IN, options.toBundle())
    }

    val singleCheckoutClick: (View, Player) -> Unit = { view, player ->
        val intent = Intent(this, SingleCheckoutActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view, getString(R.string.transition_checkout))
        intent.putExtra(Constants.INTENT_EXTRAS_KEYS.PLAYER, player)
        startActivityForResult(intent, Constants.REQUEST_CODES.SINGLE_CHECKOUT, options.toBundle())
    }


}
