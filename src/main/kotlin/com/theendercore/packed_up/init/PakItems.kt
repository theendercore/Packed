package com.theendercore.packed_up.init

import com.theendercore.packed_up.PackedUp.id
import com.theendercore.packed_up.items.BackPackItem
import com.theendercore.packed_up.util.WHITE
import com.theendercore.packed_up.util.backpack
import com.theendercore.packed_up.util.dyeColor
import com.theendercore.packed_up.util.register
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
