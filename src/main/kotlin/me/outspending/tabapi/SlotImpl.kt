package me.outspending.tabapi

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.minecraft.Optionull
import net.minecraft.network.chat.RemoteChatSession
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.world.level.GameType
import org.bukkit.entity.Player
import org.checkerframework.common.value.qual.IntRange
import java.util.*

class SlotImpl internal constructor(
    private val slotIndex: @IntRange(from = 0L, to = 19L) Int,
    private val displayText: Component
) : Slot {
    companion object {
        private val UPDATE_ENUM_SET = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)
    }

    private val fakePlayer = FakePlayer.createEmpty()
    private val serverPlayer = fakePlayer.serverPlayer

    override fun clear() {
        setDisplayText(Component.empty())
    }

    override fun setDisplayText(displayText: Component) {
        serverPlayer.listName = PaperAdventure.asVanilla(displayText)
    }

    override fun updateSlot(player: Player) {
        player.getConnection().send(ClientboundPlayerInfoUpdatePacket(UPDATE_ENUM_SET, getPacketEntry()))
    }

    override fun updateSlot(players: List<Player>) = players.forEach { updateSlot(it) }

    override fun getPacketEntry(): ClientboundPlayerInfoUpdatePacket.Entry =
        ClientboundPlayerInfoUpdatePacket.Entry(serverPlayer.uuid, serverPlayer.gameProfile, true, 0, GameType.DEFAULT_MODE, serverPlayer.tabListDisplayName, Optionull.map(serverPlayer.chatSession, RemoteChatSession::asData))
    override fun getFakePlayer(): FakePlayer = fakePlayer
    override fun getSlotIndex(): Int = slotIndex
    override fun getDisplayText(): Component = displayText
}