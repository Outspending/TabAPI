package me.outspending.tabapi

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import me.outspending.tabapi.tablists.ViewableTablist
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.time.measureTime

lateinit var instance: JavaPlugin

class TabAPI : JavaPlugin(), Listener {
    private lateinit var customTab: ViewableTablist

    private var jumpAmount: Int = 0

    override fun onEnable() {
        instance = this
        customTab = ViewableTablist()

        val time = measureTime {
            customTab.addSlot(Slot.create("0", Component.text("Jump Amount: 0"), 500))
        }
        println("Tablist addSlot finished in $time")

        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        object : BukkitRunnable() {
                override fun run() {
                    customTab.addViewer(e.player)
                }
            }
            .runTaskLater(this, 5)
    }

    @EventHandler
    fun onPlayerLeave(e: PlayerQuitEvent) {
        customTab.removeViewer(e.player)
    }

    @EventHandler
    fun onJump(e: PlayerJumpEvent) {
        jumpAmount++

        val slot = customTab.getSlot(0) ?: return
        customTab.setSlotDisplayName(slot, Component.text(jumpAmount))
    }
}
