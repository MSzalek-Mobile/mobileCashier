package com.mszalek.hajsolicznik.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.BuyIn
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_new_player.*

class NewPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_player)
        btnCancel.setOnClickListener { dismiss() }
        btnConfirm.setOnClickListener { maybeConfirm() }
        layoutBackground.setOnClickListener { dismiss() }
    }

    private fun maybeConfirm() {
        if (validateInputs()) {
            confirm()
        }
    }

    private fun confirm() {
        setPlayerResult(createPlayer())
        dismiss()
    }

    private fun setPlayerResult(player: Player) {
        val intent = Intent()
        intent.putExtra(Constants.RESPONSE_EXTRA_KEYS.NEW_PLAYER, player)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun createPlayer(): Player {
        val player = Player(getName())
        player.buyIn(BuyIn(getBuyIn()))
        return player
    }

    private fun dismiss() {
        finishAfterTransition()
    }

    private fun validateInputs(): Boolean {
        val isNameValid = isNameValid()
        val isBuyInValid = isBuyInValid()
        if (!isNameValid) {
            tilNewPlayer.error = "sdgsgdgsd"
        }
        if (!isBuyInValid) {
            tilBuyIn.error = "sdfsfdsdfsfd"
        }

        return isNameValid && isBuyInValid
    }

    private fun isNameValid(): Boolean {
        return !getName().isEmpty()
    }

    private fun isBuyInValid(): Boolean {
        return !getString(tilBuyIn).isEmpty() && getBuyIn() > 0
    }

    private fun getName(): String {
        return getString(tilNewPlayer).trim()
    }

    private fun getBuyIn(): Int {
        return getString(tilBuyIn).toInt()
    }

    private fun getString(til: TextInputLayout): String {
        return til.editText!!.text.toString()
    }

}
