package me.outspending.tabapi.tablists

import me.outspending.tabapi.Slot
import me.outspending.tabapi.Updateable
import me.outspending.tabapi.Viewable
import me.outspending.tabapi.getConnection
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class ViewableTablist : ArrayTablist(), Viewable, Updateable {
    private val viewers: MutableSet<Player> = mutableSetOf()

    private fun hidePlayersInTablist(player: Player) {
        val uuids: List<UUID> = Bukkit.getOnlinePlayers().map { it.uniqueId }
        val connection = player.getConnection()

        connection.send(ClientboundPlayerInfoRemovePacket(uuids))
    }

    override fun addViewer(player: Player) {
        if (viewers.contains(player)) return
        viewers.add(player)

        val entries = slots.map { it.packetEntry }
        val enumSet: EnumSet<ClientboundPlayerInfoUpdatePacket.Action> =
            EnumSet.of(
                ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
                ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED
            )

        hidePlayersInTablist(player)

        val connection = player.getConnection()
        connection.send(ClientboundPlayerInfoUpdatePacket(enumSet, entries))

        updateTablist(listOf(player))
    }

    override fun removeViewer(player: Player) {
        if (!viewers.contains(player)) return

        viewers.remove(player)
        deleteSlots(slots)
    }

    override fun clearViewers() {
        viewers.forEach { removeViewer(it) }
    }

    override fun updateTablist() = updateSlots(slots, viewers)

    private fun updateTablist(players: Collection<Player>) = updateSlots(slots, players)

    private fun updateSlots(slots: Collection<Slot>, players: Collection<Player>) {
        val entries = slots.map { it.packetEntry }
        val enumSet = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)

        for (player in players) {
            val connection = player.getConnection()
            connection.send(ClientboundPlayerInfoUpdatePacket(enumSet, entries))

            sendHeaderFooter(player)
        }
    }

    override fun updateSlots(slots: Collection<Slot>) = updateSlots(slots, viewers)

    fun updateSlot(slot: Slot) = updateSlots(listOf(slot), viewers)

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
