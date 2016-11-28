package com.mszalek.hajsolicznik.data.models

import java.io.Serializable
import java.util.*

/**
 * Created by Marcinus on 2016-09-07.
 */
class Player(name: String) : Serializable {

    val buyIns: MutableList<BuyIn> = LinkedList()
    val name: String
    var checkout: Int = 0

    init {
        this.name = name
    }

    fun buyIn(buyIn: BuyIn) {
        buyIns.add(buyIn)
    }

    fun buyIn(value: Int, isCash: Boolean = true) {
        buyIn(BuyIn(value, isCash))
    }

    fun getSumOfTotalBuyIns() : Int {
        return buyIns.sumBy { it.value }
    }

    fun getSumOfCashBuyIns(): Int {
        return buyIns.filter { it.isCash }.sumBy { it.value }
    }

    fun getSumOfDebtBuyIns(): Int {
        return buyIns.filter { !it.isCash }.sumBy { it.value }
    }

    fun getBalance(): Int {
        return checkout - getSumOfTotalBuyIns()
    }

    fun isInDebt(): Boolean {
        return getSumOfDebtBuyIns() > checkout
    }

    /**
     * How much did the player took out from case
     */
    fun getCashCheckout(): Int {
        val moneyTakenAboveDebtBuyIns = checkout - getSumOfDebtBuyIns()
        val moneyTakenFromCase = if (moneyTakenAboveDebtBuyIns > 0) {
            moneyTakenAboveDebtBuyIns
        } else {
            0
        }
        return moneyTakenFromCase
    }
}