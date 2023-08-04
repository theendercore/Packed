package org.teamvoided.templatemod.screen.w.temp

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.teamvoided.templatemod.TemplateMod

class ScreenDataProvider( private val provider: NamedScreenHandlerFactory) : ExtendedScreenHandlerFactory {
    override fun createMenu(i: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity): ScreenHandler? =
       provider.createMenu(i, playerInventory, playerEntity)


    override fun getDisplayName(): Text =
        provider.displayName


    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) =
        TemplateMod.LOGGER.info(":)")
//        provider.writeScreenOpeningData(player, buf)

}