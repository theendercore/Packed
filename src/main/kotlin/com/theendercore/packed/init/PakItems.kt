package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.items.PackItem
import com.theendercore.packed.util.dyeColor
import com.theendercore.packed.util.isBackpack
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object PakItems {
    val PACK: PackItem = reg(
        "pack", PackItem(
            Settings()
                .maxCount(1)
                .isBackpack()
                .dyeColor(0xFFFFFF, false)
        )
    )

    fun init() {}
    private fun <T : Item> reg(id: String, item: T): T = Registry.register(Registries.ITEM, id(id), item)

}