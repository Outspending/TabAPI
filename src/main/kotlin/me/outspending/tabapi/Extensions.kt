package me.outspending.tabapi

import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.getConnection() = (player as CraftPlayer).handle.connection