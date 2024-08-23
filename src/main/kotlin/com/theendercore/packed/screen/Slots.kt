package com.theendercore.packed.screen

import com.theendercore.packed.util.isBackpack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class BackpackPlayerSlot(inventory: Inventory, i: Int, j: Int, k: Int) : Slot(inventory, i, j, k) {
    override fun canTakeItems(playerEntity: PlayerEntity): Boolean =
        super.canTakeItems(playerEntity) && !stack.isBackpack()
}

class BackpackSlot(inventory: Inventory, i: Int, j: Int, k: Int) : Slot(inventory, i, j, k) {
    override fun canInsert(stack: ItemStack): Boolean = !stack.isBackpack() && stack.item.canBeNested()
}
