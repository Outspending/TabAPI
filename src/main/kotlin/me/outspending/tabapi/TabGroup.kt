package me.outspending.tabapi

import org.bukkit.entity.Player

class TabGroup(
    fromSlot: Int,
    toSlot: Int,
) {
    private val slots = fromSlot..toSlot
    private val members: MutableList<Player> = mutableListOf()

    fun addMember(player: Player) {
        members.add(player)
    }

    fun removeMember(player: Player) {
        members.remove(player)
    }

    fun clearMembers() {
        members.clear()
    }


}