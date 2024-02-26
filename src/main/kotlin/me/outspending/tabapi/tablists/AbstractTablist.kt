package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class AbstractTablist {
    open var header: Component = Component.empty()
    open var footer: Component = Component.empty()
    open val slots: MutableList<Slot> = mutableListOf()

    abstract fun sendHeaderFooter(player: Player)

    abstract fun addSlot(slot: Slot)

    abstract fun removeSlot(slot: Slot)

    abstract fun setSlot(slot: Slot, listName: Component)

    abstract fun clearSlot(slot: Slot)
}
