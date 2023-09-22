package org.teamvoided.templatemod.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import org.teamvoided.templatemod.TemplateMod.BHANDLER
import org.teamvoided.templatemod.iInv
import org.teamvoided.templatemod.items.InventoryAPI

class BScreenHandler @JvmOverloads constructor(
    syncId: Int, playerInventory: PlayerInventory, inventory: iInv = iInv.ofSize(9),
) : ScreenHandler(BHANDLER, syncId) {
    private val inventory: iInv

    init {
        checkSize(inventory, 9)
        this.inventory = inventory
        inventory.onOpen(playerInventory.player)
        val gridX = 62
        val gridY = 17
        var i = 0
        for (row in 0..2) {
            for (col in 0..2) {
                addSlot(Slot(inventory, i, gridX + 18 * col, gridY + 18 * row))
                i++
            }
        }
        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)
    }

    override fun quickTransfer(player: PlayerEntity, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return inventory.canPlayerUse(player)
    }

    private fun addPlayerInventory(playerInventory: PlayerInventory) {
        for (i in 0..2) for (l in 0..8)
            addSlot(Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))

    }

    private fun addPlayerHotbar(playerInventory: PlayerInventory) {
        for (i in 0..8) addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
    }

    override fun close(player: PlayerEntity) {
        if (player is ServerPlayerEntity) {
            val item = player.getStackInHand(player.activeHand)
            val stack = InventoryAPI.findInvItem(item)
            stack?.setInventory(inventory)
        }
        super.close(player)
        inventory.onClose(player)
    }
}
