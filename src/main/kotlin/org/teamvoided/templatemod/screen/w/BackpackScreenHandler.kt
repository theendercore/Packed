package org.teamvoided.templatemod.screen.w

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import org.teamvoided.templatemod.reg.ScreenRegistry

class BackpackScreenHandler(private val inventory: Inventory, i: Int) :
    ScreenHandler(ScreenRegistry.BackPack, i) {
    val columns = 9
    var rows = 1

    constructor(
        i: Int, playerInventory: PlayerInventory, inventory: Inventory, j: Int,
    ) : this(inventory,  i) {
        checkSize(inventory, 9)
        rows = j
        inventory.onOpen(playerInventory.player)
        val k = (rows - 4) * 18
        for (l in 0 until rows) {
            for (m in 0..8) addSlot(Slot(inventory, m + l * 9, 8 + m * 18, 18 + l * 18))
        }

        for (l in 0 until 3) {
            for (m in 0 until 9) addSlot(Slot(playerInventory, m + l * 9 + 9, 8 + m * 18, 103 + l * 18 + k))
        }

        for (l in 0 until 9) addSlot(Slot(playerInventory, l, 8 + l * 18, 161 + k))

    }

    override fun quickTransfer(playerEntity: PlayerEntity, i: Int): ItemStack {

        var itemStack = ItemStack.EMPTY
        val slot = slots[i]
        if (slot != null && slot.hasStack()) {
            val itemStack2 = slot.stack
            itemStack = itemStack2.copy()
            if (i < rows * 9) {
                if (!insertItem(itemStack2, rows * 9, slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(itemStack2, 0, rows * 9, false)) {
                return ItemStack.EMPTY
            }
            if (itemStack2.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }

        return itemStack!!
    }


    override fun canUse(playerEntity: PlayerEntity): Boolean {
        return false
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
    }


    companion object {
//        fun createBag9x3(
//            i: Int,
//            playerInventory: PlayerInventory,
//            inventory: Inventory,
//            buf: PacketByteBuf,
//        ): BackpackScreenHandler =
//            BackpackScreenHandler(ScreenRegistry. BAG_9X3, i, playerInventory, inventory, 3)
//
//        fun createBag9x3(i: Int, playerInventory: PlayerInventory): BackpackScreenHandler =
//            BackpackScreenHandler(ScreenRegistry.BAG_9X3, i, playerInventory, 3)

    }
}
