package me.outspending.tabapi

import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import org.bukkit.entity.Player
import org.checkerframework.common.value.qual.IntRange

interface Slot {
    companion object {
        fun create(slot: @IntRange(from = 0L, to = 19L) Int, displayText: Component): Slot {
            return SlotImpl(slot, displayText)
        }

        fun empty(index: @IntRange(from = 0L, to = 19L) Int): Slot = SlotImpl(index, Component.empty())
    }

    fun clear()

    fun setDisplayText(displayText: Component)

    fun updateSlot(player: Player)
    fun updateSlot(players: List<Player>)

    fun getPacketEntry(): ClientboundPlayerInfoUpdatePacket.Entry
    fun getFakePlayer(): FakePlayer
    fun getSlotIndex(): Int
    fun getDisplayText(): Component
}