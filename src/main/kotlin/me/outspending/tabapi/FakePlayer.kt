package me.outspending.tabapi

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.papermc.paper.adventure.PaperAdventure
import me.outspending.tabapi.textures.Texture
import net.kyori.adventure.text.Component
import net.minecraft.Optionull
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.server.dedicated.DedicatedPlayerList
import net.minecraft.server.dedicated.DedicatedServer
import net.minecraft.server.level.ClientInformation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.GameType
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_20_R3.CraftServer
import java.util.*

data class FakePlayer internal constructor(
    val serverPlayer: ServerPlayer,
    val uniqueID: UUID,
    val texture: Texture
) {
    companion object {
        private val dedicatedServer: DedicatedServer = (Bukkit.getServer() as CraftServer).server

        fun create(name: String, uuid: UUID, texture: Texture): FakePlayer {
            val gameProfile = GameProfile(uuid, name)
            texture.addTexture(gameProfile.properties)

            val serverPlayer = ServerPlayer(dedicatedServer, dedicatedServer.overworld(), gameProfile, ClientInformation.createDefault())
            return FakePlayer(serverPlayer, uuid, texture)
        }

        fun create(name: String, texture: Texture) = create(name, UUID.randomUUID(), texture)
    }
}
