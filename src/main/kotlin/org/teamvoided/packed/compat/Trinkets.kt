package org.teamvoided.packed.compat

import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.server.network.ServerPlayerEntity
import org.teamvoided.packed.init.PaItems
import org.teamvoided.packed.items.PackItem

fun handleTrinkets(player: ServerPlayerEntity): Boolean{
    val opt = TrinketsApi.getTrinketComponent(player)
    if(opt.isPresent){
        val comp = opt.get()
        comp.allEquipped.forEach {
            val stack = it.right
            if (stack.item is PackItem) {
                PaItems.PACK.openPack(stack, player)
                return true
            }
        }
    }
    return false
}