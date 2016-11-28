package com.mszalek.hajsolicznik

import com.mszalek.hajsolicznik.data.models.Player
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by Marcinus on 2016-09-07.
 */
class PlayerTest {

    @Test
    fun checkoutBiggerThanBuyIns_givingNegativeBalance() {
        val player = Player("name")
        player.populateByuIns(intArrayOf(10,20,30))

        player.checkout = 150

        assertEquals(90, player.getBalance())
    }

    @Test
    fun debtBuyIn_givingProperTotals() {
        val player = Player("")
        player.buyIn(50, false)

        assertEquals(-50, player.getBalance())
        assertEquals(50, player.getSumOfDebtBuyIns())
        assertEquals(50, player.getSumOfTotalBuyIns())
        assertEquals(0, player.getSumOfCashBuyIns())
        assertEquals(0, player.getCashCheckout())
        assertTrue(player.isInDebt())
    }

    @Test
    fun debtBuyIn_checkoutLess() {
        val player = Player("")

        player.buyIn(50, false)
        player.checkout = 20

        assertEquals(-30, player.getBalance())
        assertEquals(50, player.getSumOfDebtBuyIns())
        assertEquals(50, player.getSumOfTotalBuyIns())
        assertEquals(0, player.getSumOfCashBuyIns())
        assertEquals(0, player.getCashCheckout())
        assertTrue(player.isInDebt())
    }

    @Test
    fun debtBuyIn_checkoutMore() {
        val player = Player("")

        player.buyIn(50, false)
        player.checkout = 70

        assertEquals(20, player.getBalance())
        assertEquals(50, player.getSumOfDebtBuyIns())
        assertEquals(50, player.getSumOfTotalBuyIns())
        assertEquals(0, player.getSumOfCashBuyIns())
        assertEquals(20, player.getCashCheckout())
        assertFalse(player.isInDebt())
    }


    fun Player.populateByuIns(buyIns: IntArray) {
        buyIns.forEach { this.buyIn(it) }
    }
}