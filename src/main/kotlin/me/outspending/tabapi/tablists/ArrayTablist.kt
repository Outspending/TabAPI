package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

open class ArrayTablist : AbstractTablist() {
    override fun sendHeaderFooter(player: Player) {
        val audience = Audience.audience(player)

        audience.sendPlayerListHeaderAndFooter(header, footer)
    }

    override fun addSlot(slot: Slot) {
        if (slots.contains(slot)) return

        slots.add(slot)
    }

    override fun removeSlot(slot: Slot) {
        if (!slots.contains(slot)) return

        slots.remove(slot)
    }

    override fun setSlot(slot: Slot, listName: Component) {
        slot.setTablistName(listName)
    }

    override fun clearSlot(slot: Slot) {
        slot.clearName()
    }

    fun getSlot(index: Int): Slot? {
        if (slots.size in 0..index) return null

        return slots[index]
    }

    fun getSlots(between: IntRange) = slots.subList(between.first, between.last)

    fun getAllSlots(): List<Slot> = slots
}
