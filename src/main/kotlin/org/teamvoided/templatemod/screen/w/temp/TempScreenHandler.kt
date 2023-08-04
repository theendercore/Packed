package org.teamvoided.templatemod.screen.w.temp

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import org.teamvoided.templatemod.TemplateMod
import org.teamvoided.templatemod.TemplateMod.LOGGER
import org.teamvoided.templatemod.reg.ScreenRegistry.TEMP_SCREEN_HANDLER

class TempScreenHandler(private val inventory: Inventory, syncId: Int) :
    ScreenHandler(TEMP_SCREEN_HANDLER, syncId) {

    constructor(playerInv: PlayerInventory, inv: Inventory, syncId: Int) : this(inv, syncId) {
        TemplateMod.LOGGER.info("-1")
        checkSize(inv, 9)
        //some inventories do custom logic when a player opens it.
        inv.onOpen(playerInv.player)
        //Our inventory
        for (m in 0 until 3) for (l in 0 until 3) addSlot(Slot(inv, l + m * 3, 62 + l * 18, 17 + m * 18))
        //The player inventory
        for (m in 0 until 3) for (l in 0 until 9) addSlot(Slot(playerInv, l + m * 3, 62 + l * 18, 17 + m * 18))
        //The player Hotbar
        for (m in 0 until 9) this.addSlot(Slot(playerInv, m, 8 + m * 18, 142))
        this.slots.forEach { LOGGER.info(" Slot : [{},  {}]", it.id, it.stack.name ) }
        LOGGER.info(this.slots[0].id.toString())
    }

    override fun canUse(player: PlayerEntity): Boolean = inventory.canPlayerUse(player)

    override fun quickTransfer(player: PlayerEntity, i: Int): ItemStack {
        LOGGER.info(" qT - init")
        var newStack = ItemStack.EMPTY
        val slot = slots[i]
        if (slot != null && slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (i < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size, true))
                    return ItemStack.EMPTY
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
                return ItemStack.EMPTY

            if (originalStack.isEmpty) slot.stack = ItemStack.EMPTY
            else slot.markDirty()
        }
        return newStack
    }


}