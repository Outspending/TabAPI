package me.outspending.tabapi

import org.bukkit.entity.Player

interface Updateable {

    fun updateTablist()

    fun updateSlots(slots: Collection<Slot>)

    fun deleteSlots(slots: Collection<Slot>)
}
