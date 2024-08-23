package com.theendercore.packed.screen

import com.theendercore.packed.util.isBackpack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.screen.slot.Slot

class PackPlayerSlot(inventory: Inventory, i: Int, j: Int, k: Int) :
    Slot(inventory, i, j, k) {
    override fun canTakeItems(playerEntity: PlayerEntity): Boolean = !stack.isBackpack()
}
