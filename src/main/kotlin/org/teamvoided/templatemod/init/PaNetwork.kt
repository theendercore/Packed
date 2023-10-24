package org.teamvoided.templatemod.init

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.Item
import org.teamvoided.templatemod.TemplateMod.id
import org.teamvoided.templatemod.TemplateMod.log
import org.teamvoided.templatemod.TemplateMod.trinketsInstalled
import org.teamvoided.templatemod.items.PackItem

object PaNetwork {
    val OPEN_PACK_WITH_KEY = id("open_pack_with_key")

    fun initClient() {}
    fun initServer() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_PACK_WITH_KEY) { _, player, _, _, _ ->
            val inv = player.inventory
            if (trinketsInstalled) {
                //Handle trinkets
                log.info("handle trinkets")
            } else if (inv.containsAny(mutableSetOf(PaItems.PACK) as Set<Item>)) {

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