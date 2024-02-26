package me.outspending.tabapi

import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

private const val MAX_COLUMN_SLOTS = 20

class ColumnImpl internal constructor() : Column {
    private val slots: HashMap<Int, Slot> = HashMap()

    init {
        for (i in 0..MAX_COLUMN_SLOTS) {
            slots[i] = Slot.empty(i)
        }
    }

    override fun updateSlots(players: List<Player>) {
        val entries: List<ClientboundPlayerInfoUpdatePacket.Entry> = slots.values.map { it.getPacketEntry() }
        val enumSet = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)

        players.forEach { player ->
            val connection = player.getConnection()
            connection.send(ClientboundPlayerInfoUpdatePacket(enumSet, entries))
        }
    }

    override fun updateSlots(player: Player) = updateSlots(listOf(player))

    override fun setSlot(slot: Int, display: Component): Slot {
        val newSlot = Slot.create(slot, display)
        slots[slot] = newSlot

        return newSlot
    }

    override fun setSlot(slot: Slot): Slot {
        slots[slot.getSlotIndex()] = slot

        return slot
    }

    override fun clearSlot(slot: Int): Slot {
        val emptySlot = Slot.empty(slot)
        slots[slot] = emptySlot

        return emptySlot
    }

    override fun getSlot(slot: Int): Slot = slots[slot] ?: Slot.empty(slot)
    override fun getAllSlots(): List<Slot> = slots.values.toList()
}