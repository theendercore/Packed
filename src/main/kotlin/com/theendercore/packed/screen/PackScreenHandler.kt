package com.theendercore.packed.screen

import com.theendercore.packed.Packed.log
import com.theendercore.packed.api.InvImpl
import com.theendercore.packed.api.SortType
import com.theendercore.packed.init.PaScreens.PACK_HANDLER
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity

@Suppress("MagicNumber")
class PackScreenHandler(
    syncId: Int, playerInventory: PlayerInventory,
    private val inventory: Inventory = SimpleInventory(27),
) : ScreenHandler(PACK_HANDLER, syncId) {

    init {
        try {
            if (inventory.size() < 9) error("Pack inventory size is less than 9, ${inventory.size()} closing screen")

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
            playerInventory.addSlots()
        } catch (e: Exception) {
            log.error("Error while creating pack screen handler", e)
            val player = playerInventory.player
            player.openHandledScreen(null)
            this.close(player)
        }
    }

    override fun canUse(player: PlayerEntity): Boolean = inventory.canPlayerUse(player)

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
            } else slot.markDirty()

        }
        return newStack
    }

    override fun close(player: PlayerEntity) {
        if (player is ServerPlayerEntity) inventory.markDirty()
        super.close(player)
        inventory.onClose(player)
    }

    override fun onButtonClick(player: PlayerEntity, id: Int): Boolean {
        val storyType = BUTTON_IDS[id]
        return if (storyType != null && inventory is InvImpl) {
            inventory.sort(storyType)
            true
        } else false
    }

    companion object {
        val BUTTON_IDS = mapOf(1 to SortType.NORMAL, 2 to SortType.REVERSED)
    }

    private fun PlayerInventory.addSlots() {
        // Inv
        for (i in 0..2) for (l in 0..8) {
            val idx = l + i * 9 + 9
            addSlot(PackPlayerSlot(this, idx, 8 + l * 18, 84 + i * 18))
        }
        // Hotbar
        for (i in 0..8) addSlot(PackPlayerSlot(this, i, 8 + i * 18, 142))
    }
}
