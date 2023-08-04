package org.teamvoided.templatemod.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.screen.GenericContainerScreenHandler
import org.teamvoided.templatemod.TemplateMod


class BagScreenHandler(syncId: Int, playerInventory: PlayerInventory, backpackInventory: Inventory, slots: Int) :
    GenericContainerScreenHandler(TemplateMod.BAG, syncId, playerInventory, backpackInventory, slots) {

    constructor(syncID: Int, playerInventory: PlayerInventory) :
            this(syncID, playerInventory, SimpleInventory(54), 54)

    override fun canUse(player: PlayerEntity): Boolean = true
}