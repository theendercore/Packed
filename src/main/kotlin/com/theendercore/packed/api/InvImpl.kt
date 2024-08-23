package com.theendercore.packed.api

import com.theendercore.packed.items.PackItem
import com.theendercore.packed.util.toCollectedStacks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import kotlin.math.min

/**
 * A simple `Inventory` implementation with only default methods + an item list getter.
 *
 * Originally by Juuz
 */
@Suppress("unused")
interface InvImpl : Inventory {

    val stacks: DefaultedList<ItemStack>

    override fun size(): Int = stacks.size

    override fun isEmpty(): Boolean {
        for (i in 0 until size()) {
            val stack = getStack(i)
            if (!stack.isEmpty) return false
        }
        return true
    }


    override fun getStack(slot: Int): ItemStack {
        return stacks[slot]
    }


    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(stacks, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack {
        return Inventories.removeStack(stacks, slot)
    }


    override fun setStack(slot: Int, stack: ItemStack) {
        stacks[slot] = stack
        if (stack.count > stack.maxCount) {
            stack.count = stack.maxCount
        }
    }

    override fun clear() = stacks.clear()

    override fun markDirty() {
        // Override if you want behavior.
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean = true


    override fun isValid(slot: Int, stack: ItemStack): Boolean = stack.item !is PackItem

    fun sort(type: SortType = SortType.NORMAL) {
        val sortedItems = stacks.toCollectedStacks().toSortedMap(type.getSort())
            .flatMap { (item, count) ->
                val itemCounts = mutableListOf<ItemStack>()
                if (count <= 64) {
                    itemCounts.add(item.copyWithCount(count))
                } else {
                    var remaining = count
                    while (remaining > 0) {
                        itemCounts.add(item.copyWithCount(min(remaining, 64)))
                        remaining -= 64
                    }
                }
                itemCounts
            }
        stacks.clear()
        sortedItems.forEachIndexed(stacks::set)
        markDirty()
    }


//    companion object {
//        fun of(items: DefaultedList<ItemStack>): InvImpl {
//            return object : InvImpl {
//                override val stacks: DefaultedList<ItemStack>
//                    get() = items
//
//            }
//        }
//
//        fun ofSize(size: Int): InvImpl {
//            return of(DefaultedList.ofSize(size, ItemStack.EMPTY))
//        }
//    }
}
