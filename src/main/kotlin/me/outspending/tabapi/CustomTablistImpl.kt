package me.outspending.tabapi

import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import org.bukkit.entity.Player
import java.util.EnumSet

class CustomTablistImpl(private val name: String, amountOfColumns: Int) : CustomTablist {
    companion object {
        val ADD_ENUM_SET = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED)
    }

    private val viewers: MutableList<Player> = mutableListOf()
    private val columns: Array<Column> = Array(amountOfColumns - 1) { Column.create() }

    private fun update(player: Player) {
        for (column in columns) {
            column.updateSlots(player)
        }
    }

    override fun addViewer(player: Player) {
        update(player)

        val connection = player.getConnection()
        val entries = getAllSlots().map { it.getPacketEntry() }

        connection.send(ClientboundPlayerInfoUpdatePacket(ADD_ENUM_SET, entries))
        viewers.add(player)
    }

    override fun removeViewers(players: List<Player>) {
        for (player in players) {
            if (!viewers.contains(player)) continue

            val connection = player.getConnection()
            val uuids = getAllSlots().map { it.getFakePlayer().uniqueID }

            connection.send(ClientboundPlayerInfoRemovePacket(uuids))
            viewers.remove(player)
        }
    }

    override fun removeViewer(player: Player) = removeViewers(listOf(player))
    override fun clearViewers() = removeViewers(viewers.toList())

    override fun updateColumn(column: Int) {
        columns[column - 1].updateSlots(viewers)
    }

    override fun updateSlot(slot: Slot) {
        slot.updateSlot(viewers)
    }

    override fun setSlot(column: Int, slot: Int, displayText: Component) {
        val columnSlot: Slot = getSlot(column, slot)
        columnSlot.setDisplayText(displayText)
        updateSlot(columnSlot)
    }

    override fun clearSlot(column: Int, slot: Int) {
        val columnSlot = getSlot(column, slot)
        columnSlot.clear()
        updateSlot(columnSlot)
    }

    override fun getSlot(column: Int, slot: Int): Slot = getColumn(column).getSlot(slot)
    override fun getColumn(column: Int): Column = columns[column]

    override fun getName(): String = name
    override fun getViewers(): List<Player> = viewers
    override fun getColumns(): Array<Column> = columns
    override fun getAllSlots(): List<Slot> = columns.flatMap { it.getAllSlots() }
}