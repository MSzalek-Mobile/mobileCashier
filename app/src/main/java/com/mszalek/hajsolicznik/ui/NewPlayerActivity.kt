package com.mszalek.hajsolicznik.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mszalek.hajsolicznik.R
import kotlinx.android.synthetic.main.activity_buy_in.*

class NewPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_player)
        btnCancel.setOnClickListener { dismiss() }
        btnConfirm.setOnClickListener {
            setResult(1)
            dismiss()
        }
        layoutBackground.setOnClickListener { dismiss() }
    }

    private fun dismiss() {
        finishAfterTransition()
    }
}
