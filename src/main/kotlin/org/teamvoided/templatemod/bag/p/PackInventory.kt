package org.teamvoided.templatemod.bag.p

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import kotlin.Boolean
import kotlin.Int

interface PackInventory : Inventory {
    val items: DefaultedList<ItemStack>
    override fun size(): Int = items.size
    override fun isEmpty(): Boolean {
        for (i in 0 until size()) if (!getStack(i).isEmpty) return false
        return true
    }

    override fun getStack(slot: Int): ItemStack = items[slot]
    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(items, slot)
    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > stack.maxCount) stack.count = stack.maxCount
    }

    override fun clear() = items.clear()
    override fun markDirty() {}
    override fun canPlayerUse(player: PlayerEntity): Boolean = true

    companion object {
        fun of(items: DefaultedList<ItemStack>): PackInventory = { items } as PackInventory
        fun ofSize(size: Int): PackInventory = of(DefaultedList.ofSize(size, ItemStack.EMPTY))

    }
}
