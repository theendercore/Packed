package com.theendercore.packed.compat

import com.theendercore.packed.init.PakItems
import com.theendercore.packed.items.PackItem
import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity

object Trinkets{
    fun handleTrinkets(player: ServerPlayerEntity): Boolean {
        val comp = TrinketsApi.getTrinketComponent(player)
        if (comp.isPresent) {
            comp.get().allEquipped.forEach {
                val stack = it.right
                if (stack.item is PackItem) {
                    PakItems.PACK.openPack(stack, player)
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
}