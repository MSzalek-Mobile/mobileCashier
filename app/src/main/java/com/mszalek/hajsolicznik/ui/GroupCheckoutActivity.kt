package com.mszalek.hajsolicznik.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.GameManager
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_group_checkout.*
import kotlinx.android.synthetic.main.row_group_checkout.view.*

class GroupCheckoutActivity : AppCompatActivity() {

    val gameManager = GameManager.singleton.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_checkout)
        setSupportActionBar(toolbar)
        initRows()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_group_checkout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_group_checkout_confirm -> {
                performCheckout()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun performCheckout() {
        if (validateRows()) {
            forEachChild { view -> gameManager.getPlayer((view.tag as Player).id).checkout = view.etPlayerCheckout.text.toString().toInt() }
            setResult(Activity.RESULT_OK)
            if (gameManager.getTotalBalance() == 0) {
                finish()
            } else {
                showBalanceError()
            }
        }
    }

    private fun showBalanceError() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Coś poszło nie tak")
        val stringResId = if (gameManager.getTotalBalance() < 0) R.string.group_checkout_balance_dialog_msg_too_little_money else R.string.group_checkout_balance_dialog_msg_too_much_money
        dialog.setMessage(getString(stringResId, Math.abs(gameManager.getTotalBalance())))
        dialog.setPositiveButton("Dobra, ogarniemy", null)
        dialog.show()

    }

    private fun initRows() {
        gameManager.players.forEach {
            val view = layoutInflater.inflate(R.layout.row_group_checkout, layout_players, false)
            view.tag = it
            view.til_checkout.hint = it.name
            if (it.checkout != null) {
                view.etPlayerCheckout.setText(it.checkout.toString())
            }
            layout_players.addView(view)
        }
    }

    private fun validateRows(): Boolean {
        var areValid = true
        forEachChild { view ->
            if (!validateRow(view)) {
                areValid = false
            }
        }
        return areValid
    }

    private fun validateRow(view: View): Boolean {
        return TilValidator(view.til_checkout, view.etPlayerCheckout).validate()
    }


    private fun forEachChild(function: (View) -> Unit) {
        var index = 0
        while (index < layout_players.childCount) {
            val view = layout_players.getChildAt(index)
            function(view)
            index++
        }
    }

}
