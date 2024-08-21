package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.Packed.log
import com.theendercore.packed.Packed.trinketsInstalled
import com.theendercore.packed.compat.Trinkets
import com.theendercore.packed.items.PackItem
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.Item
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.payload.CustomPayload

object PaNetwork {

    fun init() {
        PayloadTypeRegistry.playC2S().register(OpenPackPayload.ID, OpenPackPayload.CODEC)
        ServerPlayNetworking.registerGlobalReceiver(OpenPackPayload.ID) msg@{ _, context ->
            val player = context.player() ?: return@msg
            val inv = player.inventory
            if (trinketsInstalled && Trinkets.handleTrinkets(player)) return@msg
            if (!inv.containsAny(mutableSetOf(PakItems.PACK) as Set<Item>)) return@msg

            if (!PakItems.PACK.openPack(inv.armor[2], player) && !PakItems.PACK.openPack(inv.offHand[0], player)) {
                player.inventory.main.forEach {
                    if (it.item is PackItem) {
                        log.info("Found item")
                        PakItems.PACK.openPack(it, player)
                        return@msg
                    }
                }
            }
        }
    }

    object OpenPackPayload : CustomPayload {
        override fun getId(): CustomPayload.Id<out CustomPayload> = ID
        val ID = CustomPayload.Id<OpenPackPayload>(id("open_pack_with_key"))
        val CODEC = CustomPayload.create({ _, _ -> }, { _: PacketByteBuf -> OpenPackPayload })
    }
}