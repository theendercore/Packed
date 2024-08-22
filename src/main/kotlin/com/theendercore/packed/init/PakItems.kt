package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.items.PackItem
import com.theendercore.packed.util.backpack
import com.theendercore.packed.util.dyeColor
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object PakItems {
    val PACK: PackItem = reg(
        "pack", PackItem(
            Settings()
                .maxCount(1)
                .backpack()
                .dyeColor(0xFFFFFF, false)
        )
    )

    fun init() = Unit
    private fun <T : Item> reg(id: String, item: T): T = Registry.register(Registries.ITEM, id(id), item)

}
