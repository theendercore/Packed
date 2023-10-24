package org.teamvoided.packed.init

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.Item
import org.teamvoided.packed.Packed.id
import org.teamvoided.packed.Packed.log
import org.teamvoided.packed.Packed.trinketsInstalled
import org.teamvoided.packed.compat.handleTrinkets
import org.teamvoided.packed.items.PackItem

object PaNetwork {
    val OPEN_PACK_WITH_KEY = id("open_pack_with_key")

    fun initClient() {}
    fun initServer() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_PACK_WITH_KEY) { _, player, _, _, _ ->
            val inv = player.inventory
            if (trinketsInstalled) if (handleTrinkets(player)) return@registerGlobalReceiver
            if (inv.containsAny(mutableSetOf(PaItems.PACK) as Set<Item>)) {
                if (!PaItems.PACK.openPack(inv.offHand[0], player)) {
                    player.inventory.main.forEach {
                        if (it.item is PackItem) {
                            log.info("Found item")
                            PaItems.PACK.openPack(it, player)
                            return@registerGlobalReceiver
                        }
                    }
                }
            }
        }

    }
}