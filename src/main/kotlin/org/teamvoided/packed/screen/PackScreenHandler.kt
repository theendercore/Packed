package org.teamvoided.packed.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import org.teamvoided.packed.api.InvImpl
import org.teamvoided.packed.init.PaScreens.PACK_HANDLER
import org.teamvoided.packed.items.PackItem.Companion.setInventory

class PackScreenHandler @JvmOverloads constructor(
    syncId: Int, playerInventory: PlayerInventory, private val inventory: InvImpl = InvImpl.ofSize(9),
    private val stack: ItemStack = ItemStack.EMPTY,
) : ScreenHandler(PACK_HANDLER, syncId) {

    init {
        checkSize(inventory, 9)
        inventory.onOpen(playerInventory.player)
        val gridX = 62
        val gridY = 17
        var i = 0
        for (row in 0..2) {
            for (col in 0..2) {
                addSlot(PackSlot(inventory, i, gridX + 18 * col, gridY + 18 * row))
                i++
            }
        }
        addPlayer(playerInventory)
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

    override fun canUse(player: PlayerEntity): Boolean = inventory.canPlayerUse(player)

    private fun addPlayer(pInv: PlayerInventory) {
        // Inv
        for (i in 0..2) for (l in 0..8) {
            val idx = l + i * 9 + 9
            addSlot(PackPlayerSlot(pInv, idx, 8 + l * 18, 84 + i * 18, stack))
        }
        // Hotbar
        for (i in 0..8) addSlot(PackPlayerSlot(pInv, i, 8 + i * 18, 142, stack))
    }

    override fun close(player: PlayerEntity) {
        if (player is ServerPlayerEntity && !stack.isEmpty) stack.setInventory(inventory)
        super.close(player)
        inventory.onClose(player)
    }

    override fun onButtonClick(player: PlayerEntity, id: Int): Boolean {
        return if (!BTN_IDS.contains(id)) false
        else {
            when (id) {
                1 -> inventory.sort(InvImpl.SortType.NORMAL)
                2 -> inventory.sort(InvImpl.SortType.REVERSED)
                else -> throw Error("Eyo how the tell did you get button $id and how is it on the list???")
            }
            true
        }
    }

    companion object {
        val BTN_IDS = intArrayOf(1, 2)
    }

}
