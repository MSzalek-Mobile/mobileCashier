package com.mszalek.hajsolicznik.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.BuyIn
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_buy_in.*

class BuyInActivity : AppCompatActivity() {

    var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_in)
        player = intent.getSerializableExtra(Constants.INTENT_EXTRAS_KEYS.PLAYER) as Player
        btnCancel.setOnClickListener { dismiss() }
        btnConfirm.setOnClickListener {
            setResult()
            dismiss()
        }
        layoutBackground.setOnClickListener { dismiss() }
    }

    private fun setResult() {
        val intent = Intent()
        intent.putExtra(Constants.RESPONSE_EXTRA_KEYS.BUY_INED_PLAYER, player)
        intent.putExtra(Constants.RESPONSE_EXTRA_KEYS.BUY_IN, createBuyIn())
        setResult(Activity.RESULT_OK, intent)
    }

    private fun createBuyIn(): BuyIn {
        return BuyIn(inputBuyIn.text.toString().toInt(), !checkBox.isChecked)
    }

    private fun dismiss() {
        finishAfterTransition()
    }
}
