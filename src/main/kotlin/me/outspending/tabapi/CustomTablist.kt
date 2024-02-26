package me.outspending.tabapi

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface CustomTablist {
    companion object {
        fun create(name: String, amountOfColumns: Int = 4) = CustomTablistImpl(name, amountOfColumns)
    }

    fun addViewer(player: Player)
    fun removeViewer(player: Player)
    fun removeViewers(players: List<Player>)

    fun clearViewers()

    fun updateColumn(column: Int)
    fun updateSlot(slot: Slot)

    fun setSlot(column: Int, slot: Int, displayText: Component)
    fun clearSlot(column: Int, slot: Int)

    fun getSlot(column: Int, slot: Int): Slot
    fun getColumn(column: Int): Column

    fun getName(): String
    fun getViewers(): List<Player>
    fun getColumns(): Array<Column>
    fun getAllSlots(): List<Slot>
}