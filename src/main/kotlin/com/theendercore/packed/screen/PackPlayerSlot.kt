package com.theendercore.packed.screen

import com.theendercore.packed.items.PackItem
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class PackPlayerSlot(inventory: Inventory, i: Int, j: Int, k: Int, private val pack: ItemStack) :
    Slot(inventory, i, j, k) {
    override fun canTakeItems(playerEntity: PlayerEntity): Boolean = stack.item !is PackItem && stack != pack

}
