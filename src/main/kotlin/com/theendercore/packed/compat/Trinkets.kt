package com.theendercore.packed.compat

import com.theendercore.packed.util.isBackpack
import com.theendercore.packed.util.openBackpack
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Pair
import kotlin.jvm.optionals.getOrNull

object Trinkets {
    fun handleTrinkets(player: ServerPlayerEntity): Boolean =
        player.getTrinkets()?.find { it.right.isBackpack() }?.let { player.openBackpack(it.right) } != null

    fun shouldRenderTrinket(entity: LivingEntity): Boolean =
        entity.getTrinkets()?.find { it.right.isBackpack() } != null

    private fun LivingEntity.getTrinkets(): MutableList<Pair<SlotReference, ItemStack>>? =
        TrinketsApi.getTrinketComponent(this).getOrNull()?.allEquipped
}