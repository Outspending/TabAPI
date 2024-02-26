package me.outspending.tabapi

import io.papermc.paper.adventure.PaperAdventure
import me.outspending.tabapi.textures.Texture
import net.kyori.adventure.text.Component
import net.minecraft.Optionull
import net.minecraft.network.chat.RemoteChatSession
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.world.level.GameType

class Slot private constructor(displayName: String, tabListName: Component = EMPTY_NAME, private var latency: Int = 0) {
    companion object {
        private val EMPTY_NAME: Component = Component.empty()

        fun create(displayName: String, tabListName: Component, latency: Int = 0) = Slot(displayName, tabListName, latency)

        fun create(displayName: String) = Slot(displayName)
    }

    val fakePlayer: FakePlayer = FakePlayer.create(displayName, Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzgwNTk0ZWE5YTg5ZjFhYmFlNzQ3NzRhZmU2MDhiZmVlZmIwMTNkZWU1YTM3OTYzM2ViM2QyZTBmMDgwMTUxNiJ9fX0="))
    private val serverPlayer = fakePlayer.serverPlayer

    val packetEntry: ClientboundPlayerInfoUpdatePacket.Entry
        get() =
            ClientboundPlayerInfoUpdatePacket.Entry(
                serverPlayer.uuid,
                serverPlayer.gameProfile,
                true,
                latency,
                GameType.DEFAULT_MODE,
                serverPlayer.tabListDisplayName,
                Optionull.map(serverPlayer.chatSession, RemoteChatSession::asData)
            )

    init {
        setTablistName(tabListName)
    }

    fun clearName() = setTablistName(EMPTY_NAME)

    fun setTablistName(displayText: Component) {
        serverPlayer.listName = PaperAdventure.asVanilla(displayText)
    }
}
