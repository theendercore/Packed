package org.teamvoided.packed.compat

import dev.emi.trinkets.api.TrinketsApi
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity
import org.teamvoided.packed.client.TrinketPackRenderer
import org.teamvoided.packed.init.PaItems
import org.teamvoided.packed.items.PackItem

object Trinkets{
    fun handleTrinkets(player: ServerPlayerEntity): Boolean {
        val comp = TrinketsApi.getTrinketComponent(player)
        if (comp.isPresent) {
            comp.get().allEquipped.forEach {
                val stack = it.right
                if (stack.item is PackItem) {
                    PaItems.PACK.openPack(stack, player)
                    return true
                }
            }
        }
        return false
    }

    fun shouldRenderTrinket(entity: LivingEntity): Boolean {
        val comp = TrinketsApi.getTrinketComponent(entity)
        if (comp.isPresent) {
            comp.get().allEquipped.forEach {
                if (it.right.item is PackItem) return true
            }
        }
        return false
    }

    fun initClient() {
        TrinketRendererRegistry.registerRenderer(PaItems.PACK, TrinketPackRenderer())

    }
}