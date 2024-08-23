package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.items.BackPackItem
import com.theendercore.packed.util.WHITE
import com.theendercore.packed.util.backpack
import com.theendercore.packed.util.dyeColor
import com.theendercore.packed.util.register
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries

@Suppress("UNCHECKED_CAST")
object PakItems {
    val PACK: BackPackItem = reg(
        "pack", BackPackItem(
            Settings()
                .maxCount(1)
                .backpack()
                .dyeColor(WHITE, false)
        )
    )

    fun init() = Unit
    private fun <T : Item> reg(id: String, item: T): T = Registries.ITEM.register(id(id), item) as T
}
