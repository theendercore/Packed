package com.theendercore.packed_up.inv

import com.theendercore.packed_up.util.defaultedList
import com.theendercore.packed_up.util.getBackpackContents
import com.theendercore.packed_up.util.setBackpackContents
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class BackpackInventory(val backpack: ItemStack, override val stacks: DefaultedList<ItemStack>) : ImplementedInventory {
    constructor(stack: ItemStack, size: Int) : this(stack, defaultedList(size))
    constructor(stack: ItemStack) : this(stack, stack.getBackpackContents()?.stacks?.size ?: 9)

    init {
        backpack.getBackpackContents()?.stacks?.forEachIndexed(stacks::set)
    }

    override fun markDirty() = backpack.setBackpackContents(stacks)
}
