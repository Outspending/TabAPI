package me.outspending.tabapi

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.checkerframework.common.value.qual.IntRange

interface Column {

    companion object {
        fun create(): Column = ColumnImpl()
    }

    fun updateSlots(players: List<Player>)
    fun updateSlots(player: Player)

    fun setSlot(slot: @IntRange(from = 0L, to = 19L) Int, display: Component): Slot
    fun setSlot(slot: Slot): Slot

    fun clearSlot(slot: @IntRange(from = 0L, to = 19L) Int): Slot

    fun getSlot(slot: @IntRange(from = 0L, to = 19L) Int): Slot

    fun getAllSlots(): List<Slot>

}