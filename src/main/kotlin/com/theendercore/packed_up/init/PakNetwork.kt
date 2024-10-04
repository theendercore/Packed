package com.theendercore.packed_up.init

import com.theendercore.packed_up.PackedUp.id
import com.theendercore.packed_up.util.openBackpack
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.Item
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.payload.CustomPayload

object PakNetwork {

    fun init() {
        PayloadTypeRegistry.playC2S().register(OpenPackPayload.ID, OpenPackPayload.CODEC)
        ServerPlayNetworking.registerGlobalReceiver(OpenPackPayload.ID) msg@{ _, context ->
            val player = context.player() ?: return@msg
            val inv = player.inventory

//            if (trinketsInstalled && Trinkets.handleTrinkets(player)) return@msg
            if (!inv.containsAny(mutableSetOf(PakItems.PACK) as Set<Item>)) return@msg

            if (player.openBackpack(inv.armor[2])) return@msg
            if (player.openBackpack(inv.offHand[0])) return@msg

            player.inventory.main.forEach { if (player.openBackpack(it)) return@msg }
        }
    }

    object OpenPackPayload : CustomPayload {
        override fun getId(): CustomPayload.Id<out CustomPayload> = ID
        val ID = CustomPayload.Id<OpenPackPayload>(id("open_pack_with_key"))
        val CODEC = CustomPayload.create({ _, _ -> }, { _: PacketByteBuf -> OpenPackPayload })
    }
}
