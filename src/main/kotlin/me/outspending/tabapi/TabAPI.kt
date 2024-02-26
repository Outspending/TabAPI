package me.outspending.tabapi

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

lateinit var instance: JavaPlugin

class TabAPI : JavaPlugin(), Listener {
    private lateinit var customTab: CustomTablist

    private var jumpAmount: Int = 0

    override fun onEnable() {
        instance = this

        customTab = CustomTablist.create("test", 3)

        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        customTab.addViewer(e.player)
    }

    @EventHandler
    fun onJump(e: PlayerJumpEvent) {
        jumpAmount++
        println(jumpAmount)

        customTab.setSlot(0, 0, Component.text(jumpAmount))
    }
}
