package com.theendercore.packed_up.util

import com.theendercore.packed_up.items.BackPackItem.Companion.makeBackpackScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

fun Iterable<ItemStack>.toCollectedStacks(): MutableList<ItemStack> {
    val filteredItems = this.filterNot(ItemStack::isEmpty)
    val collectedItems = mutableListOf<ItemStack>()
    for (item in filteredItems) {
        val exists = collectedItems.find { it.strictMatch(item) }
        if (exists != null) exists.count += item.count
        else collectedItems.add(item)
    }
    return collectedItems
}

fun PlayerEntity.openBackpack(stack: ItemStack): Boolean =
    ifRun(stack.isBackpack()) { this.openHandledScreen(makeBackpackScreen(stack)) }

