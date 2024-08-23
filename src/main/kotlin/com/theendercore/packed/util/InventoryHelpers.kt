package com.theendercore.packed.util

import com.theendercore.packed.items.BackPackItem.Companion.makeBackpackScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

fun DefaultedList<ItemStack>.toCollectedMap(): MutableMap<ItemStack, Int> {
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

fun Iterable<ItemStack>.toCollectedStacks(): MutableList<ItemStack> {
    val filteredItems = this.filterNot(ItemStack::isEmpty)
    val collectedItems = mutableListOf<ItemStack >()
    for (item in filteredItems) {
        val exists = collectedItems.find { it.strictMatch(item) }
        if (exists != null) exists.increment(item.count)
        else collectedItems.add(item)
    }
    return collectedItems
}

fun PlayerEntity.openBackpack(stack: ItemStack): Boolean =
    ifRun(stack.isBackpack()) { this.openHandledScreen(makeBackpackScreen(stack)) }

