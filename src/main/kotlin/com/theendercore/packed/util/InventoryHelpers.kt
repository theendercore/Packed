package com.theendercore.packed.util

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
