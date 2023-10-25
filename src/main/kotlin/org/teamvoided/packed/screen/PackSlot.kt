package org.teamvoided.packed.screen

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import org.teamvoided.packed.items.PackItem

class PackSlot(inventory: Inventory, i: Int, j: Int, k: Int) : Slot(inventory, i, j, k) {
    override fun canInsert(stack: ItemStack): Boolean = stack.item !is PackItem && stack.item.canBeNested()
}
