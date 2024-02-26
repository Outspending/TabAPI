package me.outspending.tabapi

import org.bukkit.entity.Player

interface Viewable {
    fun addViewer(player: Player)

    fun removeViewer(player: Player)

    fun clearViewers()
}
