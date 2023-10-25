package org.teamvoided.packed.api

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import org.teamvoided.packed.items.PackItem

/**
 * A simple `Inventory` implementation with only default methods + an item list getter.
 *
 * Originally by Juuz
 */
@Suppress("unused")
interface InvImpl : Inventory {

    val items: DefaultedList<ItemStack>

    override fun size(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        for (i in 0 until size()) {
            val stack = getStack(i)
            if (!stack.isEmpty) {
                return false
            }
        }
        return true
    }


    override fun getStack(slot: Int): ItemStack {
        return items[slot]
    }


    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack {
        return Inventories.removeStack(items, slot)
    }


    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > stack.maxCount) {
            stack.count = stack.maxCount
        }
    }

    override fun clear() {
        items.clear()
    }

    override fun markDirty() {
        // Override if you want behavior.
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean = true


    override fun isValid(slot: Int, stack: ItemStack): Boolean = stack.item !is PackItem

    fun sort(type: SortType = SortType.NORMAL){
        this.markDirty()
    }
    enum class SortType { NORMAL, REVERSED, SPECIAL }

    companion object {
        fun of(items: DefaultedList<ItemStack>): InvImpl {
            return object : InvImpl {
                override val items: DefaultedList<ItemStack>
                    get() = items

            }
        }

        fun ofSize(size: Int): InvImpl {
            return of(DefaultedList.ofSize(size, ItemStack.EMPTY))
        }
    }
}
