package org.teamvoided.templatemod.items

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup
import net.minecraft.item.ItemStack
import org.teamvoided.templatemod.TemplateMod
import org.teamvoided.templatemod.TemplateMod.id
import org.teamvoided.templatemod.api.HasInventory

object InventoryAPI {
    val HAS_INVENTORY: ItemApiLookup<HasInventory, Unit> =
        ItemApiLookup.get(id("has_inventory"), HasInventory::class.java, Unit::class.java)
    fun findInvItem(stack: ItemStack): HasInventory? = HAS_INVENTORY.find(stack, Unit)

    fun init() {
        HAS_INVENTORY.registerForItems({ stack, _ -> BItem.InventoryIml(stack)  }, TemplateMod.BITEM)
    }

}