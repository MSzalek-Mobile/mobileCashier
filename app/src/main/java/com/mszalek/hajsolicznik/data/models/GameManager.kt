package com.mszalek.hajsolicznik.data.models

import java.util.*

/**
 * Created by Marcinus on 2016-10-31.
 */
class GameManager(
        val players: MutableList<Player> = LinkedList()) {

    var idCounter = 0

    object singleton {
        val instance = GameManager()
    }

    init {
        //TEMPORARY:
        addPlayer(Player(++idCounter, "Kocioł"))
        addPlayer(Player(++idCounter, "Rafał"))
        addPlayer(Player(++idCounter, "Monty"))
        addPlayer(Player(++idCounter, "Szałek"))
        addPlayer(Player(++idCounter, "Patryk"))

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

    fun getPlayer(id: Int): Player {
        return players.single { it.id == id }
    }

    fun addPlayer(player: Player) {
        player.id = ++idCounter
        players.add(player)
    }

    /**
     * Should always be 0, if not, somebody fucked up
     */
    fun getTotalBalance(): Int {
        return players.sumBy(Player::getBalance)
    }

    fun getCashInCase(): Int {
        return players.sumBy{it.getSumOfCashBuyIns() - it.getCashCheckout()}
    }

    fun isAnyPlayerInDebt(): Boolean {
        return players.any(Player::isInDebt)
    }

    fun haveAllPlayersCheckedOut(): Boolean {
        return players.all { it.checkout != null }
    }


}