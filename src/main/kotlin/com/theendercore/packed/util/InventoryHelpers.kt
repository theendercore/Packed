package com.theendercore.packed.util

import com.theendercore.packed.items.BackPackItem.Companion.makeBackpackScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

fun DefaultedList<ItemStack>.toCollectedStacks(): MutableMap<ItemStack, Int> {
    val filteredItems = this.filterNot(ItemStack::isEmpty)
        .groupBy({ it.copyWithCount(1) }, { it.count })
        .mapValues { it.value.sum() }
    val collectedItems = mutableMapOf<ItemStack, Int>()
    for ((item, count) in filteredItems) {
        val existingItem = collectedItems.entries.find { (existingItem, _) -> existingItem.strictMatch(item) }
        if (existingItem != null) existingItem.setValue(existingItem.value + count)
        else collectedItems[item] = count
    }
    return collectedItems
}


fun PlayerEntity.openBackpack(stack: ItemStack): Boolean {
    val backpackContents = stack.getBackpackContents()
    return if (backpackContents != null) {
        this.openHandledScreen(makeBackpackScreen(stack))
        true
    } else false
}
