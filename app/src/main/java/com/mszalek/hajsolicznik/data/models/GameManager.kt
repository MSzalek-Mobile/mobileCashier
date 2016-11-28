package com.mszalek.hajsolicznik.data.models

import java.util.*

/**
 * Created by Marcinus on 2016-10-31.
 */
class GameManager(
        val players: MutableList<Player> = LinkedList()) {

    init {
        addPlayer(Player("Kocioł"))
        addPlayer(Player("Rafał"))
        addPlayer(Player("Monty"))
        addPlayer(Player("Szałek"))
        addPlayer(Player("Patryk"))

        players[0].buyIn(50)
        players[1].buyIn(50)
        players[1].buyIn(100, false)
        players[2].buyIn(20)
        players[3].buyIn(20)
        players[3].buyIn(20)
        players[3].buyIn(20)
        players[3].buyIn(80, false)
        players[4].buyIn(50, false)
        players[4].buyIn(50, false)
        players[4].buyIn(50, false)
        players[4].buyIn(50, false)
    }

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun getTotalChips(): Int {
        return players.sumBy(Player::getBalance)
    }

    fun getCashInCase(): Int {
        return players.sumBy{it.getSumOfCashBuyIns() - it.getCashCheckout()}
    }

    fun isAnyPlayerInDebt(): Boolean {
        return players.any(Player::isInDebt)
    }
}