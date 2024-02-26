package me.outspending.tabapi

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import me.outspending.tabapi.tablists.ViewableTablist
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

lateinit var instance: JavaPlugin

class TabAPI : JavaPlugin(), Listener {
    private lateinit var customTab: ViewableTablist

    private var jumpAmount: Int = 0

    override fun onEnable() {
        instance = this
        customTab = ViewableTablist()

        val time = measureTime {
            customTab.addSlot(Slot.create("0", Component.text("Jump Amount: 0")))
        }
        println("Tablist addSlot finished in $time")

        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val time = measureTime {
            customTab.addViewer(e.player)
        }
        println("Tablist addViewer finished in $time")
    }

    @EventHandler
    fun onPlayerLeave(e: PlayerQuitEvent) {
        val time = measureTime {
            customTab.removeViewer(e.player)
        }
        println("Tablist removeViewer finished in $time")
    }

    @EventHandler
    fun onJump(e: PlayerJumpEvent) {
        jumpAmount++

        val slot = customTab.getSlot(0) ?: return
        customTab.setSlotDisplayName(slot, Component.text(jumpAmount))
    }
}
