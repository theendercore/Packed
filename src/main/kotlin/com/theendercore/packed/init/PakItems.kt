package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.items.BackPackItem
import com.theendercore.packed.util.backpack
import com.theendercore.packed.util.dyeColor
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object PakItems {
    val PACK: BackPackItem = reg(
        "pack", BackPackItem(
            Settings()
                .maxCount(1)
                .backpack()
                .dyeColor(0xFFFFFF, false)
        )
    )

    fun init() = Unit
    private fun <T : Item> reg(id: String, item: T): T = Registry.register(Registries.ITEM, id(id), item)

}
