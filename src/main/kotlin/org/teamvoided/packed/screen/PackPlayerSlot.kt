package org.teamvoided.packed.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import org.teamvoided.packed.items.PackItem

class PackPlayerSlot(inventory: Inventory, i: Int, j: Int, k: Int, private val pack: ItemStack) :
    Slot(inventory, i, j, k) {
    override fun canTakeItems(playerEntity: PlayerEntity): Boolean = stack.item !is PackItem && stack != pack

}
