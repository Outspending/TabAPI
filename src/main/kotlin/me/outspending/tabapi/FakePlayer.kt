package me.outspending.tabapi

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.papermc.paper.adventure.PaperAdventure
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
    val uniqueID: UUID
) {
    companion object {
        // These will be moved
        private const val BLACK_VALUE: String =
            "ewogICJ0aW1lc3RhbXAiIDogMTY4MzM0NTk1ODU0NSwKICAicHJvZmlsZUlk" + "IiA6ICIzYjgwOTg1YWU4ODY0ZWZlYjA3ODg2MmZkOTRhMTVkOSIsCiAgInBy" + "b2ZpbGVOYW1lIiA6ICJLaWVyYW5fVmF4aWxpYW4iLAogICJzaWduYXR1cmVS" + "ZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIg" + "OiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQu" + "bmV0L3RleHR1cmUvZTgwZmMwNDNlYTgwMDNjOTBjYTEzOTUzYTAzNTY3NjAx" + "OTk2YTE3NDMyMzgyMWY2Y2QwOGRjZDQ1MDdiY2VlMiIsCiAgICAgICJtZXRh" + "ZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"
        private const val BLACK_SIGNATURE =
            "ml7x6W9cRrzxGHe6y4K2UQ5vm/houO9E+rk5mI8YauRNepcoi58dC91Pp8yQ" + "whAMR53lT0gPHes1rNZunUtviql27zaieHmkHw0hkasaiepiX9TvnW93dGYg" + "UiNqE7k05wd2uRmYyPQNONGCurQ98+1JKJBGhq9NR8n5p6D4W/YhHkjoIM9D" + "Ba+ENJroJRyvZ0IfQDwToAsZc4+aunIIm8nxmwZt1ujA6L6DNjbfaHF0JF7P" + "b+rDPbjqylQ2uSvneLlkouPzoWgF+rB6WbwSj1yburF+Ii1nzZNZUYujYRnU" + "tgDViXyiFf33ThTmhpoD2l2kVCsu6whAeFyQauLaNMsCj/hqSZmAm6pWKjhV" + "nPAuJfSjY64uwrtOwLtbuGHFOFgA5Gf7q98d50M2Fi+zYfkEjAUW9HAVvBIk" + "AE88LQZU4rte4xS5mr4CnqrZVZ1VOzz1BR0DjitmJTzOLkgUpIeKj/6jAvyA" + "/q7RyHJ6hiGlV3OUyOtFNWgmQ9Zefd35SPik0DTeBaNzo6RmdpamlzDqjJzf" + "nAtfwHZrTs02e/NRgeCqKmkDyVvkoop8u/AqztD+cnhTIg47R1DX4nkMxRKt" + "5lFMVJS50DcNogLQitIvC/8ziZa+Q+S+9xocFLP9hRFj7savi1ntfhGxL24L" + "IDji0ptsAgF3bdDVLyz/+iE="
        private val BLACK_TEXTURE: Property = Property("textures", BLACK_VALUE, BLACK_SIGNATURE)

        private val dedicatedServer: DedicatedServer = (Bukkit.getServer() as CraftServer).server


        fun create(name: String, uuid: UUID): FakePlayer {
            val gameProfile = GameProfile(uuid, name)
            gameProfile.properties.put("textures", BLACK_TEXTURE)

            val serverPlayer = ServerPlayer(dedicatedServer, dedicatedServer.overworld(), gameProfile, ClientInformation.createDefault())
            return FakePlayer(serverPlayer, uuid)
        }

        fun create(name: String) = create(name, UUID.randomUUID())

        fun createEmpty() = create("", UUID.randomUUID())
    }
}
