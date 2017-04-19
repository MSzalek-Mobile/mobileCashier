package com.mszalek.hajsolicznik.ui

/**
 * Created by Marcinus on 2016-12-04.
 */

class Constants() {

    object REQUEST_CODES {
        val NEW_PLAYER = 1
        val BUY_IN = 2
        val SINGLE_CHECKOUT = 3
        val GROUP_CHECKOUT = 4
    }

    object RESPONSE_EXTRA_KEYS {
        val NEW_PLAYER = "createdPlayer"
        val BUY_INED_PLAYER = "buyInedPlayer"
        val BUY_IN = "buyIn"
        val CHECKOUTED_PLAYER = "checkoutedPlayer"
    }

    object INTENT_EXTRAS_KEYS {
        val PLAYER = "extraPlayerKey"
    }
}