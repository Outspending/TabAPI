package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class AbstractTablist {
    val slots: MutableList<Slot> = mutableListOf()

    abstract fun setHeader(header: Component)

    abstract fun setFooter(footer: Component)

    abstract fun sendHeaderFooter(player: Player)

    abstract fun addSlot(slot: Slot)

    abstract fun removeSlot(slot: Slot)

    abstract fun setSlot(slot: Slot, listName: Component)

    abstract fun clearSlot(slot: Slot)
}
