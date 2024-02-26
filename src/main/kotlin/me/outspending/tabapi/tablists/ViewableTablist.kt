package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import me.outspending.tabapi.Updateable
import me.outspending.tabapi.Viewable
import me.outspending.tabapi.getConnection
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import org.bukkit.entity.Player
import java.util.*

class ViewableTablist : ArrayTablist(), Viewable, Updateable {
    private val viewers: MutableSet<Player> = mutableSetOf()

    override fun addViewer(player: Player) {
        if (viewers.contains(player)) return

        val entries = slots.map { it.packetEntry }
        val enumSet: EnumSet<ClientboundPlayerInfoUpdatePacket.Action> = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED)

        val connection = player.getConnection()
        connection.send(ClientboundPlayerInfoUpdatePacket(enumSet, entries))

        viewers.add(player)
        updateTablist()
    }

    override fun removeViewer(player: Player) {
        if (!viewers.contains(player)) return

        viewers.remove(player)
        deleteSlots(slots)
    }

    override fun clearViewers() {
        viewers.forEach { removeViewer(it) }
    }

    override fun updateTablist() = updateSlots(slots)

    fun updateSlot(slot: Slot) = updateSlots(listOf(slot))

    override fun updateSlots(slots: Collection<Slot>) {
        val entries = slots.map { it.packetEntry }
        val enumSet = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)

        for (player in viewers) {
            val connection = player.getConnection()
            connection.send(ClientboundPlayerInfoUpdatePacket(enumSet, entries))
        }
    }

    fun deleteSlot(slot: Slot) = deleteSlots(listOf(slot))

    override fun deleteSlots(slots: Collection<Slot>) {
        val uuids = slots.map { it.fakePlayer.uniqueID }

        for (player in viewers) {
            val connection = player.getConnection()
            connection.send(ClientboundPlayerInfoRemovePacket(uuids))
        }
    }

    fun setSlotDisplayName(slot: Slot, displayName: Component) {
        setSlot(slot, displayName)
        updateSlot(slot)
    }
}