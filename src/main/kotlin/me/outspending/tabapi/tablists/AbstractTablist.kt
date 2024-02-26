package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import net.kyori.adventure.text.Component

abstract class AbstractTablist {
    val slots: MutableList<Slot> = mutableListOf()

    abstract fun addSlot(slot: Slot)

    abstract fun removeSlot(slot: Slot)

    abstract fun setSlot(slot: Slot, listName: Component)

    abstract fun clearSlot(slot: Slot)
}
