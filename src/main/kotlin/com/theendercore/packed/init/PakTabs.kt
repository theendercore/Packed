package com.theendercore.packed.init

import com.theendercore.packed.Packed.id
import com.theendercore.packed.util.registerHolder
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Holder
import net.minecraft.registry.Registries
import net.minecraft.text.Text

object PakTabs {
    val PACKED_TAB = regsiter(
        "packed", FabricItemGroup.builder()
            .name(Text.translatable("itemGroup.packed"))
            .icon { PakItems.PACK.defaultStack }
            .entries { _, entries -> entries.addItem(PakItems.PACK) }
    )

    fun init() = Unit

    private fun regsiter(id: String, group: ItemGroup.Builder): Holder<ItemGroup> =
        Registries.ITEM_GROUP.registerHolder(id(id), group.build())
}
