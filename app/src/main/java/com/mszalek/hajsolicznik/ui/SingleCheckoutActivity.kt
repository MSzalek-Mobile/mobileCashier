package com.mszalek.hajsolicznik.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.activity_checkout.*

class SingleCheckoutActivity : AppCompatActivity() {

    var player: Player? = null
    var hasEnteredCheckout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        player = intent.getSerializableExtra(Constants.INTENT_EXTRAS_KEYS.PLAYER) as Player
        btnCancel.setOnClickListener { close() }
        btnConfirm.setOnClickListener {
            if (!hasEnteredCheckout) {
                if (isInputValid()) {
                    hasEnteredCheckout = true
                    player?.checkout = et_checkout.text.toString().toInt()
                    showCheckoutSummary()
                }
            } else {
                close()
            }
        }
        layoutBackground.setOnClickListener { close() }
    }

    private fun showCheckoutSummary() {
        with(player as Player) {
            til_checkout.visibility = GONE
            layout_details.visibility = VISIBLE

            tvBuyIns.text = getString(R.string.checkout_msg_buyins, getSumOfTotalBuyIns(), getSumOfDebtBuyIns())
            tvCheckouts.text = getString(R.string.checkout_msg_checkouts, checkout)
            tvCashCheckout.text = getString(R.string.checkout_msg_cash_checkout, getCashCheckout())
            tvBalance.text = getString(R.string.checkout_msg_balance, getBalance())
            adaptDebtSummary(this)

            btnCancel.visibility = GONE
        }
    }

    private fun adaptDebtSummary(player: Player) {
        with(player) {
            if (getSumOfDebtBuyIns() == 0) {
                tvDebt.visibility = GONE
                tvDebtNext.visibility = GONE
            } else {
                val debtCheckout = checkout ?: 0 - getCashCheckout()
                val debt = getSumOfDebtBuyIns() - debtCheckout
                tvDebt.text = getString(R.string.checkout_msg_debt, debtCheckout, getSumOfDebtBuyIns())
                if (debt > 0) {
                    tvDebtNext.text = getString(R.string.checkout_msg_debt_next, debt)
                } else {
                    tvDebtNext.visibility = GONE
                }
            }
        }
    }

    private fun isInputValid(): Boolean {
        return TilValidator(til_checkout, et_checkout).validate()
    }

    fun close() {
        if (hasEnteredCheckout) {
            setResult()
        }
        finishAfterTransition()
    }

    private fun setResult() {
        val intent = Intent()
        intent.putExtra(Constants.RESPONSE_EXTRA_KEYS.CHECKOUTED_PLAYER, player)
        setResult(Activity.RESULT_OK, intent)
    }
}
